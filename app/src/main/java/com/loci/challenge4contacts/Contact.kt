package com.loci.challenge4contacts

import android.graphics.drawable.Drawable

data class Contact(
    val id: Int,
    val name: String,
    var photo: Drawable?,
    var phoneNumber: String
)
