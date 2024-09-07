package com.example.problemdesk.presentation.mytasks.pagersubfragments

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
import com.example.problemdesk.databinding.FragmentSubNewTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import kotlinx.coroutines.launch

//TODO modal windows: logs, details
//TODO reason

class NewTasksFragment : Fragment() {
    private var _binding: FragmentSubNewTasksBinding? = null
    private val binding get() = _binding!!

    private val newTasksViewModel: NewTasksViewModel by viewModels()

    companion object {
        fun newInstance() = NewTasksFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSubNewTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        newTasksViewModel.cards.observe(viewLifecycleOwner, Observer { cards: List<Card> ->
            (binding.newTasksRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
        })

        newTasksViewModel.takeSuccess.observe(viewLifecycleOwner, Observer { success: Boolean ->
            if (success) {
                showSuccessTakeDialog()
            }
        })

        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt(USER_ID, 0)

        lifecycleScope.launch {
            if (userId != null) {
                newTasksViewModel.loadCards(userId)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
        binding.newTasksRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleCardClick(card: Card) {
        val requestId = card.requestId
        //TODO    reason!
        val reason = ""
//        showButtonsDialog(requestId, reason)
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
//            val userId = sharedPreferences?.getInt(USER_ID, 0)
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

    private fun showSuccessTakeDialog() {
        androidx.appcompat.app.AlertDialog.Builder(requireContext()).apply {
            setTitle("Заявка принята")
            setMessage("Заявка принята вами на выполнение")
            setNegativeButton("Ок", null)
            show()
        }
    }
}