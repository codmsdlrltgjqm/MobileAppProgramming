package com.example.mid20220969

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mid20220969.databinding.FragmentTwoBinding

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
    lateinit var binding: FragmentTwoBinding
    lateinit var radiotext:String
    var datas = mutableListOf<String>()

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

        binding = FragmentTwoBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )
        datas = savedInstanceState?.getStringArrayList("datas")?.toMutableList() ?: mutableListOf()

        binding.recyclerView.addItemDecoration(
            // 수평선으로 구별해주는
            DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        )


        // ActivityResultLauncher 등록
        val requestLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val phoneNumber = data?.getStringExtra("phoneNumber")
                val name = data?.getStringExtra("name")
                val combinedResult = "$name\n$phoneNumber"
                datas.add(combinedResult)// RecyclerView 갱신
            }
        }

        val items = arrayOf<String>("과일", "채소", "육류")
        var selected = 0
        val eventHandler1 = object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                val intent = Intent(requireContext(), AddActivity::class.java)
                if (which == DialogInterface.BUTTON_POSITIVE){
                    Log.d("mobileapp", "BUTTON_POSITIVE")
                    radiotext = "${items[selected]}"
                }
                intent.putExtra("radiotext", radiotext)
                requestLauncher.launch(intent)
            }
        }
        // FloatingActionButton 클릭 이벤트 설정
        binding.mainFab.setOnClickListener {
            AlertDialog.Builder(requireContext()).run(){
                setTitle("종류 선택")
                setIcon(android.R.drawable.ic_dialog_alert)
                setSingleChoiceItems(items, 1, object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Log.d("mobileapp", "${items[which]}선택")
                        selected =which
                    }
                })
                setPositiveButton("예", eventHandler1)
                setNegativeButton("아니오", eventHandler1)
                show()
            }
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