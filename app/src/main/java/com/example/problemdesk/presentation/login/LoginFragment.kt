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
                Role.MANAGER -> findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToManagerFragment())
                Role.NONE -> Toast.makeText(
                    requireContext(),
                    "Invalid login or password",
                    Toast.LENGTH_SHORT
                ).show()

                else -> findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
            }
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}