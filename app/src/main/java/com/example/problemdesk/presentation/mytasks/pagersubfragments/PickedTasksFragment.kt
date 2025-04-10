package com.example.problemdesk.presentation.mytasks.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentSubPickedTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.details.RequestorBottomSheetDialogFragment
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import com.example.problemdesk.presentation.general.getArea
import com.example.problemdesk.presentation.general.getDate
import com.example.problemdesk.presentation.general.getSpecialization
import kotlinx.coroutines.launch

class PickedTasksFragment : Fragment() {
    private var _binding: FragmentSubPickedTasksBinding? = null
    private val binding get() = _binding!!

    private val pickedTasksViewModel: PickedTasksViewModel by viewModels()

    companion object {
        fun newInstance() = PickedTasksFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubPickedTasksBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.pickedTasksRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
        loadCards()
        setUpResultListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            pickedTasksRv.isGone = true
            plug.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            pickedTasksRv.isVisible = true
            plug.isGone = true
        }
    }

    private fun showPlug() {
        with(binding) {
            progressBar.isGone = true
            pickedTasksRv.isGone = true
            plug.isVisible = true
        }
    }

    private fun setUpObservers() {
        pickedTasksViewModel.cards.observe(viewLifecycleOwner) { cards: List<Card> ->
            (binding.pickedTasksRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
            if (cards.isEmpty()) {
                showPlug()
            } else {
                showContent()
            }
        }
    }

    private fun setUpResultListener() {
        // Listen for the result from the BottomSheetDialogFragment
        parentFragmentManager.setFragmentResultListener("requestUpdate", this) { _, _ ->
            loadCards() // Reload cards when dialog dismisses with result
        }
    }

    private fun loadCards() {
        showLoading()
        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
            if (userId != null) {
                pickedTasksViewModel.loadCards(userId)
            }
        }
    }

    private fun handleCardClick(card: Card) {
        val id = card.requestId
        val date = getDate(card.createdAt)
        val spec = getSpecialization(card.requestType)
        val area = getArea(card.areaId)
        val desc = card.description
        val stat = card.statusId
        showBottomSheetDialogFragmentRequestor(id, stat, date, spec, area, desc)
    }

    private fun showBottomSheetDialogFragmentRequestor(
        requestId: Int,
        stat: Int,
        date: String,
        spec: String,
        area: String,
        desc: String
    ) {
        val role = "executor"
        val requestorBottomSheetDialogFragment =
            RequestorBottomSheetDialogFragment(requestId, stat, role, date, spec, area, desc)
        requestorBottomSheetDialogFragment.show(
            parentFragmentManager,
            RequestorBottomSheetDialogFragment::class.java.simpleName
        )
    }
}