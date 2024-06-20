package com.example.jetpackapplication

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jetpackapplication.databinding.FragmentTwoBinding
import com.example.jetpackapplication.databinding.ItemRecyclerviewBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class MyViewHolder(val binding:ItemRecyclerviewBinding) : RecyclerView.ViewHolder(binding.root) //바인딩 파일 이름 값 변경 가능
class MyAdapter(val datas:MutableList<String>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(ItemRecyclerviewBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) { //holder가 myViewHolder 인거
        // 전달 받은 바인드랑 데이터랑 연결
        val binding = (holder as MyViewHolder).binding //이 holder가 내가  클래스 만든 거
        binding.itemData.text = datas[position]
    }

    override fun getItemCount(): Int { //전달받은 데이터 리스트 관련
        return datas.size
    }
}

class MyItemDecoration(val context: Context) : RecyclerView.ItemDecoration(){
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 그림 ->항목
        super.onDraw(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            0f, 0f, null)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) { // 항목 -> 그림
        super.onDrawOver(c, parent, state)
        c.drawBitmap(BitmapFactory.decodeResource(context.resources, R.drawable.kbo),
            500f, 500f, null)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        view.setBackgroundColor(Color.parseColor("#123456")) //xml에서 android:background="#123456" 이 문장으로도 해결 가능

    }
}
/**
 * A simple [Fragment] subclass.
 * Use the [TwoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TwoFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentTwoBinding.inflate(inflater, container, false)

        var datas = mutableListOf<String>()//프로그램 실행동안 크기 바꾸기 위해  mutable로 한겨
        for(i in 1..10){
            datas.add("Item $i")
        }

        var adapter = MyAdapter(datas)
        // adapter & viewHolder 반드시 설정해야 함
        binding.recyclerView.adapter = adapter

        // layoutManager
        var linearlayout = LinearLayoutManager(activity)
        linearlayout.orientation = LinearLayoutManager.HORIZONTAL
        var gridlayout = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = gridlayout //linearlayout

        //선택적
        binding.recyclerView.addItemDecoration(MyItemDecoration(activity as Context))

        binding.mainFab.setOnClickListener{
            datas.add("Add Item")
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TwoFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            TwoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}