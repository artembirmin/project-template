/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.adapter.databindingdiff

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Simple implementation of [RecyclerView.Adapter].
 * [V] is type of displayed item. It must be [ViewItem] implementation.
 */
abstract class DataBindingAdapter<B : ViewDataBinding, V : DataBindingViewItem<B>> :
    RecyclerView.Adapter<DataBindingViewHolder<B>>() {

    /**
     * List of displayed items.
     */
    open var items: MutableList<V> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    /**
     * Adds [item] to top of list.
     */
    fun addItemToTop(item: V) {
        items.add(0, item)
        notifyItemInserted(0)
    }

    /**
     * Removes all items from list.
     */
    fun clearAll() {
        items.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<B> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<B>(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<B>, position: Int) {
        items[holder.bindingAdapterPosition].bind(holder)
        items[holder.bindingAdapterPosition].onPositionChanged(position, itemCount)
    }

    override fun getItemViewType(position: Int): Int = items[position].getLayoutId()

    override fun getItemCount(): Int = items.size
}