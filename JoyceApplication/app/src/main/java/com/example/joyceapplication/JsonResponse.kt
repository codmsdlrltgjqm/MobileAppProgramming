package com.example.joyceapplication
// 데이터를 받기 위한 데이터 클래스 만들기, 내가 받아오고자 하는 데이터의 구조에 따라 달라짐, 여기선, body랑 header가 있는데 header 내용은 안 가져옴

data class myJsonItems(val districtName:String, val issueDate:String, val issueTime:String, val issueGbn:String) // 내가 필요한 items 중 districtName, issuedate 등을 가져올거임
data class myJsonBody(val items: MutableList<myJsonItems>) // body 안에서 내가 필요한 items만 가져옴
data class myJsonResponse(val body : myJsonBody) // 내가 필요한 내용만 가져와도 됨, 즉, body만 받아올거임
data class JsonResponse(val response : myJsonResponse) // response를 통해 전달받음
