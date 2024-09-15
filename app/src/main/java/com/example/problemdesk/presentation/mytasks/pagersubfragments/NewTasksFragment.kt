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
import com.example.problemdesk.databinding.FragmentSubNewTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.details.RequestorBottomSheetDialogFragment
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import com.example.problemdesk.presentation.general.getArea
import com.example.problemdesk.presentation.general.getDate
import com.example.problemdesk.presentation.general.getSpecialization
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
        showLoading()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        binding.newTasksRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
            if (userId != null) {
                newTasksViewModel.loadCards(userId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            newTasksRv.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            newTasksRv.isVisible = true
        }
    }

    private fun setUpObservers() {
        showContent()
        newTasksViewModel.cards.observe(viewLifecycleOwner) { cards: List<Card> ->
            (binding.newTasksRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
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

    private fun showBottomSheetDialogFragmentRequestor(requestId: Int, stat: Int, date:String, spec: String, area: String, desc: String) {
        val role = "executor"
        val requestorBottomSheetDialogFragment = RequestorBottomSheetDialogFragment(requestId, stat, role, date, spec, area, desc)
        requestorBottomSheetDialogFragment.show(parentFragmentManager, RequestorBottomSheetDialogFragment::class.java.simpleName)
    }
}