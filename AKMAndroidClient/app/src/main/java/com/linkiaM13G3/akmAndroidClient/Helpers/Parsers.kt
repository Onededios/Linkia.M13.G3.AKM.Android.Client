package com.linkiaM13G3.akmAndroidClient.Helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Parsers {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun parseISODateToYMD(date: String): String {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

            val dateTime = LocalDateTime.parse(date, inputFormatter)

            return dateTime.format(outputFormatter)
        }
    }
}