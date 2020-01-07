package com.example.observer

import com.example.observer.util.isAllegroPage
import org.junit.Test
import org.junit.Assert.*

class IsAllegroPageTest {


    @Test
    fun is_AllegroPage_Fun_Ok(){

        val str_1 = "https://allegro.pl/oferta/lenovo-thinkpad-x201-i5-8830209137"
        val str_2 = "https://allegro.pl/oferta/laptop-dell-e7440-i5-8gb-ssd-windows-7923326019?bi_s=ads&bi_m=listing%3Adesktop%3Acategory&bi_c=YTY1NTRiZTYtYWIyNi00NzJiLTlmYWUtYWM5Njc0NDZiM2ViAA&bi_t=ape&referrer=proxy&emission_unit_id=40a796bc-6a41-439e-9568-0bc6bde8d7e7"
        val str_3 = "https://allegro.pl/oferta/kieszen-na-dysk-sata-ssd-hdd-2-5-12-7mm-8155619475"
        val str_4 = "oferta/kieszen-na-dysk-sata-ssd-hdd-2-5-12-7mm-8155619475/https://allegro.pl/oferta/"
        val str_5 = "https://allegro.pl/oferta"
        val str_6 ="ioasjdioqwjeqmpoiqfhttp://allegro.pl/oqwjeioqwje/oferta/"


        assertTrue(isAllegroPage(str_1))
        assertTrue(isAllegroPage(str_2))
        assertTrue(isAllegroPage(str_3))
        assertFalse(isAllegroPage(str_4))
        assertFalse(isAllegroPage(str_5))
        assertFalse(isAllegroPage(str_6))
    }

}