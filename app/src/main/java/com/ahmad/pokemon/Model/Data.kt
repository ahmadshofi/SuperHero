package com.ahmad.pokemon.Model


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val `data`: List<DataX>,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)