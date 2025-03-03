package com.sw.sample.common

import java.text.SimpleDateFormat
import java.util.Locale

class AppUtils{


    companion object {
        fun getDateFormat(dateOfBirth: String?): String? {
           val inputFormat = SimpleDateFormat("dd-MM-yyyy", Locale.UK)
            val outputFormat = SimpleDateFormat("dd MMM yyyy", Locale.UK)
            val date = dateOfBirth?.let { inputFormat.parse(it) }
            return date?.let { outputFormat.format(it) }
        }
    }
}