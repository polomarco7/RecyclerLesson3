package com.example.recycleraston

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.recycleraston.databinding.ListContactsBinding

class ContactsAdapter(
    private val onClick: (ContactsInfo) -> Unit
) : ListAdapter<ContactsInfo, ViewHolder>(DiffUtilCallback()) {

    private var mModelList: List<ContactsInfo>? = null
    var setDelState = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListContactsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mModelList = ArrayList(currentList)
        val item = getItem(position)
        val model = mModelList!![position]

        with(holder.binding) {
            idTextView.text = position.toString()
            nameTextView.text =
                holder.itemView.context.getString(R.string.name, item?.name, item?.surname)
            phoneTextView.text = item?.phoneNumber
        }

        holder.itemView.setBackgroundColor(
            if (model.isSelected) Color.CYAN else
                ContextCompat.getColor(holder.itemView.context, R.color.grey)
        )

        if (setDelState) {
            holder.itemView.setOnClickListener {
                model.isSelected = !model.isSelected
                holder.itemView.setBackgroundColor(
                    if (model.isSelected) Color.CYAN else
                        ContextCompat.getColor(holder.itemView.context, R.color.grey)
                )
            }
        } else {
            holder.itemView.setOnClickListener {
                item?.let {
                    onClick(item)
                }
            }
        }
    }

    fun updateAll(newList: List<ContactsInfo>) {
        submitList(newList)
    }

    fun add(contactsInfo: ContactsInfo, position: Int) {
        val newItems = ArrayList(currentList)
        newItems.add(position, contactsInfo)
        val changedCount = itemCount - position + 1
        submitList(newItems) {
            notifyItemRangeChanged(position, changedCount)
        }
    }

    fun updateContact(contactsInfo: ContactsInfo?) {
        val newItems = ArrayList(currentList)

        if (contactsInfo == null) return
        for (item in newItems) {
            if (item.id == contactsInfo.id) {
                val position = contactsInfo.id?.minus(1)
                newItems[position!!] = contactsInfo
                submitList(newItems) {
                    notifyItemChanged(position)
                }
                break
            }
        }
    }
    fun cancelSelect() {
        val newItems = ArrayList(currentList)
        for (item in newItems){
            if(item.isSelected)
                item.isSelected = false
        }
        submitList(newItems) {
            notifyDataSetChanged()
        }
    }

    fun removeSelected(){
        val newItems = ArrayList(currentList)
        newItems.removeAll{it.isSelected}
        submitList(newItems) {
            notifyDataSetChanged()
        }
    }
}

class ViewHolder(val binding: ListContactsBinding) : RecyclerView.ViewHolder(binding.root)

class DiffUtilCallback : DiffUtil.ItemCallback<ContactsInfo>() {
    override fun areItemsTheSame(oldItem: ContactsInfo, newItem: ContactsInfo): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ContactsInfo, newItem: ContactsInfo): Boolean =
        oldItem == newItem
}