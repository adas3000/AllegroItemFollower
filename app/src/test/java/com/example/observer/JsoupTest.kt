package com.example.observer

import com.example.observer.enums.AllegroDivInstance
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.Test
import org.junit.Assert.*
import java.lang.NullPointerException

class JsoupTest {

    @Test
    fun checkPriceTest(){

        val url_1 = "https://allegro.pl/oferta/lenovo-thinkpad-x220-4gb-320gb-win7-klasa-a-8285685643?reco_id=8e55ff5d-3140-11ea-a198-246e9677fa08&sid=4ff40c840cdcf178556a07ba43c6f454bd9bac0cb20532ba39776a525078097b"
        val url_2 = "https://allegro.pl/oferta/lenovo-thinkpad-t410-4gb-320gb-nvida-1440x900x-8802496430?reco_id=96b3926c-3140-11ea-be48-b02628c7fac0&sid=041047f9c36843e364ecb91b45c568a2755aa386fe7e14ee7421a14291fbf951"
        val url_3 = "https://allegro.pl/oferta/asus-fx553v-i5-7300hq-gtx1050-fhd-15-6-usb3-bu396-8828144774"


        val doc_1 = Jsoup.connect(url_1).get()
        val doc_2 = Jsoup.connect(url_2).get()
        val doc_3 = Jsoup.connect(url_3).get()

        assertNotNull(doc_1)
        assertNotNull(doc_2)
        assertNotNull(doc_3)

        val elem_1 = doc_1.selectFirst(AllegroDivInstance.Instance.div)
        val elem_2 = doc_2.selectFirst(AllegroDivInstance.Instance.div)
        val elem_3 = doc_3.selectFirst(AllegroDivInstance.Instance.div)

        assertNotNull(elem_1)
        assertNotNull(elem_2)
        assertNotNull(elem_3)

        assertEquals("489,00 zł",elem_1.text())
        assertEquals("449,00 zł",elem_2.text())
        assertEquals("1 159,00 zł",elem_3.text())
    }


    @Test
    @Throws(NullPointerException::class)
    fun getExpiredDate(){

        val url_1 = "https://allegro.pl/oferta/asus-r510l-i5-8875874115"
        val url_2 = "https://allegro.pl/oferta/hit-lenovo-chromebook-4rdzeniowy-4gb-hdmi-bat-5h-8833258670"

        val doc_1 = Jsoup.connect(url_1).get()
        val doc_2 = Jsoup.connect(url_2).get()

        val elem_1 = doc_1.selectFirst("div._9a071_Phfa8")
        val elem_2 = doc_2.selectFirst("div._9a071_Phfa8")

        println(doc_1.location())

        println(elem_1.text())
        if(elem_2!=null)
        println(elem_2.text())

    }

}