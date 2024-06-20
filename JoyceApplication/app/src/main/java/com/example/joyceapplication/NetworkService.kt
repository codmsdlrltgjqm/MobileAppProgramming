package com.example.joyceapplication

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//http://apis.data.go.kr/B552584/UlfptcaAlarmInqireSvc/getUlfptcaAlarmInfo?year=2020&pageNo=1&numOfRows=100&returnType=xml&serviceKey=서비스키
interface NetworkService {  // 기본 url 다음 주소를 만들어줌, 어느 폴더에서 가져오고 Query 내용 어떻게 만들지 선언해주는 역할

    //데이터 타입이 json일 때
    @GET("getUlfptcaAlarmInfo") // @GET은 http에서 get 방식으로 데이터를 불러오겠다, request하는 방식을 get으로 하겠다, () 안에는 endpoint로 주어진 그 베이스 url에서 어느 주소로 갈지 알려주느 ㄴ거
    fun getJsonList( // 그리고 그 주소로 갔을 때 이 함수를 실행시키겠다
        @Query("year") year:Int,// 매개변수 전달하는 것 , year는 url 주소에서 ? 뒤에 있음 ex) ?year=값 (매개변수가 하나 이상인 경우 &로 연결)
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") serviceKey:String,
    ) : Call<JsonResponse> // 리턴값의 형태

    //데이터 타입이 xml일 때
    @GET("getUlfptcaAlarmInfo")
    fun getXmlList(
        @Query("year") year:Int,// 매개변수 전달하는 것 , year는 url 주소에서 ? 뒤에 있음 ex) ?year=값 (매개변수가 하나 이상인 경우 &로 연결)
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("returnType") returnType:String,
        @Query("serviceKey") serviceKey:String,
    ) : Call<XmlResponse>
}