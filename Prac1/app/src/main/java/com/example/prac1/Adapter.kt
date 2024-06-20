package com.example.prac1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.prac1.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

class Adapter(private val datas: MutableList<String>): RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = datas[position]
        val parts = data.split("\n")

        if (parts.size >= 2) {
            holder.binding.itemData1.text = parts[0]
            holder.binding.itemData2.text = parts[1]
        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }
}
