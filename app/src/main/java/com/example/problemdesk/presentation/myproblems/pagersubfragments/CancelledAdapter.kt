package com.example.problemdesk.presentation.myproblems.pagersubfragments

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.problemdesk.databinding.FragmentSubCardItemBinding
import com.example.problemdesk.domain.models.Card

class CancelledAdapter(private val cancelledCardListener: (Card) -> Unit) : RecyclerView.Adapter<CancelledCardsViewHolder>() {
    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
//            notifyItemChanged(position)    --better performance, but idk how to use it
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CancelledCardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubCardItemBinding.inflate(inflater, parent, false)
        return CancelledCardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CancelledCardsViewHolder, position: Int) {
        holder.bind(cards[position], cancelledCardListener)
    }

    override fun getItemCount(): Int = cards.size
}

class CancelledCardsViewHolder(private val binding: FragmentSubCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Card, cancelledCardListener: (Card) -> Unit) {
        with(binding) {
            cardStatus.text = card.status.toString()
            cardSpecialization.text = card.specialization.toString()
            cardWorkplace.text = card.workplace.toString()
            cardDate.text = card.date
            cardText.text = card.taskText
        }

        itemView.setOnClickListener {
            cancelledCardListener(card)
        }
    }
}