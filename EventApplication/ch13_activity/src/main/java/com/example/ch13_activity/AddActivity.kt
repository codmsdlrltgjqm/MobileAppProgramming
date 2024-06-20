package com.example.ch13_activity

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch13_activity.databinding.ActivityAddBinding

class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var date = intent.getStringExtra("today")
        binding.date.text = date

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setOnClickListener {
            val intent = intent
            intent.putExtra("result", binding.addEditView.toString())
            setResult(Activity.RESULT_OK, intent) //누르면 자기가 눌럿던 곳으로 되돌아가

            finish()
            true
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = intent
        setResult(Activity.RESULT_OK, intent)
        finish()
        return true
    }
}

