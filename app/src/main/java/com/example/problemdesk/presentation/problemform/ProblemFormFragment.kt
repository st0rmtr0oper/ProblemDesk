package com.example.problemdesk.presentation.problemform

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentProblemFormBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Specialization
import com.example.problemdesk.domain.OLDMODELSrefactor.Workplace

//TODO spinners/edit text validation
//TODO errors

class ProblemFormFragment : Fragment() {

    private var _binding: FragmentProblemFormBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProblemFormBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val problemTypeSpinner: Spinner = binding.problemTypeSpinner
        val userWorkplaceSpinner: Spinner = binding.userWorkplaceSpinner
        val problemTypeAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, getSpecializationArray())
        val userWorkplaceAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), R.layout.simple_spinner_item, getWorkplaceArray())
        problemTypeAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        userWorkplaceAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        problemTypeSpinner.adapter = problemTypeAdapter
        userWorkplaceSpinner.adapter = userWorkplaceAdapter

        //TODO spinner choose handling
        //https://metanit.com/java/android/5.4.php
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //TODO add hint emulation

    //TODO this shouldn't be here
    private fun getSpecializationArray(): Array<String> {
        val specializations: Array<String> = arrayOf(
            "Выберите тип проблемы...",
            Specialization.DOCUMENTS.toString(),
            Specialization.INSTRUMENTS.toString(),
            Specialization.SANITARY_CONDITIONS.toString(),
            Specialization.SAFETY.toString()
        )
        return specializations
    }
    private fun getWorkplaceArray(): Array<String> {
        val workplaces: Array<String> = arrayOf(
            "Выберите участок...",
            Workplace.N1.toString(),
            Workplace.N2.toString(),
            Workplace.N3.toString(),
            Workplace.N4.toString()
        )
        return workplaces
    }
}