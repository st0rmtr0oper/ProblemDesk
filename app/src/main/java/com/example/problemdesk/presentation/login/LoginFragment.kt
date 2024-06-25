package com.example.problemdesk.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.problemdesk.MainActivity
import com.example.problemdesk.databinding.FragmentLoginBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Role

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
            loginViewModel.validate(login, password, resources)
        }

        loginViewModel.loginResult.observe(viewLifecycleOwner, Observer { role ->
            when (role) {
                Role.NONE -> Toast.makeText(
                    requireContext(),
                    "Неверный логин или пароль",
                    Toast.LENGTH_SHORT
                ).show()

                Role.MANAGER -> {
                    (activity as MainActivity).setupBottomNavMenu("manager")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationStatistics())
                }

                Role.MASTER -> {
                    (activity as MainActivity).setupBottomNavMenu("master")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationMaster())
                }

                else -> {
                    (activity as MainActivity).setupBottomNavMenu("executor")
                    findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
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