package com.example.problemdesk.presentation.mytasks.pagersubfragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.R
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.databinding.FragmentSubNewTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.CardRecyclerViewAdapter
import kotlinx.coroutines.launch

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

        val sharedPreferences = context?.let { PreferenceUtil.getEncryptedSharedPreferences(it) }
        val userId = sharedPreferences?.getInt("user_id", 0)

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

    private fun handleCardClick(card: Card) {
        //TODO delete mocking
//        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
        showButtonsDialog()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showButtonsDialog() {
        // Inflate the custom layout
        val dialogView = layoutInflater.inflate(R.layout.dialog_unassigned, null)

        // Create an AlertDialog Builder
        val builder = AlertDialog.Builder(requireContext())
            .setView(dialogView)

        // Create and show the AlertDialog
        val dialog = builder.create()
        dialog.show()

        // Set up the button click listeners
        dialogView.findViewById<Button>(R.id.button_take).setOnClickListener {
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