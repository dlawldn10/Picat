package com.tumblers.picat.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.arasthel.spannedgridlayoutmanager.SpanSize
import com.arasthel.spannedgridlayoutmanager.SpannedGridLayoutManager
import com.bumptech.glide.Glide
import com.tumblers.picat.ImageViewPagerActivity
import com.tumblers.picat.R
import com.tumblers.picat.dataclass.ImageData

class PictureAdapter(private var imageDataList: ArrayList<ImageData>,
                     val mContext: Context,
                     var mSelected: HashSet<Int>,
                    var zoomSelected: HashSet<Int>)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var mClickListener: ItemClickListener? = null
    private var headerView: View? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        if (viewType == SharePictureMainAdapter.TYPE_HEADER){
            val view = inflater.inflate(R.layout.activity_share_picture_header, parent, false)
            headerView = view
            return HeaderViewHolder(view)
        }
        else{
            val view = inflater.inflate(R.layout.item_picture, parent, false)
            return PictureViewHolder(view)
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER
        else TYPE_ITEM
    }

    companion object {
        const val TYPE_HEADER = 0
        const val TYPE_ITEM = 1
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == TYPE_HEADER){

        }else {

            val holder = holder as PictureViewHolder
            Glide.with(mContext)
                .load(imageDataList[position].uri)
                .into(holder.imv)

            holder.itemView.setOnClickListener {
//            var intent = Intent(mContext, ImageViewPagerActivity::class.java)
//            intent.putExtra("imageList", imageDataList)
//            intent.putExtra("current", position)
//            mContext.startActivity(intent)
                if (zoomSelected.contains(position)) {
                    zoomSelected.remove(position)
                    notifyItemChanged(position)
                    Glide.with(mContext)
                        .load(imageDataList[position].uri)
                        .into(holder.imv)
                } else {
                    zoomSelected.add(position)
                    notifyItemChanged(position)
                    Glide.with(mContext)
                        .load(imageDataList[position].uri)
                        .into(holder.imv)

                }
            }

            // 화면 갱신 시 필요
            // 선택된 사진이면
            if (mSelected.contains(position)) {
                holder.itemView.isSelected = true
                holder.isSelectedButton.setImageResource(R.drawable.selected_icn)
            } else {
                holder.isSelectedButton.visibility = View.INVISIBLE
            }
            holder.zoomButton.visibility = View.INVISIBLE
        }

    }


    // 아이템 개수
    override fun getItemCount(): Int {
        return imageDataList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // ----------------------
    // Selection
    // ----------------------

    fun toggleSelection(view: View?, pos: Int) {
        val isSelectedButton = view?.findViewById<ImageButton>(R.id.is_selected_imagebutton)

        if (mSelected.contains(pos)) {
            view?.isSelected = false
            isSelectedButton?.setImageResource(R.drawable.unselected_icn)
            mSelected.remove(pos)
        } else {
            view?.isSelected = true
            isSelectedButton?.setImageResource(R.drawable.selected_icn)
            mSelected.add(pos)
        }
        notifyItemChanged(pos)
    }

    fun select(pos: Int, selected: Boolean) {
        if (selected) {
            mSelected.add(pos)
        } else {
            mSelected.remove(pos)
        }
        notifyItemChanged(pos)
    }


    fun selectRange(start: Int, end: Int, selected: Boolean) {
        for (i in start..end) {
            if (selected) {
                mSelected.add(i)
            } else{
                mSelected.add(i)
            }
        }
        notifyItemRangeChanged(start, end - start + 1)
    }

    fun deselectAll() {
        // this is not beautiful...
        mSelected.clear()
        notifyDataSetChanged()
    }

    fun selectAll() {
        for (i in 0 until imageDataList.size) mSelected.add(i)
        notifyDataSetChanged()
    }

    fun getCountSelected(): Int {
        return mSelected.size
    }

    fun getSelection(): HashSet<Int> {
        return mSelected
    }

    // ----------------------
    // Click Listener
    // ----------------------

    fun setClickListener(itemClickListener: ItemClickListener) {
        mClickListener = itemClickListener
    }

    interface ItemClickListener {
        fun onItemClick(view: View?, position: Int)
        fun onItemLongClick(view: View?, position: Int): Boolean
    }

    // ----------------------
    // ViewHolder
    // ----------------------
    inner class PictureViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView),
        View.OnClickListener, View.OnLongClickListener {
        var imv: ImageView
        var isSelectedButton: ImageButton
        var zoomButton: ImageButton

        init {
            imv = itemView.findViewById(R.id.imv)
            isSelectedButton = itemView.findViewById(R.id.is_selected_imagebutton)
            zoomButton = itemView.findViewById(R.id.zoom_imagebutton)
            itemView.setOnClickListener(this)
            itemView.setOnLongClickListener(this)
        }

        override fun onClick(view: View) {
            if (mClickListener != null) mClickListener!!.onItemClick(view, bindingAdapterPosition)
        }

        override fun onLongClick(view: View): Boolean {
            return if (mClickListener != null) mClickListener!!.onItemLongClick(
                view,
                bindingAdapterPosition
            ) else false
        }

    }


    inner class HeaderViewHolder(itemView: View)
        : RecyclerView.ViewHolder(itemView){

    }


}