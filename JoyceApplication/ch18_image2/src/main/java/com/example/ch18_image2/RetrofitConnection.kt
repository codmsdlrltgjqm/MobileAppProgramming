package com.example.ch18_image2

import com.example.ch18_image2.NetworkService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// https://apis.data.go.kr/B553748/CertImgListServiceV3/getCertImgListServiceV3?serviceKey=
class RetrofitConnection{

    //객체를 하나만 생성하는 싱글턴 패턴을 적용합니다.
    companion object {
        //API 서버의 주소가 BASE_URL이 됩니다.
        //http://localhost/PHP_connection.php

        private const val BASE_URL_Php = "http://10.207.17.75/" //cmd에서 ipconfig -> ipv4
        var phpNetworkService : NetworkService
        val phpRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL_Php)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        private const val BASE_URL = "https://apis.data.go.kr/B553748/CertImgListServiceV3/"

        var xmlNetworkService : NetworkService
        val parser = TikXml.Builder().exceptionOnUnreadXml(false).build()
        val xmlRetrofit : Retrofit
            get() = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(TikXmlConverterFactory.create(parser))
                .build()

        init{
            phpNetworkService = phpRetrofit.create(NetworkService::class.java)
            xmlNetworkService = xmlRetrofit.create(NetworkService::class.java)
        }
    }
}