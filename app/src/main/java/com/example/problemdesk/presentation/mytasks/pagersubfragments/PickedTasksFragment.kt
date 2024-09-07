package com.example.problemdesk.presentation.mytasks.pagersubfragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.R
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.databinding.FragmentSubPickedTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import kotlinx.coroutines.launch

class PickedTasksFragment: Fragment() {
    private var _binding: FragmentSubPickedTasksBinding? = null
    private val binding get() = _binding!!

    private val pickedTasksViewModel: PickedTasksViewModel by viewModels()

    companion object {
        fun newInstance() = PickedTasksFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubPickedTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        pickedTasksViewModel.cards.observe(viewLifecycleOwner, Observer { cards: List<Card> ->
            (binding.pickedTasksRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
        })

        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt(USER_ID, 0)

        lifecycleScope.launch {
            if (userId != null) {
                pickedTasksViewModel.loadCards(userId)
            }
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
        binding.pickedTasksRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
    }

    private fun handleCardClick(card: Card) {
        showButtonsDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showButtonsDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_assigned, null)

        // Create an AlertDialog Builder
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()

        // Set up the button click listeners
        dialogView.findViewById<Button>(R.id.button_send).setOnClickListener {
            // Handle Take button click
            dialog.dismiss()

        }

        dialogView.findViewById<Button>(R.id.button_details).setOnClickListener {
            // Handle Details button click
            dialog.dismiss()

        }

        dialogView.findViewById<Button>(R.id.button_logs).setOnClickListener {
            // Handle Logs button click
            dialog.dismiss()

        }

        dialogView.findViewById<Button>(R.id.button_cancel).setOnClickListener {
            // Handle Cancel button click
            dialog.dismiss()
        }
    }
}