package com.tapcrew.rickmorty.alfonso_sotelo.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * Base adapter to handle a list of Items
 * T: Entity type of the List
 * VH: ViewHolder inherited from BaseViewHolder
 * @param layoutRes Layout Resource Id
 * @param diffCallback an ItemCallback to differentiate between T objects, if T is a PrimaryKeyObject you can use DefaultDiffCallback()
 */
abstract class BaseAdapter<T: Any, VH: BaseAdapter<T, VH>.BaseViewHolder>(
    @LayoutRes private var layoutRes: Int,
    diffCallback: DiffUtil.ItemCallback<T>
): PagingDataAdapter<T, VH>(diffCallback) {

    var clickListener: ((entity: T?, viewHolder: VH) -> Unit)? = null
    var longClickListener: ((entity: T?, viewHolder: VH) -> Unit)? = null


    override fun onBindViewHolder(holder: VH, position: Int) {
        val element = getItem(position)

        holder.itemView.setOnClickListener {
            clickListener?.invoke(element, holder)
        }

        holder.itemView.setOnLongClickListener {
            longClickListener?.invoke(element, holder)
            true
        }

        holder.bind(element)
        onBindVH(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return instanceViewHolder(LayoutInflater.from(parent.context).inflate(layoutRes, parent, false))
    }

    /**
     * To add things to onBindViewHolder, child can be empty
     */
    protected abstract fun onBindVH(holder: VH, position: Int)

    /**
     * @return a new Instance of ViewHolder
     */
    protected abstract fun instanceViewHolder(view: View): VH


    /**
     * Base ViewHolder
     */
    abstract inner class BaseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        /**
         * In this method you have to load the data from the entity to the view
         */
        abstract fun bind(element: T?)
    }

    /**
     * This class is a Default Diff Item Callback for objects of PrimaryKeyObject type
     */
    class DefaultDiffCallback<T>: DiffUtil.ItemCallback<T>() where T: PrimaryKeyObject {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return if (oldItem.getPrimaryKey() != null || newItem.getPrimaryKey() != null)
                oldItem.getPrimaryKey() == newItem.getPrimaryKey()
            else
                oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem == newItem
        }

    }

}