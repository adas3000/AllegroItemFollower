package com.example.observer

import com.example.observer.util.textToFloat
import org.junit.Test
import org.junit.Assert.*

class FormatTextPriceTest {

    @Test
    fun checkToFloatFun(){
        val f_value = "100.00".toFloat()

        assertEquals(100.00f,f_value)
    }

    @Test
    fun checkReplaceFun(){

        var str="name=someGuy123 age=21soon                     year=2020"
        str=str.replace(" ","")

        println(str)
        assertEquals("name=someGuy123age=21soonyear=2020",str)
    }

    @Test
    fun removeLettersTest(){
        var str:String = "1 159,00 zł"
        str = str.replace("zł", "")

        assertEquals("1 159,00 ",str)

    }

    @Test
    fun textToFloatTest(){

        var price_1:String = "489,00 zł"
        var price_2:String = "449,00 zł"
        var price_3:String = "1 159,00 zł"


        assertEquals(489.00f, textToFloat(price_1))
        assertEquals(449.00f, textToFloat(price_2))
        assertEquals(1159.00f, textToFloat(price_3))
    }


}