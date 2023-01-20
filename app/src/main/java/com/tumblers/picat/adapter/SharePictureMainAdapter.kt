package com.tumblers.picat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tumblers.picat.R

class SharePictureMainAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private val list = listOf<Unit>(Unit, Unit)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == TYPE_HEADER){
            val view = inflater.inflate(R.layout.activity_share_picture_header, parent, false)
            return SharePictureHeaderViewHolder(view)
        }
        else{
            val view = inflater.inflate(R.layout.item_picture, parent, false)
            return SharePictureItemViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = list.size


    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_ITEM
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    inner class SharePictureMainViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
        }

    inner class SharePictureHeaderViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){

    }

    inner class SharePictureItemViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){
    }




}