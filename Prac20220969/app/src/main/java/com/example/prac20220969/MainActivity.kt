package com.example.prac20220969

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.prac20220969.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}