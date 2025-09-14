@file:Suppress("DEPRECATION")

package com.example.movamovieapp.util

import android.content.Context
import android.content.res.Configuration
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.movamovieapp.R
import java.util.Locale


fun ImageView.loadImageUrl(url: String?){
    url?.let {
        val fullUrl ="https://image.tmdb.org/t/p/original" + it
        Glide.with(this.context)
            .load(fullUrl)
            .into(this)
    }
}
fun ImageView.loadImageYoutube(key: String?){
    key?.let {
        val youtubeUrl = "https://img.youtube.com/vi/"+it+"/hqdefault.jpg"
        Glide.with(this.context)
            .load(youtubeUrl)
            .into(this)
    }
}
fun View.visible(){
    this.visibility=View.VISIBLE
}
fun View.gone(){
    this.visibility=View.GONE

}

fun maskCardNumberGrouped(cardNumber: String): String {
    if (cardNumber.length < 4) return cardNumber

    val last4 = cardNumber.takeLast(4)
    val groups = (cardNumber.length - 4) / 4 // neçə tam qrup var
    val remainder = (cardNumber.length - 4) % 4 // artıq qalan rəqəmlər

    val maskedGroups = buildString {
        repeat(groups) {
            append("**** ")
        }
        if (remainder > 0) {
            append("*".repeat(remainder)).append(" ")
        }
        append(last4.chunked(4).joinToString(" "))
    }
    return maskedGroups.trim()
}






//
//
//// ✅ Kart nömrəsini maskalama
//fun maskCardNumber(cardNumber: String): String {
//    return if (cardNumber.length > 4) {
//        "*".repeat(cardNumber.length - 4) + cardNumber.takeLast(4)
//    } else {
//        cardNumber
//    }
//}
//
//// ✅ Qiyməti formatlama (Məs: $12.99)
//fun formatPrice(price: Double): String {
//    val formatter = NumberFormat.getCurrencyInstance(Locale.US)
//    return formatter.format(price)
//}
//
//// ✅ Tarixi formatlama (Məs: 2025-09-07 → Sep 07, 2025)
//fun formatDate(dateString: String, inputPattern: String = "yyyy-MM-dd"): String {
//    return try {
//        val inputFormat = SimpleDateFormat(inputPattern, Locale.getDefault())
//        val outputFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
//        val date = inputFormat.parse(dateString)
//        outputFormat.format(date!!)
//    } catch (e: Exception) {
//        dateString // parse alınmasa orijinalı qaytarırıq
//    }
//}
//}
