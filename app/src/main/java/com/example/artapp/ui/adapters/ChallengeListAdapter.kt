package com.example.artapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.entities.Challenge
import com.example.artapp.ui.ChallengeActivity

class ChallengeListAdapter : ListAdapter<Challenge, ChallengeListAdapter.ChallengeViewHolder>(
    ChallengeComparator()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChallengeViewHolder {
        return ChallengeViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ChallengeViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title, current.id)
    }

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val challengeItemView: TextView = itemView.findViewById(R.id.textView)

        fun bind(text: String?, id: Int) {
            challengeItemView.text = text
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChallengeActivity::class.java)
                intent.putExtra("challenge_id", id)
                startActivity(itemView.context, intent, null)
            }
        }

        companion object {
            fun create(parent: ViewGroup): ChallengeViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_challengelist, parent, false)
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