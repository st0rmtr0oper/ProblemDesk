package com.example.problemdesk.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.databinding.FragmentDetailsBottomSheetDialogRequestorBinding
import com.example.problemdesk.domain.models.RequestLog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

class RequestorBottomSheetDialogFragment(
    private val requestId: Int,
    private val stat: Int,
    private val role: String,
    private val date: String,
    private val spec: String,
    private val area: String,
    private val desc: String
) : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailsBottomSheetDialogRequestorBinding? = null
    private val binding get() = _binding!!

    private val requestorBottomSheetDialogViewModel: RequestorBottomSheetDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding =
            FragmentDetailsBottomSheetDialogRequestorBinding.inflate(inflater, container, false)

        binding.dateOfCreationData.text = date
        binding.specializationData.text = spec
        binding.areaData.text = area
        binding.descriptionData.text = desc
        //TODO последний комментарий

        inflateOnScenario()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setUpObservers()
        binding.logsRv.adapter = DetailsRecyclerViewAdapter(::handleLogClick)
        setUpClickListeners()
        lifecycleScope.launch {
            requestorBottomSheetDialogViewModel.loadHistory(requestId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inflateOnScenario() {
        if (role == "requestor") {
            if (stat == 1) {
                // inflate requestor_delete
            }
            else if (stat == 5) {
                // inflate requestor_check
            }
        }
        else if (role == "executor") {
            if (stat == 2) {
                //inflate executor_unassigned
            }
            else if (stat == 4) {
                //inflate executor_assigned
            }
        }
        else if (role == "master") {
            if (stat == 1) {
                //inflate master
            }
        }
        else {
            //inflate monitor
        }
    }

    private fun setUpObservers() {

        //TODO i get status errors
        requestorBottomSheetDialogViewModel.logs.observe(
            viewLifecycleOwner,
            Observer { logs: List<RequestLog> ->
                (binding.logsRv.adapter as? DetailsRecyclerViewAdapter)?.logs = logs
            })
    }

    private fun handleLogClick(log: RequestLog) {
        //TODO i dont need it actually
    }

    private fun setUpClickListeners() {
        binding.solvedButton
        binding.sendButton

        binding.deleteButton
    }
}

//TODO
//вогнать все варианты в один шаблон (проставить везде visible=gone у layout'ов)
//при создании инстнса проверяется статусИд
//в зависимости от статуса создаются нужные кнопки в onCreateView
//фрагмент создается как обычно
//включаются нужные клик листенеры в onViewCreated