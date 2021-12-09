package br.com.raveline.testinghealth.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Height(
    @SerializedName("imperial")
    val imperial: String,
    @SerializedName("metric")
    val metric: String
)