package com.example.problemdesk.presentation.details

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.problemdesk.databinding.FragmentSubLogItemBinding
import com.example.problemdesk.domain.models.RequestLog
import com.example.problemdesk.presentation.general.getDate
import com.example.problemdesk.presentation.general.getStatus

class DetailsRecyclerViewAdapter(private val logListener: (RequestLog) -> Unit) :
    RecyclerView.Adapter<CardsViewHolder>() {

    var logs: List<RequestLog> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
//            notifyItemChanged(position)    --better performance, but idk how to use it
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FragmentSubLogItemBinding.inflate(inflater, parent, false)
        return CardsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardsViewHolder, position: Int) {
        holder.bind(logs[position], logListener)
    }

    override fun getItemCount(): Int = logs.size
}

class CardsViewHolder(private val binding: FragmentSubLogItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(log: RequestLog, cancelledCardListener: (RequestLog) -> Unit) {
        with(binding) {
            status.text = getStatus(log.newStatusId)
            time.text = getDate(log.changedAt)
            name.text = log.changerName
            if (log.reason != null && log.reason != "") {
                reason.text = log.reason
                reasonCombined.isVisible = true
            }
        }
        itemView.setOnClickListener {
            cancelledCardListener(log)
        }
    }
}