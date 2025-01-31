package com.example.problemdesk.presentation.login

import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.problemdesk.MainActivity
import com.example.problemdesk.data.sharedprefs.OLD_FCM
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.ROLE
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.data.sharedprefs.USER_ROLE
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserRole
import com.example.problemdesk.databinding.FragmentLoginBinding
import kotlinx.coroutines.launch

//TODO error messages for user (no connection, dead server and etc) ---
// --- implemented, but in primitive way. returns shit when you type wrong credentials
//TODO password recovery (hidden for now)

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val loginViewModel: LoginViewModel by lazy {
        ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        setUpObservers(sharedPreferences)
        setUpTextChangedListeners()
        setUpClickListeners(sharedPreferences)
    }


    //TODO it says that this is deprecated, but it works. i have trouble with checkLogin -
    // it seems it try to called before activity fully created (idk), so onCreateView and onViewCreated
    // are not working with that shit
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        checkLogin()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //this ifs and "strings" are bad code
    private fun checkLogin() {
        val userId = context?.let { getSharedPrefsUserId(it) }
        val userRole = context?.let { getSharedPrefsUserRole(it) }
        if (userId != 0 && userRole != 0) {
            when (userRole) {
                1 -> {
                    (activity as MainActivity).setUpBottomNavMenu(1)
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
                }

                2 -> {
                    (activity as MainActivity).setUpBottomNavMenu(2)
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationMaster())
                }

                3 -> {
                    (activity as MainActivity).setUpBottomNavMenu(3)
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationCharts())
                }
            }
        }
    }

    private fun setUpObservers(sharedPreferences: SharedPreferences?) {
        loginViewModel.errorStatus.observe(viewLifecycleOwner) { event ->
            hideLoading()
            event.getContentIfNotHandled()?.let { errorStatus ->
                showErrorDialog(errorStatus)
            }
        }

        loginViewModel.userId.observe(viewLifecycleOwner) { userId ->
            hideLoading()
            //Storing user ID
            //null hell - looks like shit
            userId?.let {
                sharedPreferences?.edit()?.putInt(USER_ID, it)?.apply()
            }
        }

        loginViewModel.userRole.observe(viewLifecycleOwner) { role ->
            hideLoading()
            role?.let {
                sharedPreferences?.edit()?.putInt(USER_ROLE, it)?.apply()
            }

            when (role) {
                1 -> {
                    (activity as MainActivity).setUpBottomNavMenu(role)
                    role.let { sharedPreferences?.edit()?.putInt(ROLE, it)?.apply() }
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
                }

                2 -> {
                    (activity as MainActivity).setUpBottomNavMenu(role)
                    role.let { sharedPreferences?.edit()?.putInt(ROLE, it)?.apply() }
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationMaster())
                }

                3 -> {
                    (activity as MainActivity).setUpBottomNavMenu(role)
                    role.let { sharedPreferences?.edit()?.putInt(ROLE, it)?.apply() }
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationCharts())
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
        }
    }

    private fun setUpClickListeners(sharedPreferences: SharedPreferences?) {
        binding.loginButton.setOnClickListener {
            val login = binding.loginText.text.toString()
            val password = binding.loginPassword.text.toString()
            binding.loginTextLayout.error = null
            binding.loginPasswordLayout.error = null

            if (validate(login, password)) {
                var fcm: String?
                showLoading()
                lifecycleScope.launch {
                    loginViewModel.validate(login, password)
                    fcm = loginViewModel.getFcm()
                    fcm?.let { sharedPreferences?.edit()?.putString(OLD_FCM, it)?.apply() }
                }
            } else {
                showNotValidatedDialog()
            }
        }
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            header.isGone = true
        }
    }

    private fun hideLoading() {
        with(binding) {
            progressBar.isGone = true
            header.isVisible = true
        }
    }

    private fun setUpTextChangedListeners() {
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
    }

    private fun showErrorDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Ошибка")
            setMessage("Произошла ошибка: \n$text")
            setNegativeButton("Ок", null)
            show()
        }
    }

    private fun showNotValidatedDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Неполные данные")
            setMessage("Пожалуйста, заполните все поля")
            setNegativeButton("Ок", null)
            show()
        }
    }

    private fun validate(login: String, password: String): Boolean {
        return login != "" && password != ""
    }
}