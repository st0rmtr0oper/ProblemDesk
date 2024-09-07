package com.example.problemdesk.presentation.mastermenu.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.example.problemdesk.R
import com.example.problemdesk.data.models.TaskManipulationRequest
import com.example.problemdesk.databinding.MasterApproveBottomSheetDialogBinding

//TODO UI

class MasterApproveBottomSheetDialog(private val requestId: Int, private val userId: Int, private val masterApproveViewModel: MasterApproveViewModel) : BottomSheetDialogFragment() {

    private var _binding: MasterApproveBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    //TODO need to check how it's working, it seems like it being created 2 times
//    private val masterApproveViewModel: MasterApproveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = MasterApproveBottomSheetDialogBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonApprove.setOnClickListener{
            val reason = binding.editTextInput.text.toString()
            val request = TaskManipulationRequest(userId, requestId, reason)
            masterApproveViewModel.masterApprove(request)
            dismiss()
        }

        binding.buttonCancel.setOnClickListener{
            val reason = binding.editTextInput.text.toString()
            val request = TaskManipulationRequest(userId, requestId, reason)
            masterApproveViewModel.masterDeny(request)
            dismiss()
        }
    }
}