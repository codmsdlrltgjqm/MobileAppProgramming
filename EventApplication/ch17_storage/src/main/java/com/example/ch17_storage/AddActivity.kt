package com.example.ch17_storage

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.ch17_storage.databinding.ActivityAddBinding
import java.io.File
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.text.SimpleDateFormat

class AddActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddBinding
    lateinit var sharedPreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreference = PreferenceManager.getDefaultSharedPreferences(this)// 우리가 만든 sharedPreference 가져올 수 ㅇㅅㅇ므
        val color=sharedPreference.getString("color", "#00ff00") // SharedPreference의 값 데려오기
        binding.date.setTextColor(Color.parseColor(color))

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        binding.btnSave.setOnClickListener {
            val edt_srt = binding.addEditView.text.toString()
            val intent = intent
            intent.putExtra("result", edt_srt)
            setResult(Activity.RESULT_OK, intent)

            // db에 저장하기
            val db = DBHelper(this).writableDatabase
            db.execSQL("insert into todo_tb (todo) values (?)", arrayOf<String>(edt_srt))
            db.close()

            // 파일 저장하기
            val dateFormat = SimpleDateFormat("yyyy-MM-dd hh-mm-ss")
            val file = File(filesDir, "test.txt")

            val writestream : OutputStreamWriter = file.writer()
            writestream.write(dateFormat.format(System.currentTimeMillis()))
            writestream.flush()
            finish()
            true
        }
    } // onCreate()

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
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

    override fun onResume() {
        super.onResume()

        sharedPreference=PreferenceManager.getDefaultSharedPreferences(this)
        val color = sharedPreference.getString("color", "#00ff00")

        binding.date.setTextColor(Color.parseColor(color))
    }
}