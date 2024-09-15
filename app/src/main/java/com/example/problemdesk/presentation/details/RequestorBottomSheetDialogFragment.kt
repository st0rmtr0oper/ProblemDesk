package com.example.problemdesk.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.OptIn
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
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
    ): View? {
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

        lifecycleScope.launch {
            requestorBottomSheetDialogViewModel.loadHistory(requestId)
        }

        setUpClickListeners()

        //TODO click listeners
        //включаются нужные клик листенеры в onViewCreated
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
                    deleteButton.setOnClickListener { }
                }
                // inflate requestor_delete
            } else if (stat == 5) {
                with(binding) {
                    requestorCheckButtons.visibility = View.VISIBLE
                    solvedButton.setOnClickListener {}
                    resendButton.setOnClickListener {}
                }
                // inflate requestor_check
            }
        } else if (role == "executor") {
            if (stat == 2) {
                with(binding) {
                    executorTakeButtons.visibility = View.VISIBLE
                    takeButton.setOnClickListener {}
                }
                //inflate executor_unassigned
            } else if (stat == 4) {
                with(binding) {
                    executorInworkButtons.visibility = View.VISIBLE
                    sendButton.setOnClickListener { }
                    dropButton.setOnClickListener { }
                }
                //inflate executor_assigned
            }
        } else if (role == "master") {
            if (stat == 1) {
                with(binding) {
                    masterButtons.visibility = View.VISIBLE
                    approveButton.setOnClickListener { }
                    cancelButton.setOnClickListener { }
                }
                //inflate master
            }
        }
    }

    private fun hideAll() {
        binding.reasonText.visibility = View.GONE
        with(binding) {
            requestorDeleteButtons
            requestorCheckButtons
            executorTakeButtons
            executorInworkButtons
            masterButtons
        }.visibility = View.GONE

    }

    private fun setUpClickListeners() {
        clearClickListeners()

        //TODO sharedPrefs again. i really need to put em in other place

        val userdId = getSharedPrefsUserId(requireContext())
        val reason = binding.reasonText.text.toString()
        val request = TaskManipulationRequest(userdId, requestId, reason)

        if (role == "requestor") {
            if (stat == 1) {
                with(binding) {
                    deleteButton.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.requestorDelete(request)
                        }
                    }
                }
                // inflate requestor_delete
            } else if (stat == 5) {
                with(binding) {
                    solvedButton.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.requestorConfirm(request)
                        }

                    }
                    resendButton.setOnClickListener {
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
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.takeTask(request)
                        }

                    }
                }
                //inflate executor_unassigned
            } else if (stat == 4) {
                with(binding) {
                    sendButton.setOnClickListener {
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.executorComplete(request)

                        }
                    }
                    dropButton.setOnClickListener {
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
                        CoroutineScope(Dispatchers.IO).launch {
                            requestorBottomSheetDialogViewModel.masterApprove(request)
                        }

                    }
                    cancelButton.setOnClickListener {
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

    private fun setUpObservers() {
        requestorBottomSheetDialogViewModel.logs.observe(
            viewLifecycleOwner,
            Observer { logs: List<RequestLog> ->
                (binding.logsRv.adapter as? DetailsRecyclerViewAdapter)?.logs = logs
            })

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
            setNegativeButton("Ок", null)
            show()
        }
    }
    private fun showErrorDialog(text: String) {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Ошибка")
            setMessage("Произошла ошибка: \n$text")
            setNegativeButton("Ок", null)
            show()
        }
    }
}