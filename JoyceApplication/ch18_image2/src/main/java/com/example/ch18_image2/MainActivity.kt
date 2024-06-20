package com.example.ch18_image2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ch18_image2.XmlResponse
import com.example.ch18_image2.databinding.ActivityMainBinding
import com.example.ch18_image2.XmlAdapter
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener { // Drawer 메뉴
    // DrawerLayout Toggle
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var binding:ActivityMainBinding
    lateinit var headerview : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 0
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // DrawerLayout Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawer,
            R.string.drawer_opened,
            R.string.drawer_closed
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState()

        // Drawer 메뉴
        binding.mainDrawerView.setNavigationItemSelectedListener(this)
        headerview = binding.mainDrawerView.getHeaderView(0) // 하나밖에 없으니까 0
        val button = headerview.findViewById<Button>(R.id.btnAuth)
        button.setOnClickListener{
            Log.d("mobileApp", "button.setOnclickllistener")
            val intent = Intent(this, AuthActivity::class.java)
            if(button.text.equals("로그인")){
                intent.putExtra("status", "logout")
            }
            else if (button.text.equals("로그아웃")){
                intent.putExtra("status", "login")
            }
            startActivity(intent)
            binding.drawer.closeDrawers()
        }

        // 본 화면 기능 작성
        binding.btnSearch.setOnClickListener {
        }

        binding.btnSearch.setOnClickListener{
            if(MyApplication.checkAuth()){
                val name = binding.edtName.text.toString()
                Log.d("mobileapp", name)

                val call: Call<XmlResponse> = RetrofitConnection.xmlNetworkService.getXmlList(
                    name,
                    1,
                    10,
                    "xml",
                    "MASF2JpUHSPgsstr7YsnYuG3EeKJX1I63jikj58Hdzx54o0m7rLkPDwpH3e4x2+tUMIdO8Zrr2x2i96WR3fEnQ==" // 일반인증키(+)
                )

                call?.enqueue(object : Callback<XmlResponse> {
                    override fun onResponse(call: Call<XmlResponse>, response: Response<XmlResponse>) {
                        if(response.isSuccessful){
                            Log.d("mobileApp", "$response")
                            Log.d("mobileApp", "${response.body()}")
                            binding.xmlRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
                            binding.xmlRecyclerView.adapter = XmlAdapter(response.body()!!.body!!.items!!.item)
                            binding.xmlRecyclerView.addItemDecoration(DividerItemDecoration(applicationContext, LinearLayoutManager.VERTICAL))
                        }
                    }

                    override fun onFailure(call: Call<XmlResponse>, t: Throwable) {
                        Log.d("mobileApp", "onFailure ${call.request()}")
                    }
                })
            }
            else{
                Toast.makeText(this, "인증을 먼저 진행해주세요..", Toast.LENGTH_LONG).show()
            }

        }

    }

    // DrawerLayout Toggle
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    // Drawer 메뉴
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.item_php -> {
                Log.d("mobileapp", "Php 메뉴")
                val intent = Intent(this, PhpActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_board -> {
                Log.d("mobileapp", "게시판 메뉴")
                val intent = Intent(this, BoardActivity::class.java)
                startActivity(intent)
                binding.drawer.closeDrawers()
                true
            }
            R.id.item_setting -> {
                Log.d("mobileapp", "설정 메뉴")
                binding.drawer.closeDrawers()
                true
            }

        }
        return false
    }
    override fun onStart(){
        super.onStart()

        val button = headerview.findViewById<Button>(R.id.btnAuth)
        val tv = headerview.findViewById<TextView>(R.id.tvID)

        if(MyApplication.checkAuth()){
            button.text = "로그아웃"
            tv.text ="${MyApplication.email}님 \n 반갑습니다"
        }
        else{
            button.text ="로그인"
            tv.text = "안녕하세요"
        }
    }

}