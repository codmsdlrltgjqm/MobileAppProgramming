package com.example.ch18_image2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image2.databinding.ActivityPhpBinding
import com.google.gson.annotations.JsonAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPhpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPhp.setOnClickListener {
            var age = binding.etAge.text.toString() ?: ""
            val call: Call<PhpResponse> = RetrofitConnection.phpNetworkService.getPhpList(age)

            call?.enqueue(object : Callback<PhpResponse> {
                    override fun onFailure(call: Call<PhpResponse>, t: Throwable) { // 통신이 실패했을 경우
                        Log.d("mobileApp", "onFailure")
                    }

                    override fun onResponse(call: Call<PhpResponse>, response: Response<PhpResponse>) { //성공적으로 왔을 때
                        if(response.isSuccessful){ // 성공적으로 온거니?
                            Log.d("mobileApp", "$response") // url이 잘 왔는지 확인 가능
                            Log.d("mobileApp", "${response.body()}") // 내가 정말 원하는 데이터
                            binding.phpRecyclerView.adapter= PhpAdapter(this@PhpActivity, response.body()?.result!!) //!!는 null이면 안 된다는 거
                            binding.phpRecyclerView.layoutManager = LinearLayoutManager(this@PhpActivity)
                            binding.phpRecyclerView.addItemDecoration(DividerItemDecoration(this@PhpActivity, LinearLayoutManager.VERTICAL))
                        }
                    }

                })

        }
    }
}