package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentSubCancelledBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Specialization
import com.example.problemdesk.domain.OLDMODELSrefactor.Status
import com.example.problemdesk.domain.OLDMODELSrefactor.Card
import com.example.problemdesk.domain.OLDMODELSrefactor.Workplace
import com.example.problemdesk.presentation.CardRecyclerViewAdapter

class CancelledFragment : Fragment() {
    private var _binding: FragmentSubCancelledBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CancelledFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubCancelledBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
        binding.cancelledRv.adapter = CardRecyclerViewAdapter(::handleCardClick)

        //TODO delete mocking
        val cards = listOf(
            Card(Status.CANCELLED, "01.07.24", Specialization.DOCUMENTS, Workplace.N3, "проблема с документами")
        )
        (binding.cancelledRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
    }

    private fun handleCardClick(card: Card) {
        //TODO delete mocking
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}