package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.problemdesk.databinding.FragmentSubCompletedBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.domain.models.Specialization
import com.example.problemdesk.domain.models.Status
import com.example.problemdesk.domain.models.Workplace
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
            Card(Status.CANCELLED, "222", Specialization.INSTRUMENTS, Workplace.N1, "aboba"),
            Card(Status.APPROVED, "0", Specialization.DOCUMENTS, Workplace.N3, "amogus"),
            Card(Status.UNCHECKED, "111", Specialization.SANITARY_CONDITIONS, Workplace.N4, "хачю питсу"),
            Card(Status.COMPLETED, "111", Specialization.SANITARY_CONDITIONS, Workplace.N4, "vzlom zhopi")
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