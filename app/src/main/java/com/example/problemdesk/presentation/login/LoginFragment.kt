package com.example.problemdesk.presentation.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.problemdesk.MainActivity
import com.example.problemdesk.databinding.FragmentLoginBinding
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import kotlinx.coroutines.launch

//TODO remember me. шобы не заходить постоянно в акк раз за разом

//TODO loading animation
//TODO error messages for user (no connection, dead server and etc)

//TODO password recovery

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Add TextWatcher to loginText for clearing errors when users type smthng
        binding.loginText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.loginTextLayout.error = null // Clear error when user starts typing
            }
        })
        // Add TextWatcher to loginPassword
        binding.loginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                binding.loginPasswordLayout.error = null // Clear error when user starts typing
            }
        })

        //i dont know is this a good way to use SP, cause it have troubles with context inside ViewModel
        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }

        binding.loginButton.setOnClickListener {
            val login = binding.loginText.text.toString()
            val password = binding.loginPassword.text.toString()
            binding.loginTextLayout.error = null
            binding.loginPasswordLayout.error = null

            var fcm: String?
            lifecycleScope.launch {
                loginViewModel.validate(login, password)
                fcm = loginViewModel.getFcm()
                fcm?.let { sharedPreferences?.edit()?.putString("old_fcm", it)?.apply() }
            }
        }

        loginViewModel.userId.observe(viewLifecycleOwner, Observer { userId ->
            //Storing user ID
            //null hell - looks like shit
            userId?.let {
                sharedPreferences?.edit()?.putInt("user_id", it)?.apply()
            }
        })

        loginViewModel.userRole.observe(viewLifecycleOwner, Observer { role ->
            when (role) {
                1 -> {
                    (activity as MainActivity).setupBottomNavMenu("executor")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
                }

                2 -> {
                    (activity as MainActivity).setupBottomNavMenu("master")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationMaster())
                }

                3 -> {
                    (activity as MainActivity).setupBottomNavMenu("manager")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationStatistics())
                }

                0 -> {
                    binding.loginTextLayout.apply {
                        isErrorEnabled = true
                        error = "Неверный логин или пароль"
                    }
                    binding.loginPasswordLayout.apply {
                        isErrorEnabled = true
                        error = "Неверный логин или пароль"
                    }
                }
            }
        })
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}