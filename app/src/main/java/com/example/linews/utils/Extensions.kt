package com.example.linews.utils

import android.content.Context
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun String.convertDate(): String{
    val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()).parse(this)
    val dateFormat: DateFormat = SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date!!)
}