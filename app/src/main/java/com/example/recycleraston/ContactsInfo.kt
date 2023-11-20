package com.example.recycleraston

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactsInfo(
    val id: Int? = null,
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    var isSelected: Boolean = false
): Parcelable