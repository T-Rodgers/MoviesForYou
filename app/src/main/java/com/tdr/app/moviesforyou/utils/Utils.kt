package com.tdr.app.moviesforyou.utils

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.annotation.RequiresApi
import timber.log.Timber
import java.text.ParseException
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
fun formatReleaseDate(date: String): String {
    var formattedDate = "null"
    try {
        val originalFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val dateFormatter = SimpleDateFormat("MMM d, yyyy", Locale.US)
        val releaseDate = originalFormat.parse(date)
        formattedDate = dateFormatter.format(releaseDate)
    } catch (e: ParseException) {
        Timber.e(e.message + "Date: $formattedDate")
    }
    return formattedDate

}