package com.example.problemdesk.presentation.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.problemdesk.databinding.FragmentStatisticsBinding

class ManagerFragment : Fragment() {

    private var _binding: FragmentStatisticsBinding? = null
    private val binding get() = _binding!!

    private val managerViewModel: ManagerViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStatisticsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //графики

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}