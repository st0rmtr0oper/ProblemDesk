package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentSubCompletedBinding
import com.example.problemdesk.domain.OLDMODELSrefactor.Card
import com.example.problemdesk.domain.OLDMODELSrefactor.Specialization
import com.example.problemdesk.domain.OLDMODELSrefactor.Status
import com.example.problemdesk.domain.OLDMODELSrefactor.Workplace
import com.example.problemdesk.presentation.CardRecyclerViewAdapter

class CompletedFragment : Fragment() {
    private var _binding: FragmentSubCompletedBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = CompletedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubCompletedBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //::handleCardClick binding RV click logic with fragment
        binding.completedRv.adapter = CardRecyclerViewAdapter(::handleCardClick)

        //TODO delete mocking
        val cards = listOf(
            Card("Выполнено", "27.06.24", "Санитарно-бытовые условия", "№4", "поменять лампочку")

//                    Card(Status.COMPLETED, "27.06.24", Specialization.SANITARY_CONDITIONS, Workplace.N4, "поменять лампочку")
        )
        (binding.completedRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
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