package com.example.eventapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.KeyEvent
import android.widget.Toast
import com.example.eventapplication.databinding.ActivityMainBinding
import kotlinx.coroutines.processNextEventInCurrentThread

class MainActivity : AppCompatActivity() {
    var initTime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        var prevTime = 0L
        binding.startButton.text = "시작"
        binding.startButton.textSize = 24.0f

        binding.startButton.setOnClickListener{ //it 키워드는 startbutton을 받는 변수
            binding.chronometer.base = SystemClock.elapsedRealtime() + prevTime
            binding.chronometer.start()
            binding.startButton.isEnabled = false
            binding.stopButton.isEnabled = true
            binding.resetButton.isEnabled = true
        }
        binding.stopButton.setOnClickListener{ //it 키워드는 stopbutton을 받는 변수
            prevTime= binding.chronometer.base -SystemClock.elapsedRealtime()

            binding.chronometer.stop()
            binding.stopButton.isEnabled=false
            binding.startButton.isEnabled = true
            binding.resetButton.isEnabled = false
        }
        binding.resetButton.setOnClickListener{ //it 키워드는 startbutton을 받는 변수
            prevTime =0L
            binding.chronometer.stop()

            binding.resetButton.isEnabled = false
            binding.startButton.isEnabled = true
            binding.stopButton.isEnabled = true
        }
    }
    override fun onKeyDown(keyCode : Int , event:KeyEvent?):Boolean{
        when(keyCode){
            KeyEvent.KEYCODE_BACK -> {
                if(System.currentTimeMillis()-initTime > 3000) {//3초(밀리세컨 단위니끼)
                    Log.d("mobileapp"," Back Key가 눌렸어요 종료하려면 한 번 더 누르세요")
                    initTime = System.currentTimeMillis() //처음 BACK을 누른 시간이 저장

                    Toast.makeText(this,"Back Key가 눌렸어요 종료하려면 한 번 더 누르세요", Toast.LENGTH_LONG).show()
                    return true
                }
            }
            KeyEvent.KEYCODE_VOLUME_UP -> Log.d("mobileapp"," VOLUME-UP Key가 눌렸어요")
            KeyEvent.KEYCODE_VOLUME_DOWN -> Log.d("mobileapp"," VOLUME-DOWN Key가 눌렸어요")
        }
        return super.onKeyDown(keyCode, event)
    }
}