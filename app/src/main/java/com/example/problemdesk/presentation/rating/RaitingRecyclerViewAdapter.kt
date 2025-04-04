package com.example.problemdesk.presentation.rating

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.problemdesk.databinding.FragmentSubRatingItemBinding
import com.example.problemdesk.domain.models.UserRating

class RaitingRecyclerViewAdapter(private val userRatingListener: (UserRating) -> Unit) :
    RecyclerView.Adapter<RaitingViewHolder>() {

    // Intrinsic data - immutable labels
    private val labels = IntrinsicLabels(
        nameLabel = "Имя:",
        specializationLabel = "Должность:",
        tokensLabel = "Токены:",
        numCreatedLabel = "Создано:",
        numCompletedLabel = "Выполнено:"
    )

    var userRatings: List<UserRating> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RaitingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubRatingItemBinding.inflate(inflater, parent, false)
        return RaitingViewHolder(binding, labels)
    }

    override fun onBindViewHolder(holder: RaitingViewHolder, position: Int) {
        holder.bind(userRatings[position], userRatingListener)
    }

    override fun getItemCount(): Int = userRatings.size
}

data class IntrinsicLabels(
    val nameLabel: String,
    val specializationLabel: String,
    val tokensLabel: String,
    val numCreatedLabel: String,
    val numCompletedLabel: String
)

class RaitingViewHolder(
    private val binding: FragmentSubRatingItemBinding,
    private val labels: IntrinsicLabels
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(userRating: UserRating, userRatingCardListener: (UserRating) -> Unit) {
        with(binding) {
            name.text = "${userRating.surname} ${userRating.name} ${userRating.middleName}"
            specialization.text = userRating.specialization
            tokens.text = userRating.tokens.toString()
            numCreated.text = userRating.numCreated.toString()
            numCompleted.text = userRating.numCompleted.toString()

            // Use intrinsic labels
            nameLabel.text = labels.nameLabel
            specializationLabel.text = labels.specializationLabel
            tokensLabel.text = labels.tokensLabel
            numCreatedLabel.text = labels.numCreatedLabel
            reasonLabel.text = labels.numCompletedLabel
        }
        itemView.setOnClickListener {
            userRatingCardListener(userRating)
        }
    }
}
