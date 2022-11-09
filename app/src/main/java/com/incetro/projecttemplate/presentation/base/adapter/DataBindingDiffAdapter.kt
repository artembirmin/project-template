/*
 * ProjectTemplate
 *
 * Created by artembirmin on 9/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

/**
 * Base implementation of [ListAdapter] using `DataBinding`.
 * [B] is type of DataBinding generated class for your [DataBindingViewItem] layout.
 * [V] is type of displayed item. It must be [DataBindingViewItem] implementation.
 */
open class DataBindingDiffAdapter<B : ViewDataBinding, V : DataBindingViewItem<B>>(
    diffUtilCallback: DiffUtil.ItemCallback<V>
) : ListAdapter<V, DataBindingViewHolder<B>>(diffUtilCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<B> {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<B>(inflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<B>, position: Int) {
        getItem(holder.bindingAdapterPosition)?.bind(holder)
        getItem(holder.bindingAdapterPosition)?.onPositionChanged(position, itemCount)
    }

    override fun getItemViewType(position: Int): Int = getItem(position)!!.getLayoutId()
}