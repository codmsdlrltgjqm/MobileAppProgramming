package com.example.prac1

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.prac1.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // 전달받은 값을 가져옴
        val receivedText = intent.getStringExtra("radiotext")

        // 전달받은 값을 제목으로 설정
        val titleTextView = findViewById<TextView>(R.id.add_title)
        titleTextView.text = "$receivedText 추가하기"

        binding.submitArea.setOnClickListener {
            val phoneNumber = binding.phonenumber.text.toString()
            val name = binding.nameText.text.toString()

            // 선택된 옵션에 따라서 '[친구]' 또는 '[장소]'를 추가
            val selectedOption = intent.getStringExtra("radiotext") ?: "" // 기본값은 빈 문자열
            val nameWithType = if (selectedOption == "친구") {
                "[친구] $name"
            } else if (selectedOption == "장소") {
                "[장소] $name"
            } else {
                name // 선택된 옵션이 없는 경우에는 이름만 반환
            }

            // 이름과 전화번호를 함께 반환
            val intent = Intent()
            intent.putExtra("name", nameWithType)
            intent.putExtra("phoneNumber", phoneNumber)
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