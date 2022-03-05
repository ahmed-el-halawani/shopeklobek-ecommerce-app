package com.stash.shopeklobek.utils

fun Double.toFixed(places:Int=2):Double{
    return String.format("%.${places}f", this).toDouble()
}