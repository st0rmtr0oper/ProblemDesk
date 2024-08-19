package com.example.problemdesk.presentation

import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import com.example.problemdesk.databinding.FragmentSubCardItemBinding
import com.example.problemdesk.domain.models.Card

//TODO remake UI

class CardRecyclerViewAdapter(private val cardListener: (Card) -> Unit) : RecyclerView.Adapter<CardsViewHolder>() {

    var cards: List<Card> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
//            notifyItemChanged(position)    --better performance, but idk how to use it
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubCardItemBinding.inflate(inflater, parent, false)
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(cards[position], cardListener)
    }

    override fun getItemCount(): Int = cards.size
}

class CardsViewHolder(private val binding: FragmentSubCardItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(card: Card, cancelledCardListener: (Card) -> Unit) {
        with(binding) {
            cardSpecialization.text = getSpecialization(card.requestType)
            cardArea.text = getArea(card.areaId)

            statusBar.setBackgroundColor(getStatusBarColor(card.statusId)) //TODO getStatusColor
            cardStatus.text = getStatus(card.statusId) //TODO fun returnStatus

            cardText.text = card.description
            cardDate.text = getDate(card.createdAt)
        }

        itemView.setOnClickListener {
            cancelledCardListener(card)
        }
    }
}