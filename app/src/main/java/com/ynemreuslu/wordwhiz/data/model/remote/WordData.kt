package com.ynemreuslu.wordwhiz.data.model.remote

import com.google.gson.annotations.SerializedName

data class WordData(
    @SerializedName("word") val word: String? = null,
    @SerializedName("phonetic") val phonetic: String? = null,
    @SerializedName("phonetics") val phonetics: List<Phonetic>? = null,
    @SerializedName("meanings") val meanings: List<Meaning>? = null,
    @SerializedName("license") val license: License? = null,
    @SerializedName("sourceUrls") val sourceUrls: List<String>? = null
)