package com.example.joyceapplication

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


//http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2020&pageNo=1&numOfRows=100&returnType=xml&serviceKey=서비스키

class RetrofitConnection {
    companion object{ //코틀린에서 companion은 전역변수 형태로 보면 됨 RetrofitConnection 클래스가 먼저 선언돼서 설정되지 않는다고 해도 부를 수 있고 값을 설정할 수 있는 변수
        private const val BASE_URL ="http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/"
        // 통신시작, json일 때
        var jsonNetServ : NetworkService
        val jsonRetrofit : Retrofit//Retrofit 객체는 Builder를 통해 만들어짐
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL) // 어떤 Retrofit이냐면 이 BASE_URL을 가지고 (request 하기 위한 baseurl)
                .addConverterFactory(GsonConverterFactory.create())//response해서 돌아온 문서를 json형태로 바꾸겟다
                .build()
        // 통신시작, xml일 때
        val xmlNetServ : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()
        init{
            jsonNetServ = jsonRetrofit.create(NetworkService::class.java) // 이렇게 하면 baseurl과 networkservice에 설정해둔 폴더와 쿼리에 대한 부분을 연결해줘서 하나의 request문을 만들어줌
            xmlNetServ = xmlRetrofit.create(NetworkService::class.java)
        }
    }


}