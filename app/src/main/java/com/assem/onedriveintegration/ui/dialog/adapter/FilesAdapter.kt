package com.assem.onedriveintegration.ui.dialog.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.assem.onedriveintegration.databinding.ItemFileRowBinding
import com.assem.onedriveintegration.domain.entity.OneDriveItem
import com.assem.onedriveintegration.utils.getFileIcon

class FilesAdapter(
    private val onItemClick: (OneDriveItem) -> Unit,
) : RecyclerView.Adapter<FilesAdapter.ViewHolder>() {

    private var dataList: List<OneDriveItem> = emptyList()

    class ViewHolder(private val binding: ItemFileRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: OneDriveItem,
            onItemClick: (OneDriveItem) -> Unit
        ) {
            binding.run {
                tvFileName.text = item.name
                ivFileIcon.getFileIcon(item.extension)
                root.setOnClickListener { onItemClick(item) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFileRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position], onItemClick)
    }

    override fun getItemCount(): Int = dataList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(data: List<OneDriveItem>) {
        dataList = data
        notifyDataSetChanged()
    }
}
