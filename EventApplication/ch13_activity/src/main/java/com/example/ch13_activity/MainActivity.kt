package com.example.ch13_activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch13_activity.databinding.ActivityMainBinding
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    var datas : MutableList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        datas = savedInstanceState?.let{
            it.getStringArrayList("datas")?.toMutableList()
        } ?: let{
            mutableListOf<String>()
        }


        val adapter = MyAdapter(datas)
        val layoutmanager = LinearLayoutManager(this)

        binding.recyclerView.adapter = adapter //MyAdapter(datas)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        )
        val requestLauncher:ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        { //ActivityResult가 it이라는 키워드를 씀
            //callback을 처리
            it.data!!.getStringExtra("result")?.let{
                if(it!=""){
                    datas?.add(it)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        binding.mainFab.setOnClickListener{
            val intent = Intent(this, AddActivity::class.java)
            val dateFormat = SimpleDateFormat("yyyy-MM-dd") //년월일
            intent.putExtra("today", dateFormat.format(System.currentTimeMillis()))

            //startActivity(intent)
            requestLauncher.launch(intent)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList("datas", ArrayList(datas))
    }
}