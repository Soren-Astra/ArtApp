package com.example.artapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.entities.Prompt

class PromptDisplayListAdapter(private val toggleCallback: (Int) -> Unit): ListAdapter<Prompt, PromptDisplayListAdapter.PromptDisplayViewHolder>(
    PromptDisplayComparator()
) {
    override fun onBindViewHolder(holder: PromptDisplayViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current, toggleCallback)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromptDisplayViewHolder {
        return PromptDisplayViewHolder.create(parent)
    }

    class PromptDisplayViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promptNameView: TextView = itemView.findViewById(R.id.prompt_list_name)
        private val promptCardView: CardView = itemView.findViewById(R.id.prompt_list_card)

        fun bind(prompt: Prompt, toggleCallback: (Int) -> Unit) {
            promptNameView.text = prompt.title
            promptCardView.setOnClickListener {
                toggleCallback(prompt.id)
                setCardColor(prompt.isDone)
            }
            setCardColor(prompt.isDone)
        }

        private fun setCardColor(isPromptDone: Boolean)
        {
            when(isPromptDone)
            {
                true -> promptCardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.sandy_brown))
                false -> promptCardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.light_gray))
            }
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