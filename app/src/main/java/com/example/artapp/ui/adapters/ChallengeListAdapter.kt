package com.example.artapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
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
        holder.bind(current)
    }

    class ChallengeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val challengeTextView: TextView = itemView.findViewById(R.id.challengelist_name)
        private val challengeCounterView: TextView = itemView.findViewById(R.id.challengelist_completion)
        private val challengeBarView: ProgressBar = itemView.findViewById(R.id.challengelist_bar)

        fun bind(challenge: Challenge) {
            challengeTextView.text = challenge.title
            challengeCounterView.text = "${challenge.completedPromptCount}/${challenge.promptCount}"

            if (challenge.promptCount == 0)
                challengeBarView.progress = 100
            else
                challengeBarView.progress = ((challenge.completedPromptCount.toDouble() / challenge.promptCount) * 100).toInt()
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ChallengeActivity::class.java)
                intent.putExtra("challenge_id", challenge.id)
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