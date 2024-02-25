package com.example.artapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.entities.Prompt

class PromptDisplayListAdapter: ListAdapter<Prompt, PromptDisplayListAdapter.PromptDisplayViewHolder>(
    PromptDisplayComparator()
) {
    override fun onBindViewHolder(holder: PromptDisplayViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.title)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromptDisplayViewHolder {
        return PromptDisplayViewHolder.create(parent)
    }

    class PromptDisplayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promptNameView: TextView = itemView.findViewById(R.id.promptlist_name)

        fun bind(text: String) {
            promptNameView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PromptDisplayViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_promptlist, parent, false)
                return PromptDisplayViewHolder(view)
            }
        }
    }

    class PromptDisplayComparator: DiffUtil.ItemCallback<Prompt>() {
        override fun areContentsTheSame(oldItem: Prompt, newItem: Prompt): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Prompt, newItem: Prompt): Boolean {
            return oldItem.title == newItem.title
        }
    }
}