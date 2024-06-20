package com.example.joyceapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.joyceapplication.databinding.FragmentJsonBinding
import com.example.joyceapplication.databinding.FragmentXmlBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [JsonFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class JsonFragment : Fragment() {
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
        // Inflate the layout for this fragment
        val binding = FragmentJsonBinding.inflate(inflater, container, false)
        val year = arguments?.getString("searchYear") ?: "2024"

        val call : Call<JsonResponse> = RetrofitConnection.jsonNetServ.getJsonList( // 결과를 전달받는 거
            year.toInt(),
            1,
            10, // 한 페이지에 10개의 데이터
            "json",
            "MASF2JpUHSPgsstr7YsnYuG3EeKJX1I63jikj58Hdzx54o0m7rLkPDwpH3e4x2+tUMIdO8Zrr2x2i96WR3fEnQ=="
        )

        //response되어진 문서를 처리하는 방법
        call?.enqueue(object : Callback<JsonResponse> {
            override fun onFailure(call: Call<JsonResponse>, t: Throwable) { // 통신이 실패했을 경우
                Log.d("mobileApp", "onFailure")
            }

            override fun onResponse(call: Call<JsonResponse>, response: Response<JsonResponse>) { //성공적으로 왔을 때
                if(response.isSuccessful){ // 성공적으로 온거니?
                    Log.d("mobileApp", "$response") // url이 잘 왔는지 확인 가능
                    Log.d("mobileApp", "${response.body()}") // 내가 정말 원하는 데이터
                    binding.jsonRecyclerView.adapter= JsonAdapter(response.body()?.response!!.body!!.items) //!!는 null이면 안 된다는 거
                    binding.jsonRecyclerView.layoutManager = LinearLayoutManager(activity)
                    binding.jsonRecyclerView.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))
                }
            }

        })

        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment JsonFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            JsonFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}