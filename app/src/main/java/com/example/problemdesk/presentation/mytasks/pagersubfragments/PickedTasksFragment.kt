package com.example.problemdesk.presentation.mytasks.pagersubfragments

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
import com.example.problemdesk.databinding.FragmentSubPickedTasksBinding
import com.example.problemdesk.domain.models.Card
import com.example.problemdesk.presentation.CardRecyclerViewAdapter
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
        val userId = sharedPreferences?.getInt("user_id", 0)

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
        //TODO delete mocking
        Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}