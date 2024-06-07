package com.example.problemdesk.presentation.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentManagerBinding

class ManagerFragment : Fragment() {

    private var _binding: FragmentManagerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //графики

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}