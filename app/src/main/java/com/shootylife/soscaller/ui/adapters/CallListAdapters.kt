package com.shootylife.soscaller.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shootylife.soscaller.R

class CallListAdapters(private val list: List<String>): RecyclerView.Adapter<CallListAdapters.ExempleViewHolder>() {

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ExempleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_call, parent, false)
        return ExempleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExempleViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount() = list.size

    inner class ExempleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(string: String, position: Int) {
            itemView.findViewById<TextView>(R.id.callTextView).text = string
        }
    }
}