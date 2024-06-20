package com.example.joyceapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.joyceapplication.databinding.ItemMainBinding


class XmlViewHolder(val binding: ItemMainBinding): RecyclerView.ViewHolder(binding.root)
class XmlAdapter(val datas:MutableList<myXmlItem>?): RecyclerView.Adapter<RecyclerView.ViewHolder>() { // JsonAdapter에서 전달받은 매개변수 값이 myJsonResponse의 myJsonItems로 바뀜, JsonAdapter에서 전달받은 매개변수 값이 바뀌면 onBindViewHolder 수정해야함
    override fun getItemCount(): Int {
        return datas?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return XmlViewHolder(ItemMainBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {  //ViewHolder에 지정되어 잇는 itemmain이라하는 xml하고 adapter에서 전달받은 mutable리스트인 datas하고 연결해주는 역할
        //MutableList의 type이 이제는 string이 아닌 class이기 때문에 MutableList의 type이 String일 때처럼 바로 binding쪽에 값을 넘기는 게 아니라 객체를 받아서 세부적인 데이터로 넘겨줘야 함
        val binding = (holder as XmlViewHolder).binding
        val model = datas!![position]

        binding.name.text = model.districtName
        binding.issue.text = model.issueDate + " " + model.issueTime
        binding.issueGbn.text = model.issueGbn
    }
}