/*
 * ProjectTemplate
 *
 * Created by artembirmin on 6/11/2022.
 */

package com.incetro.projecttemplate.presentation.base.adapter.databindingdiff

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * [RecyclerView.ViewHolder] implementation using DataBinding.
 * [B] is type of DataBinding generated class for your [DataBindingViewItem] layout.
 * [binding] is instance of [B].
 */
class DataBindingViewHolder<B : ViewDataBinding>(val binding: B) :
    RecyclerView.ViewHolder(binding.root)
