package com.example.advdev3.view.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.advdev3.R
import com.example.advdev3.model.data.SearchResult
import com.example.advdev3.utils.convertMeaningsToString
import kotlinx.android.synthetic.main.activity_main_recyclerview_item.view.*

class MainAdapter(private var onListItemClickListener: OnListItemClickListener):RecyclerView.Adapter<MainAdapter.RecyclerItemViewHolder>() {

    private var data: List<SearchResult> = arrayListOf()

    fun setData(data: List<SearchResult>) {
        this.data = data
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): MainAdapter.RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_main_recyclerview_item, parent, false) as View
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MainAdapter.RecyclerItemViewHolder, position: Int) {
        holder.bind(data[position])
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(data: SearchResult) {
            if (layoutPosition != RecyclerView.NO_POSITION) {
                itemView.header_textview_recycler_item.text = data.text
                itemView.description_textview_recycler_item.text = convertMeaningsToString(data.meanings!!)
                itemView.setOnClickListener { openInNewWindow(data) }
            }
        }
    }
    private fun openInNewWindow(listItemData: SearchResult) {
        onListItemClickListener.onItemClick(listItemData)
    }


    interface OnListItemClickListener {
        fun onItemClick(data: SearchResult)
    }

}
