package com.linkiaM13G3.akmAndroidClient.Helpers

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField

class Parsers {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        fun parseISODateToYMD(date: String): String {
            val inputFormatter = DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .appendFraction(ChronoField.MICRO_OF_SECOND, 0, 7, true)
                    .toFormatter()
            val outputFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd")

            val dateTime = LocalDateTime.parse(date, inputFormatter)

            return dateTime.format(outputFormatter)
        }



        fun parsePasswordSecure(pass: String): String {
            var formatted = ""
            for (char in pass) formatted += "*"
            return formatted
        }
    }
}