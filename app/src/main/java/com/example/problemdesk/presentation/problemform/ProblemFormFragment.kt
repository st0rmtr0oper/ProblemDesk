package com.example.problemdesk.presentation.problemform

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.databinding.FragmentProblemFormBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Specialization
import com.example.problemdesk.domain.OLDMODELSrefactor.Workplace
import kotlinx.coroutines.launch

//TODO spinners/edit text validation
//TODO errors

class ProblemFormFragment : Fragment() {

    private var _binding: FragmentProblemFormBinding? = null
    private val binding get() = _binding!!

    private val problemFormViewModel: ProblemFormViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProblemFormBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.loginButton.setOnClickListener {
            val requestType: Int
            val userId: Int //= binding.userWorkplaceSpinner.focusedChild
            val areaId: Int //= binding.userWorkplaceSpinner TODO how to choose that shit?
            val description: String //= binding. TODO need to pick text

            lifecycleScope.launch {
//                problemFormViewModel.createRequest(requestType, userId, areaId, description)
            } //TODO error handling - RequestResponse???
        }
        //TODO need to add a dialog

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

    //TODO this shouldn't be here...? who cares?
    private fun getSpecializationArray(): Array<String> {
        val specializations: Array<String> = arrayOf(
            "Выберите тип проблемы...",
            "Документы",
            "Инструменты",
            "Санитарно-бытовые условия",
            "Безопасность"
        )
        return specializations
    }
    private fun getWorkplaceArray(): Array<String> {
        val workplaces: Array<String> = arrayOf(
            "Выберите участок...",
            "№1",
            "№2",
            "№3",
            "№4",
            "№5",
            "№6",
            "№7",
            "№8"
        )
        return workplaces
    }
}