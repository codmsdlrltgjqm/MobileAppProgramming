package com.example.joyceapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.joyceapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val jsonfragment = JsonFragment()
        val xmlfragment = XmlFragment()
        val bundle = Bundle() //fragment를 부를때 fragment에 edittext의 값을 전달해주기 위해

        binding.btnSearch.setOnClickListener {
            bundle.putString("searchYear", binding.edtYear.text.toString()) //searchYear 관련ㄴ된 값을  edtYear에 저장된 텍스트를  String으로 만들어서 bundleㅇㅔ 저장하겠다

            if (binding.rGroup.checkedRadioButtonId == R.id.rbJson) { //rbJson오면 jsonfragment 보여줄거임
                jsonfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, jsonfragment)
                    .commit()
            } else if (binding.rGroup.checkedRadioButtonId == R.id.rbXml) { //rbXml오면 xmlfragment 보여줄거임
                xmlfragment.arguments = bundle
                supportFragmentManager.beginTransaction()
                    .replace(R.id.activity_content, xmlfragment)
                    .commit()
            }
        }
    }
}