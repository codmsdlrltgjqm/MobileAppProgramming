package com.example.ch13_activity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ch13_activity.databinding.ItemRecyclerviewBinding

class MyViewHolder(val binding: ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    //?를 통해 null일수도 잇음을 알려줌, 계속 ? 써줘야함
class MyAdapter(val datas: MutableList<String>?): RecyclerView.Adapter<RecyclerView.ViewHolder>(){ // 2-1
    override fun getItemCount(): Int {
        return datas?.size ?:0;     // 2-2, ?:은 두 개가 합쳐서 하나의 키워드임
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as MyViewHolder).binding
        binding.itemData.text = datas!![position]      // 2-3, datas가 null인 경우에느 ㄴ연결ㄹ할게 없으므로  null 이 아닐 때만 바인딩해주겟다 한게 !!
    }
}
