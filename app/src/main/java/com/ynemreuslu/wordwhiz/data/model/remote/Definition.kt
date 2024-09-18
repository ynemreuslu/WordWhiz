package com.ynemreuslu.wordwhiz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Definition(
    @SerializedName("definition") val definition: String?,
    @SerializedName("synonyms") val synonyms: List<String>?,
    @SerializedName("antonyms") val antonyms: List<String>?,
    @SerializedName("example") val example: String?,
)