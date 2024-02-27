package com.example.artapp.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.artapp.R
import com.example.artapp.entities.Prompt

class PromptAddListAdapter: ListAdapter<Prompt, PromptAddListAdapter.PromptAddViewHolder>(
    PromptAddComparator()
) {
    override fun onBindViewHolder(holder: PromptAddViewHolder, position: Int) {
        val current = getItem(position)

        holder.bind(current.title)
        holder.promptDeleteView.setOnClickListener {
            val list = currentList.toMutableList()
            list.removeAt(position)
            submitList(list)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PromptAddViewHolder {
        return PromptAddViewHolder.create(parent)
    }

    class PromptAddViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val promptNameView: TextView = itemView.findViewById(R.id.prompt_add_name)
        val promptDeleteView: ImageButton = itemView.findViewById(R.id.prompt_add_delete)

        fun bind(text: String) {
            promptNameView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): PromptAddViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_prompt_add, parent, false)
                return PromptAddViewHolder(view)
            }
        }
    }

    class PromptAddComparator: DiffUtil.ItemCallback<Prompt>() {
        override fun areContentsTheSame(oldItem: Prompt, newItem: Prompt): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(oldItem: Prompt, newItem: Prompt): Boolean {
            return oldItem.title == newItem.title
        }
    }
}