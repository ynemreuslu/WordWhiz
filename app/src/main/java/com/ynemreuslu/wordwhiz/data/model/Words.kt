package com.ynemreuslu.wordwhiz.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Words(
    val id : Int,
    val english: String,
    val turkish: String,
) : Parcelable