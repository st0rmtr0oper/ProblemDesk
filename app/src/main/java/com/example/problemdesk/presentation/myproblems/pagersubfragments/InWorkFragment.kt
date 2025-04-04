package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.R
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentSubInworkBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.details.RequestorBottomSheetDialogFragment
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import com.example.problemdesk.presentation.general.getArea
import com.example.problemdesk.presentation.general.getDate
import com.example.problemdesk.presentation.general.getSpecialization
import com.example.problemdesk.presentation.myproblems.CardListManager
import com.example.problemdesk.presentation.myproblems.SortByTime
import com.example.problemdesk.presentation.myproblems.SortByAreaId
import com.example.problemdesk.presentation.myproblems.SortByStatusId
import kotlinx.coroutines.launch

class InWorkFragment : Fragment() {
    private var _binding: FragmentSubInworkBinding? = null
    private val binding get() = _binding!!

    private val inWorkViewModel: InWorkViewModel by viewModels()

    private lateinit var cardListManager: CardListManager
    private var currentCards: List<Card> = listOf()


    companion object {
        fun newInstance() = InWorkFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubInworkBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cardListManager = CardListManager(SortByTime()) // or any initial sorting strategy
        setUpRadioButtons()

        setUpObservers()
        binding.inWorkRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
        loadCards()
        setUpResultListener()
    }

    private fun setUpRadioButtons() {
        val radioGroup = binding.radioGroup
        radioGroup.setOnCheckedChangeListener {_, checkedId ->
            val strategy = when (checkedId) {
                R.id.radio_time -> SortByTime()
                R.id.radio_status -> SortByStatusId()
                R.id.radio_area -> SortByAreaId()
                else -> SortByTime()
            }


            // TODO sorting throw empty list


            cardListManager.setSortStrategy(strategy)
            displaySortedCards(currentCards)

            Log.wtf(">>---- setting strategy", strategy.toString())

            Log.wtf(">>---- current cards", currentCards.toString())

        }
    }

    private fun displaySortedCards(cards: List<Card>) {
        val sortedCards = cardListManager.getSortedCards(cards)

        // Explicitly setting the data to ensure the adapter recognizes the update
        (binding.inWorkRv.adapter as? CardRecyclerViewAdapter)?.apply {
            this.cards = sortedCards
            notifyDataSetChanged() // Notify adapter of data change to refresh RecyclerView
        }

        if (sortedCards.isEmpty()) {
            showPlug()
        } else {
            showContent()
        }
    }


    private fun setUpObservers() {
        inWorkViewModel.cards.observe(viewLifecycleOwner) { cards: List<Card> ->

            currentCards = cards

            (binding.inWorkRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
            if (cards.isEmpty()) {
                showPlug()
            } else {
                showContent()
            }
        }

        displaySortedCards(currentCards)


    }





    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading() {
        with(binding) {
            progressBar.isVisible = true
            inWorkRv.isGone = true
            plug.isGone = true
        }
    }

    private fun showContent() {
        with(binding) {
            progressBar.isGone = true
            inWorkRv.isVisible = true
            plug.isGone = true
        }
    }

    private fun showPlug() {
        with(binding) {
            progressBar.isGone = true
            inWorkRv.isGone = true
            plug.isVisible = true
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
                inWorkViewModel.loadCards(userId)
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

    private fun showBottomSheetDialogFragmentRequestor(requestId: Int, stat: Int, date:String, spec: String, area: String, desc: String) {
        val role = "requestor"
        val requestorBottomSheetDialogFragment = RequestorBottomSheetDialogFragment(requestId, stat, role, date, spec, area, desc)
        requestorBottomSheetDialogFragment.show(parentFragmentManager, RequestorBottomSheetDialogFragment::class.java.simpleName)
    }
}