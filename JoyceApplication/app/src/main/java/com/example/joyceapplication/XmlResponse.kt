package com.example.joyceapplication

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

//xml로 넘어온 데이터를 처리해줄 수 있는 데이터 클래스

@Xml(name="response") // response를 만나면
data class XmlResponse(
    @Element // 내 안에 또 다른 element
    val body : myXmlBody
)

@Xml(name="body") // body 만나면 불려지는 클래스다 라는 뜻
data class myXmlBody(
    @Element
    val items : myXmlItems
)

@Xml(name="items")
data class myXmlItems(
    @Element
    val item : MutableList<myXmlItem>
)

@Xml(name="item")
data class myXmlItem(
    @PropertyElement
    val districtName : String?,
    @PropertyElement
    val issueDate : String?,
    @PropertyElement
    val issueTime : String?,
    @PropertyElement
    val issueGbn : String?,
) {
    constructor() :this(null, null, null, null)
}