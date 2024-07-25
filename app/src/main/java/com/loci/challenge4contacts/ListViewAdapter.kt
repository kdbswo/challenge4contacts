package com.loci.challenge4contacts

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.loci.challenge4contacts.databinding.ItemRecyclerviewBinding
import com.loci.challenge4contacts.databinding.StarredItemRecyclerviewBinding

class ListViewAdapter : ListAdapter<Contact, RecyclerView.ViewHolder>(diffUtil) {

    inner class MyViewHolder(private val binding: ItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact) {
            binding.apply {
                recyclerImage.setImageDrawable(item.photo)
                recyclerName.text = item.name
                recyclerNumber.text = item.phoneNumber
            }
        }
    }


    inner class MyStarViewHolder(private val binding: StarredItemRecyclerviewBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Contact) {
            binding.apply {
                starredRecyclerImage.setImageDrawable(item.photo)
                starredRecyclerName.text = item.name
                starredRecyclerNumber.text = item.phoneNumber
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when(viewType){
            COMMON_CONTENT -> MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            STAR_CONTENT -> MyStarViewHolder(StarredItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else-> throw Exception("unknown type!!")
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = currentList[position]
        when(holder){
            is MyViewHolder -> holder.bind(item)
            is MyStarViewHolder -> holder.bind(item)
            else -> throw Exception("unknown holder type!!")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(currentList[position].starred.toInt()){
            0 -> COMMON_CONTENT
            1 -> STAR_CONTENT
            else -> throw Exception("unknown type!!")
        }
    }

    companion object {

        const val COMMON_CONTENT = 0
        const val STAR_CONTENT = 1

        val diffUtil = object : DiffUtil.ItemCallback<Contact>() {
            override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
                return oldItem == newItem
            }

        }
    }
}