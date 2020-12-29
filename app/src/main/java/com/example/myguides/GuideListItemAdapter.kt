package com.example.myguides

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myguides.common.GuideDescription

class GuideListItemAdapter(private val values: List<GuideDescription>, private val layout: Int, private val viewId: Int): RecyclerView.Adapter<GuideListItemAdapter.ViewHolder>() {
    override fun getItemCount() = values.size

    fun getByIndex(position: Int): GuideDescription {
        return values[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(layout, parent,false)
        return ViewHolder(itemView, viewId)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = values[position].name
    }

    class ViewHolder(itemView: View, viewId: Int) : RecyclerView.ViewHolder(itemView) {
        var textView: TextView = itemView.findViewById(viewId)
    }
}