package com.example.ch18_image2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ch18_image2.databinding.ActivityHumanBinding

class HumanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityHumanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvName.text = intent.getStringExtra("name")
        binding.tvAge.text = intent.getStringExtra("age")
        binding.tvAddr.text = intent.getStringExtra("addr")
    }
}