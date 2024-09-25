package com.example.problemdesk.presentation.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.problemdesk.databinding.FragmentSubRatingItemBinding
import com.example.problemdesk.domain.models.UserRating

class RaitingRecyclerViewAdapter(private val userRatingListener: (UserRating) -> Unit) :
    RecyclerView.Adapter<RaitingViewHolder>() {

    var userRatings: List<UserRating> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
//            notifyItemChanged(position)    --better performance, but idk how to use it
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaitingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubRatingItemBinding.inflate(inflater, parent, false)
        return RaitingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RaitingViewHolder, position: Int) {
        holder.bind(userRatings[position], userRatingListener)
    }

    override fun getItemCount(): Int = userRatings.size
}

class RaitingViewHolder(private val binding: FragmentSubRatingItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(userRating: UserRating, userRatingCardListener: (UserRating) -> Unit) {
        with(binding) {
            name.text = "${userRating.surname} ${userRating.name} ${userRating.middleName}"
            specialization.text = userRating.specialization
            tokens.text = userRating.tokens.toString()
            numCreated.text = userRating.numCreated.toString()
            numCompleted.text = userRating.numCompleted.toString()
        }
        itemView.setOnClickListener {
            userRatingCardListener(userRating)
        }
    }
}