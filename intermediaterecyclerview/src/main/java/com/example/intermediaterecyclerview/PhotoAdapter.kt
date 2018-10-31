package com.example.intermediaterecyclerview

import android.support.annotation.IdRes
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.intermediaterecyclerview.models.PhotoRow
import com.example.intermediaterecyclerview.models.RowType

/**
 */
class PhotoAdapter(private var photoList: ArrayList<PhotoRow>) : RecyclerView.Adapter<PhotoAdapter.DefaultViewHolder>() {

    private var filteredPhotos = ArrayList<PhotoRow>()
    private var filtering = false

    override fun getItemCount(): Int {
        if (filtering) {
            return filteredPhotos.size
        }
        return photoList.size
    }

    override fun onBindViewHolder(holder: DefaultViewHolder, position: Int) {
        val photoRow: PhotoRow = if (filtering) {
            filteredPhotos[position]
        } else {
            photoList[position]
        }
        if (photoRow.type == RowType.PHOTO) {
            val photo = photoRow.photo
            Glide.with(holder.itemView.context)
                    .load(photo?.img_src)
                    .into(holder.getImage(R.id.camera_image))
            photo?.earth_date?.let { holder.setText(R.id.date, it) }
        } else {
            photoRow.header?.let { holder.setText(R.id.header_text, it) }
        }

        // Adding the row loading animation
        setAnimation(holder.itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView: View = when (viewType) {
            RowType.PHOTO.ordinal -> layoutInflater.inflate(R.layout.row_item, parent, false)
            else -> layoutInflater.inflate(R.layout.header_item, parent, false)
        }
        return DefaultViewHolder(inflatedView)
    }

    override fun getItemViewType(position: Int) =
            if (filtering) {
                filteredPhotos[position].type.ordinal
            } else {
                photoList[position].type.ordinal
            }

    // For filtering the camera
    fun filterCamera(camera: String) {
        filtering = true
        val newPhotos = photoList.filter { photo -> photo.type == RowType.PHOTO && photo.photo?.camera?.name.equals(camera) } as ArrayList<PhotoRow>
        DiffUtil.calculateDiff(PhotoRowDiffCallback(newPhotos, photoList), false).dispatchUpdatesTo(this)
        filteredPhotos = newPhotos
    }

    //For updating the views
    fun updatePhotos(newphotos: ArrayList<PhotoRow>) {
        DiffUtil.calculateDiff(PhotoRowDiffCallback(newphotos, photoList), false).dispatchUpdatesTo(this)
        photoList = newphotos
        clearFilter()
    }

    private fun clearFilter() {
        filtering = false
        filteredPhotos.clear()
    }

    // Removing the row
    fun removeRow(row: Int) {
        if (filtering) {
            filteredPhotos.removeAt(row)
        } else {
            photoList.removeAt(row)
        }

        //To show an animation for adding a row, make sure you use notifyItemAdded(position) instead of calling notifyDataChanged().
        // This lets the view know that just one row has been added and can animate that addition.For deleting, call notifyItemRemoved(position).

        notifyItemRemoved(row)
    }

    //For view loading animation
    private fun setAnimation(viewToAnimate: View) {
        if (viewToAnimate.animation == null) {
            val animation = AnimationUtils.loadAnimation(viewToAnimate.context, android.R.anim.slide_in_left)
            viewToAnimate.animation = animation
        }
    }

    class PhotoRowDiffCallback(private val newRows: List<PhotoRow>, private val oldRows: List<PhotoRow>) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow.type == newRow.type
        }

        override fun getOldListSize(): Int = oldRows.size

        override fun getNewListSize(): Int = newRows.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow == newRow
        }
    }


    class DefaultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val viewMap: MutableMap<Int, View> = HashMap()

        fun setText(@IdRes id: Int, text: String) {

            val view = (viewMap[id]
                    ?: throw IllegalArgumentException("View for $id not found")) as? TextView
                    ?: throw IllegalArgumentException("View for $id is not a TextView")
            view.text = text
        }


        fun getImage(@IdRes id: Int): ImageView {
            return (viewMap[id]
                    ?: throw IllegalArgumentException("View for $id not found")) as? ImageView
                    ?: throw IllegalArgumentException("View for $id is not a ImageView")
        }

        private fun findViewitems(itemView: View) {
            addToMap(itemView)
            if (itemView is ViewGroup) {
                val childCount = itemView.childCount
                (0 until childCount)
                        .map { itemView.getChildAt(it) }
                        .forEach { findViewitems(it) }
            }
        }

        private fun addToMap(itemView: View) {
            if (itemView.id == View.NO_ID) {
                itemView.id = View.generateViewId()
            }

            viewMap.put(itemView.id, itemView)
        }

    }


}