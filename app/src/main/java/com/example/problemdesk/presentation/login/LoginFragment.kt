package com.example.problemdesk.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.problemdesk.R
import com.example.problemdesk.databinding.FragmentLoginBinding
import com.example.problemdesk.unfiltered.Role

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

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
            if (validate(login, password) == Role.MANAGER) {
                findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToManagerFragment())
            } else if (validate(login, password) == Role.NONE) {
                Toast.makeText(requireContext(), "Invalid login or password", Toast.LENGTH_SHORT)
                    .show()
            } else {
                findNavController().navigate(LoginFragmentDirections.actionNavigationLoginToNavigationProblemForm())
            }
        }

        return root
    }

    private fun validate(login: String, password: String): Role {
        if (login == getString(R.string.login1) && password == getString(R.string.password1)) {
            return Role.COMPLAINER
        } else if (login == getString(R.string.login2) && password == getString(R.string.password2)) {
            return Role.EXECUTOR
        } else if (login == getString(R.string.login3) && password == getString(R.string.password3)) {
            return Role.MASTER
        } else if (login == getString(R.string.login4) && password == getString(R.string.password4)) {
            return Role.MANAGER
        } else {
            return Role.NONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}