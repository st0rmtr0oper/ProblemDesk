package com.example.problemdesk.presentation.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.problemdesk.databinding.FragmentChartDetailsBottomSheetDialogBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.details.RequestorBottomSheetDialogFragment
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ChartDetailsBottomSheetDialogFragment(private val cards: List<Card>) : BottomSheetDialogFragment() {

    private var _binding: FragmentChartDetailsBottomSheetDialogBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance(cards: List<Card>) = ChartDetailsBottomSheetDialogFragment(cards)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChartDetailsBottomSheetDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardsRv.adapter = CardRecyclerViewAdapter(::handleLogClick)
        setUpCards(cards)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun handleLogClick(card: Card) {
        //TODO i dont need it
        showBottomSheetDialogFragmentRequestor(card.requestId, card.statusId, card.createdAt, card.requestType.toString(), card.areaId.toString(), card.description)
    }

    private fun showBottomSheetDialogFragmentRequestor(requestId: Int, stat: Int, date:String, spec: String, area: String, desc: String) {
        val role = "manager"
        val requestorBottomSheetDialogFragment = RequestorBottomSheetDialogFragment(requestId, stat, role, date, spec, area, desc)
        requestorBottomSheetDialogFragment.show(parentFragmentManager, RequestorBottomSheetDialogFragment::class.java.simpleName)
    }

    private fun setUpCards(cards: List<Card>) {
        (binding.cardsRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
    }
}