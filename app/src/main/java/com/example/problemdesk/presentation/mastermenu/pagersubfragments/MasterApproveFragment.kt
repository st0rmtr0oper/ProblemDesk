package com.example.problemdesk.presentation.mastermenu.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentSubApproveBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import kotlinx.coroutines.launch

//TODO modal windows: logs, details
//TODO reason

class MasterApproveFragment : Fragment() {
    private var _binding: FragmentSubApproveBinding? = null  //TODO
    private val binding get() = _binding!!

    private val masterApproveViewModel: MasterApproveViewModel by viewModels()

    companion object {
        fun newInstance() = MasterApproveFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubApproveBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        //::handleCardClick binding RV click logic with fragment
        binding.approveRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
            if (userId != null) {
                masterApproveViewModel.loadCards(userId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleCardClick(card: Card) {
//        val requestId = card.requestId
//        TODO    reason!
//        val reason = ""
//        showButtonsDialog(requestId, reason)
        //TODO HANDLE CLICK
        val requestId = card.requestId
//        showBottomSheetDialog(requestId)
    }

    private fun setUpObservers() {
        masterApproveViewModel.cards.observe(viewLifecycleOwner, Observer { cards: List<Card> ->
            (binding.approveRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
        })

        masterApproveViewModel.approveSuccess.observe(
            viewLifecycleOwner,
            Observer { success: Boolean ->
                if (success) {
//                    showApproveDialog()
                }
            })

        masterApproveViewModel.denySuccess.observe(
            viewLifecycleOwner,
            Observer { success: Boolean ->
                if (success) {
//                    showDenyDialog()
                }
            })

    }


    private fun showBottomSheetDialog(requestId: Int) {
        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt(USER_ID, 0)

//        if (userId != null) {
//            val bottomSheet =
//                MasterApproveBottomSheetDialog(requestId, userId, masterApproveViewModel)
//            bottomSheet.show(parentFragmentManager, bottomSheet.tag)
        //parentFragmentManager - Fragment, supportFragmentManager - Activity
    }


//    private fun showButtonsDialog(requestId: Int, reason: String) {
//        // Inflate the custom layout
//        val dialogView = layoutInflater.inflate(R.layout.dialog_unassigned, null)
//        // Create an AlertDialog Builder
//        val builder = AlertDialog.Builder(requireContext())
//            .setView(dialogView)
//        // Create and show the AlertDialog
//        val dialog = builder.create()
//        dialog.show()
//
//        // Set up the button click listeners
//        dialogView.findViewById<Button>(R.id.button_take).setOnClickListener {
//            // Handle Take button click
//
//            val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
//            val userId = sharedPreferences?.getInt("user_id", 0)
//
//            //TODO implement Take on work
//            lifecycleScope.launch {
//                if (userId != null) {
//                    val request = TaskManipulationRequest(userId, requestId, reason)
//                    newTasksViewModel.takeTask(request)
//
//                    newTasksViewModel.loadCards(userId)
//                }
//            }
//            dialog.dismiss()
//        }
//
//        dialogView.findViewById<Button>(R.id.button_details).setOnClickListener {
//            // Handle Details button click
//            dialog.dismiss()
//        }
//
//        dialogView.findViewById<Button>(R.id.button_logs).setOnClickListener {
//            // Handle Logs button click
//            dialog.dismiss()
//        }
//
//        dialogView.findViewById<Button>(R.id.button_cancel).setOnClickListener {
//            // Handle Cancel button click
//            dialog.dismiss()
//        }
//    }

//    private fun showApproveDialog() {
//        androidx.appcompat.app.AlertDialog.Builder(requireContext()).apply {
//            setTitle("Заявка принята")
//            setMessage("Заявка успешно принятя")
//            setNegativeButton("Ок", null)
//            show()
//        }
//    }
//
//    private fun showDenyDialog() {
//        androidx.appcompat.app.AlertDialog.Builder(requireContext()).apply {
//            setTitle("Заявка отклонена")
//            setMessage("Заявка успешно отклонена")
//            setNegativeButton("Ок", null)
//            show()
//        }
//    }
}