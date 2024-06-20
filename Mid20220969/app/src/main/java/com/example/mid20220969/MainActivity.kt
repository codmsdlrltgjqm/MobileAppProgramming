package com.example.mid20220969

import android.content.Intent
import android.net.Uri
import com.example.mid20220969.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mid20220969.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding : ActivityMainBinding
    lateinit var toggle : ActionBarDrawerToggle

    class MyFragmentPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity){
        val fragments : List<Fragment>
        init{
            fragments =
                listOf(OneFragment(), TwoFragment()) // 총 3개의 fregment를 리스트로 담고 있다
        }

        // 반드시 포함해야하는 오버라이드 함수 -> getItemCount()
        override fun getItemCount(): Int {
            return fragments.size
        }
        // 반드시 포함해야하는 오버라이드 함수 -> createFragment()
        override fun createFragment(position: Int): Fragment {
            // position -> 0-> onefragment 1-> two 2-> three ...
            return fragments[position]
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)
        setSupportActionBar(binding.toolbar) // 툴바를 액션바로 설정

        // --- 토글 관련
        toggle = ActionBarDrawerToggle(this, binding.drawer, R.string.drawer_opened, R.string.drawer_closed)
        // 여기서 3번째 인자는 open 했을 때 나오는 string, 4번째 인자는 close 했을 때 나오는 string

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toggle.syncState() // 상태

        // NavigationView.OnNavigationItemSelectedListener 이것도 상속받아야함
        binding.mainDrawerView.setNavigationItemSelectedListener(this)



        // 프래그먼트 어댑터 생성
        val fragmentAdapter = MyFragmentPagerAdapter(this)
        binding.viewpager.adapter = fragmentAdapter

        // TabLayout과 ViewPager2 연결
        TabLayoutMediator(binding.tabs, binding.viewpager) { tab, position ->
            tab.text = "TAB ${position + 2}"
        }.attach()

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return true
    }


    // 좌측 상단 네비게이션의 메뉴들
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            // itemId 값에 따라 다르게 하겠다
            R.id.mail -> {
                val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:kmlee@ds.ac.kr"))
                startActivity(intent)
                true
            }
            R.id.map -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("geo:37.65198, 127.0162"))
                startActivity(intent)
                true
            }
            R.id.map1 -> {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/dir/덕성여자대학교/수유역"))
                startActivity(intent)
                true
            }
            R.id.call -> {
                // 전화 앱에 전화번호 보여주는 것만
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:/119"))
                startActivity(intent)

                true
            }
        }
        return false // 디폴트 리턴은 펄스
    }

}