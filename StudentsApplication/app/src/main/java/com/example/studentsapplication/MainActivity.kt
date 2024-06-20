package com.example.studentsapplication

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.drawable.AnimationDrawable
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.studentsapplication.databinding.ActivityMainBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import java.util.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //애니메이션
        binding.btnTranslate.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.translate)
            binding.btnTranslate.startAnimation(anim)
        }

        binding.btnScale.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.scale)
            binding.btnScale.startAnimation(anim)
        }

        binding.btnRotate.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.rotate)
            binding.btnRotate.startAnimation(anim)
        }

        binding.btnAlpha.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.alpha)
            binding.btnAlpha.startAnimation(anim)
        }

        binding.btnWave.setOnClickListener {
            var anim = AnimationUtils.loadAnimation(this, R.anim.wave)
            binding.btnWave.startAnimation(anim)
        }

        var rocketAnimation : AnimationDrawable
        val rocketImage = binding.rocketImage.apply{
            setBackgroundResource(R.drawable.rocket)
            rocketAnimation = background as AnimationDrawable
        }

        rocketAnimation.start()

        // 유튜브
        val random = Random()
        val num = random.nextInt(5)
        lifecycle.addObserver(binding.youtubePlayerView)
        binding.youtubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
            override fun onReady(youTubePlayer: YouTubePlayer) {
                super.onReady(youTubePlayer)

                val videoId :String
                if(num>3) videoId= "96Gt_7XFTOY"
                else videoId ="V0OAgnHSo9Y"
                youTubePlayer.cueVideo(videoId, 0f)
            }
        })

        // 알람 notification
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions() ) {
            if (it.all { permission -> permission.value == true }) { // 퍼미션 허용으로 변경
                noti()
            }
            else {
                Toast.makeText(this, "permission denied...", Toast.LENGTH_SHORT).show()
            }
        }

        binding.notificationButton.setOnClickListener {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // 티라미수버전보다 높으면
                // 이 앱이 POST_NOTIFICATIONS 퍼미션을 획득했는지 확인
                if (ContextCompat.checkSelfPermission(this,"android.permission.POST_NOTIFICATIONS") == PackageManager.PERMISSION_GRANTED) {
                    noti()
                } // POST_NOTIFICATIONS 퍼미션 없으면
                else {
                    //사용자에게 POST_NOTIFICATIONS 퍼미션 허용해줘 물어보는 거, 위에 permissionLauncher 호출
                    permissionLauncher.launch( arrayOf( "android.permission.POST_NOTIFICATIONS"  ) )
                }
            }
            else {
                noti()
            }
        } // binding.notificationButton

    } // override fun onCreate
    fun noti(){
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder // 알림을  만드는  builder
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){     // 26 버전 이상, 알람을 줄 때 채널이라는 개념이 생김, 각 채널별로 하나씩 알림ㅇ르 보낼 수 잇음 = 한 번에 여러 종류의 알람을 보낼 수 있음 (채널만 다르게 한다면)
            val channelId="one-channel"
            val channelName="My Channel One"
            val channel = NotificationChannel( // 채널 생성
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {   // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)  // 앱 런처 아이콘 상단 상태바에 숫자 배지를 표시할지 여부를 지정
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) //RingtoneManager가 에뮬 돌렸을 때 잘 안될 수 있음 -> val url~setSound까지 주석처리
                val audioAttributes = AudioAttributes.Builder() // 사운드 설정
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes) // 알림에 소리 넣기
                enableVibration(true) // 알림에 진동 넣기
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)
            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)
        }
        else {  // 26 버전 미만 -> 바로 빌더 생성 가능
            builder = NotificationCompat.Builder(this)
        }

        // 알림의 기본 정보
        builder.run {
            setSmallIcon(R.drawable.small) // 알림 창에 뜨는 아이콘 등록
            setWhen(System.currentTimeMillis()) // 알림이 보내지는 시간 -> 여기선 현재시간
            setContentTitle("홍길동") // 알림 제목 작성
            setContentText("안녕하세요.") // 알림 문구 작성
            setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.big)) // 알림 이미지 추가
        }

        manager.notify(11, builder.build())
    }
}
