package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.problemdesk.data.sharedprefs.PreferenceUtil
import com.example.problemdesk.data.sharedprefs.USER_ID
import com.example.problemdesk.data.sharedprefs.getSharedPrefsUserId
import com.example.problemdesk.databinding.FragmentSubCancelledBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.general.CardRecyclerViewAdapter
import kotlinx.coroutines.launch

class CancelledFragment : Fragment() {
    private var _binding: FragmentSubCancelledBinding? = null
    private val binding get() = _binding!!

    private val cancelledViewModel: CancelledViewModel by viewModels()

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
        setUpObservers()
        //::handleCardClick binding RV click logic with fragment
        binding.cancelledRv.adapter = CardRecyclerViewAdapter(::handleCardClick)
        val userId = context?.let { getSharedPrefsUserId(it) }
        lifecycleScope.launch {
            if (userId != null) {
                cancelledViewModel.loadCards(userId)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleCardClick(card: Card) {
        //TODO HANDLE CLICK
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
    }

    private fun setUpObservers() {
        cancelledViewModel.cards.observe(viewLifecycleOwner, Observer { cards: List<Card> ->
            (binding.cancelledRv.adapter as? CardRecyclerViewAdapter)?.cards = cards
        })
    }
}