package com.example.restaurant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


abstract class BaseAdpter<VB:ViewBinding , T:Any> (
    private val items:List<T>
        ): RecyclerView.Adapter<BaseAdpter.BaseViewHolder<VB>>() {

     class BaseViewHolder<VB:ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    // anonymous function
    abstract val bindingInFalter: (LayoutInflater, ViewGroup, Boolean) -> VB
    abstract fun bindItem(binding: VB, item: T)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<VB> {
        val inflate = LayoutInflater.from(parent.context)
        val view = bindingInFalter(inflate, parent, false)
        return BaseViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: BaseViewHolder<VB>, position: Int) {
         bindItem(holder.binding,items[position])


    }


}