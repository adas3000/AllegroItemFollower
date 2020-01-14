package com.example.observer.util

import java.io.Serializable

interface ItemAdded:Serializable {
    fun setAdded(added:Boolean)
}