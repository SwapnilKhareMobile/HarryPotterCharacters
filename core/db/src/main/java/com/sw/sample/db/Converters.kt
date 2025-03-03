package com.sw.sample.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    @TypeConverter
    fun convertInvoiceListToJSONString(list: List<String>): String = Gson().toJson(list)
    @TypeConverter
    fun convertJSONStringToInvoiceList(jsonString: String): List<String> = Gson().fromJson(jsonString,object : TypeToken<List<String>>() {}.type)

}