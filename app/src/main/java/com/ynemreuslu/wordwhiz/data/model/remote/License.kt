package com.ynemreuslu.wordwhiz.data.model.remote

import com.google.gson.annotations.SerializedName

data class License(
    @SerializedName("name") val name: String?,
    @SerializedName("url") val url: String?
)
