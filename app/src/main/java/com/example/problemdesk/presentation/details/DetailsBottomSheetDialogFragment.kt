package com.example.problemdesk.presentation.details

//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Observer
//import androidx.lifecycle.lifecycleScope
//import com.example.problemdesk.databinding.DetailsBottomSheetDialogBinding
//import com.example.problemdesk.domain.models.RequestLog
//import com.example.problemdesk.presentation.details.DETAILSRecyclerViewAdapter
//import com.example.problemdesk.presentation.details.DetailsViewModel
//import com.google.android.material.bottomsheet.BottomSheetDialogFragment
//import kotlinx.coroutines.launch
//
////TODO bind with navigation+requestId
//
//class DetailsBottomSheetDialogFragment(
//    private val requestId: Int,
//    private val
//) : BottomSheetDialogFragment() {
//    private var _binding: DetailsBottomSheetDialogBinding? = null
//    private val binding get() = _binding!!
//
//    private val logsViewModel: DetailsViewModel by viewModels()
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = DetailsBottomSheetDialogBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        logsViewModel.cards.observe(viewLifecycleOwner, Observer { logs: List<RequestLog> ->
//            (binding.logsRv.adapter as? DETAILSRecyclerViewAdapter)?.logs = logs
//        })
//
//        lifecycleScope.launch {
//            logsViewModel.loadLogs(requestId)
//        }
//
//        return root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        //::handleCardClick binding RV click logic with fragment
////        binding.logsRv.adapter = LogRecyclerViewAdapter(::handleCardClick)
//        //TODO what to do with that
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}