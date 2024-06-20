package com.example.mid20220969

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mid20220969.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 전달받은 값을 가져옴
        val receivedText = intent.getStringExtra("radiotext")

        // 전달받은 값을 제목으로 설정
        val titleTextView = findViewById<TextView>(R.id.add_title)
        titleTextView.text = "$receivedText"

        binding.submitArea.setOnClickListener {
            val number = binding.number.text.toString()
            val name = binding.nameText.text.toString()

            val selectedOption = intent.getStringExtra("radiotext") ?: "" // 기본값은 빈 문자열
            val WithType ="$selectedOption \n $name \n $number"

            val intent = Intent()
            intent.putExtra("all", WithType)
            setResult(Activity.RESULT_OK, intent)

            finish()
        }

        true
    }


    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }

}