package com.example.problemdesk.presentation.login

import android.os.Bundle
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
import com.example.problemdesk.domain.OLDMODELSrefactor.Role
import kotlinx.coroutines.launch

//TODO validation

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

        binding.loginButton.setOnClickListener {
            val login = binding.loginText.text.toString()
            val password = binding.loginPassword.text.toString()
            binding.loginTextLayout.error = null
            binding.loginPasswordLayout.error = null

            lifecycleScope.launch {
                loginViewModel.validate(login, password)
            }
        }

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
                    //TODO need to handle error!!!
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