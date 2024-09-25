package com.example.problemdesk.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentDetailsBottomSheetDialogBinding
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.presentation.general.SingleLiveEvent
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

//not sure how good this code is. actually, looks like shit
//govnokod

//TODO NoSuchMethodException (constructor with parameters)
//The error you're encountering, NoSuchMethodException, occurs because Android can't find a default
// (no-argument) constructor for your RequestorBottomSheetDialogFragment. By default, fragments need
// a public no-argument constructor so the system can recreate them during configuration changes, like rotation.
//
//Solution:
//Remove the constructor that takes parameters (private val requestId: Int, private val stat: Int,
// private val role: String) and use the newInstance pattern with a Bundle to pass data instead.


class RequestorBottomSheetDialogFragment(
    private val requestId: Int,
    private val stat: Int,
    private val role: String,
    private val date: String,
    private val spec: String,
    private val area: String,
    private val desc: String
) : BottomSheetDialogFragment() {
    private var _binding: FragmentDetailsBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    private val requestorBottomSheetDialogViewModel: RequestorBottomSheetDialogViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding =
            FragmentDetailsBottomSheetDialogBinding.inflate(inflater, container, false)

        binding.dateOfCreationData.text = date
        binding.specializationData.text = spec
        binding.areaData.text = area
        binding.descriptionData.text = desc

        inflateOnScenario()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.logsRv.adapter = DetailsRecyclerViewAdapter(::handleLogClick)
        loadLogs()
        setUpClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun inflateOnScenario() {
        hideAll()
        if (role == "requestor") {
            if (stat == 1) {
                with(binding) {
                    requestorDeleteButtons.visibility = View.VISIBLE
                }
                // inflate requestor_delete
            } else if (stat == 5) {
                with(binding) {
                    reasonText.visibility = View.VISIBLE
                    requestorCheckButtons.visibility = View.VISIBLE
                }
                // inflate requestor_check
            }
        } else if (role == "executor") {
            if (stat == 2) {
                with(binding) {
                    executorTakeButtons.visibility = View.VISIBLE
                }
                //inflate executor_unassigned
            } else if (stat == 4) {
                with(binding) {
                    reasonText.visibility = View.VISIBLE
                    executorInworkButtons.visibility = View.VISIBLE
                }
                //inflate executor_assigned
            }
        } else if (role == "master") {
            if (stat == 1) {
                with(binding) {
                    reasonText.visibility = View.VISIBLE
                    masterButtons.visibility = View.VISIBLE
                }
                //inflate master
            }
        }
    }

    private fun hideAll() {
        with(binding) {
            reasonText
            requestorDeleteButtons
            requestorCheckButtons
            executorTakeButtons
            executorInworkButtons
            masterButtons
        }.visibility = View.GONE
    }

    private fun setUpClickListeners() {
        clearClickListeners()

        var reason = ""
        var request: TaskManipulationRequest

        //TODO sharedPrefs again. i really need to put em in other place

        val userdId = getSharedPrefsUserId(requireContext())
        if (role == "requestor") {
            if (stat == 1) {
                with(binding) {
                    deleteButton.setOnClickListener {
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.requestorDelete(request)
                        }
                    }
                }
                // inflate requestor_delete
            } else if (stat == 5) {
                with(binding) {
                    solvedButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.requestorConfirm(request)
                        }

                    }
                    resendButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.requestorDeny(request)
                        }
                    }
                }
                // inflate requestor_check
            }
        } else if (role == "executor") {
            if (stat == 2) {
                with(binding) {
                    takeButton.setOnClickListener {
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.takeTask(request)
                        }

                    }
                }
                //inflate executor_unassigned
            } else if (stat == 4) {
                with(binding) {
                    sendButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.executorComplete(request)

                        }
                    }
                    dropButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.executorCancel(request)
                        }
                    }
                }
                //inflate executor_assigned
            }
        } else if (role == "master") {
            if (stat == 1) {
                with(binding) {
                    approveButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.masterApprove(request)
                        }

                    }
                    cancelButton.setOnClickListener {
                        reason = binding.reasonText.text.toString()
                        request = TaskManipulationRequest(userdId, requestId, reason)
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.masterDeny(request)
                        }
                    }
                }
                //inflate master
            }
        }
    }

    private fun clearClickListeners() {
        with(binding) {
            //requestor
            solvedButton.setOnClickListener(null)
            resendButton.setOnClickListener(null)
            deleteButton.setOnClickListener(null)
            //executor
            takeButton.setOnClickListener(null)
            dropButton.setOnClickListener(null)
            sendButton.setOnClickListener(null)
            //master
            cancelButton.setOnClickListener(null)
            approveButton.setOnClickListener(null)
        }
    }

    private fun showLogLoading() {
        with(binding) {
            progressBar.isVisible = true
            logsRv.isGone = true
        }
    }

    private fun showLogContent() {
        with(binding) {
            progressBar.isGone = true
            logsRv.isVisible = true
        }
    }

    private fun loadLogs() {
        showLogLoading()
        lifecycleScope.launch {
            requestorBottomSheetDialogViewModel.loadHistory(requestId)
        }
    }

    private fun setUpObservers() {
        showLogContent()
        requestorBottomSheetDialogViewModel.logs.observe(viewLifecycleOwner) { logs: List<RequestLog> ->
            (binding.logsRv.adapter as? DetailsRecyclerViewAdapter)?.logs = logs
            showLogContent()
        }

        with(requestorBottomSheetDialogViewModel) {
            // List of LiveData properties to observe
            val liveDataList = listOf(
                reqConfirmSuccess,
                reqDenySuccess,
                reqDeleteSuccess,
                takeSuccess,
                cancelSuccess,
                completeSuccess,
                approveSuccess,
                denySuccess
            )
            // Observe each LiveData property
            liveDataList.forEach { observeEvent(it) }
        }
        //TODO need to check this
    }

    private fun observeEvent(liveData: LiveData<SingleLiveEvent<String>>) {
        liveData.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { status ->
                if (status == "success") {
                    showSuccessDialog()
                } else {
                    showErrorDialog(status)
                }
            }
        }
    }

    private fun handleLogClick(log: RequestLog) {
        //TODO i dont need it actually
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Успешно")
            setMessage("Действие произведено успешно")
            setPositiveButton("Ок") { _, _ ->
                setFragmentResult("requestUpdate", Bundle())
                dismiss()
            }
        }.show()
    }

    private fun showErrorDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Ошибка")
            setMessage("Произошла ошибка: \n$text")
            setPositiveButton("Ок") { _, _ ->
                setFragmentResult("requestUpdate", Bundle())
                dismiss()
                //TODO idk is it needed, but i will add it for now
            }
        }.show()
    }
}