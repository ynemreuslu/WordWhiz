package com.ynemreuslu.wordwhiz.data.model.remote

import com.google.gson.annotations.SerializedName

data class Meaning(
    @SerializedName("partOfSpeech") val partOfSpeech: String?,
    @SerializedName("definitions") val definitions: List<Definition>?,
    @SerializedName("synonyms") val synonyms: List<String>?,
    @SerializedName("antonyms") val antonyms: List<String>?,
)
