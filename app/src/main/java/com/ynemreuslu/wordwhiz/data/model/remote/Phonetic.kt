package com.ynemreuslu.wordwhiz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Phonetic(
    @SerializedName("text") val text: String?,
    @SerializedName("audio") val audio: String?,
    @SerializedName("sourceUrl") val sourceUrl: String?,
    @SerializedName("license") val license: PhoneticLicense?
)
