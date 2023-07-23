package com.example.artapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.model.Challenge

class ChallengeListAdapter : ListAdapter<Challenge, ChallengeListAdapter.ChallengeViewHolder>(ChallengeComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title)
    }

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val challengeItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?) {
            challengeItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): ChallengeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.recyclerview_item, parent, false)
                return ChallengeViewHolder(view)
            }
        }
    }

    class ChallengeComparator : DiffUtil.ItemCallback<Challenge>() {
        override fun areItemsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Challenge, newItem: Challenge): Boolean {
            return oldItem.title == newItem.title
        }
    }
}