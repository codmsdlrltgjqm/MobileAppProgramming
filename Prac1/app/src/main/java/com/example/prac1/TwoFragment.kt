package com.example.prac1

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.prac1.databinding.ActivityMainBinding
import com.example.prac1.databinding.FragmentTwoBinding
import com.example.prac1.databinding.ItemRecyclerviewBinding
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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
    lateinit var binding: FragmentTwoBinding
    var datas = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentTwoBinding.inflate(inflater, container, false)

        datas = savedInstanceState?.getStringArrayList("datas")?.toMutableList() ?: mutableListOf()

        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )

        val adapter = Adapter(datas)


        // ActivityResultLauncher 등록
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val phoneNumber = data?.getStringExtra("phoneNumber")
                val name = data?.getStringExtra("name")
                val combinedResult = "$name\n$phoneNumber"
                datas.add(combinedResult)
                adapter.notifyDataSetChanged() // RecyclerView 갱신
            }
        }

        // FloatingActionButton 클릭 이벤트 설정
        binding.mainFab.setOnClickListener {
            val intent = Intent(requireContext(), SecondActivity::class.java)

            // 라디오 그룹 내의 체크된 라디오 버튼을 찾아 해당 텍스트를 인텐트에 추가
            val radiotext = if (binding.radioFriend.isChecked) {
                binding.radioFriend.text.toString()
            } else {
                binding.radioPlace.text.toString()
            }

            intent.putExtra("radiotext", radiotext)

            // SecondActivity 실행
//            startActivity(intent)
            requestLauncher.launch(intent)
        }

// RecyclerView 설정
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        return binding.root
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
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