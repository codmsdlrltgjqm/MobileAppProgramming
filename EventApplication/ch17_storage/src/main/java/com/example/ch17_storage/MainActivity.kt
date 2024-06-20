package com.example.ch17_storage

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch17_storage.databinding.ActivityMainBinding
import com.example.ch17_storage.MyAdapter
import java.io.BufferedReader
import java.io.File


import java.text.SimpleDateFormat



class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    // var datas: MutableList<String>? = null
    lateinit var adapter: MyAdapter
    lateinit var sharedPreference: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)// 우리가 만든 sharedPreference 가져올 수 ㅇㅅㅇ므
        val color=sharedPreference.getString("color", "#00ff00") // SharedPreference의 값 데려오기
        binding.lastsaved.setBackgroundColor(Color.parseColor(color)) //binding 내 어디서나 이제 color 속성 사용 가능

        val idStr = sharedPreference.getString("id", "")
        binding.todoTitle.text =idStr

        val size = sharedPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())


        val datas = mutableListOf<String>()

        val db = DBHelper(this). readableDatabase

        val cursor = db.rawQuery("select * from todo_tb", null)

        while(cursor.moveToNext()){
            datas?.add(cursor.getString(1))
        }

        db.close()

        adapter=MyAdapter(datas)
        binding.recyclerView.adapter=adapter
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager=layoutManager
        binding.recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))


        val requestLauncher: ActivityResultLauncher<Intent> = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){
            it.data?.getStringExtra("result")?.let {// "result"에 값이 저장되어 있으면(non-null)
                if(it != "") {
                    datas?.add(it)
                    adapter.notifyDataSetChanged()


                    val file = File(filesDir, "test.txt")

                    val readstream: BufferedReader = file.reader().buffered()

                    binding.lastsaved.text = "마지막 저장 시간 : " + readstream.readLine()
                }
            }
        }

        binding.mainFab.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)

            val dateFormat = SimpleDateFormat("yyyy-MM-dd") // 년 월 일
            intent.putExtra("today",dateFormat.format(System.currentTimeMillis()))

            requestLauncher.launch(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_setting, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId === R.id.menu_main_setting){
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent) //전달되는 값이 없어서 바로 startActivity 부를 수 있음
        }
        return super.onOptionsItemSelected(item)
    }

    //색상 설정 후 activity에 바로 반영하기
    override fun onResume() {
        super.onResume()

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)// 우리가 만든 sharedPreference 가져올 수 ㅇㅅㅇ므
        val color=sharedPreference.getString("color", "#00ff00") // SharedPreference의 값 데려오기
        binding.lastsaved.setBackgroundColor(Color.parseColor(color)) //binding 내 어디서나 이제 color 속성 사용 가능

        val idStr = sharedPreference.getString("id", "")
        binding.todoTitle.text =idStr

        val size = sharedPreference.getString("size", "16.0f")
        binding.lastsaved.setTextSize(TypedValue.COMPLEX_UNIT_DIP, size!!.toFloat())

    }
}