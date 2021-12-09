package br.com.raveline.testinghealth.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Keep
@Parcelize
@Entity(tableName = "breed_table")
data class BreedsItem(
    @SerializedName("bred_for")
    val bredFor: String?=String(),
    @SerializedName("breed_group")
    val breedGroup: String?=String(),

    @SerializedName("id")
    val id: Int,

    @PrimaryKey(autoGenerate = true)
    val idLocal:Int,

    @SerializedName("image")
    val image: @RawValue Image?=null,
    @SerializedName("life_span")
    val lifeSpan: String?= String(),
    @SerializedName("name")
    val name: String?=String(),
    @SerializedName("origin")
    val origin: String?=String(),
    @SerializedName("reference_image_id")
    val referenceImageId: String?=String(),
    @SerializedName("temperament")
    val temperament: String?=String(),

    @SerializedName("country_code")
    val countryCode: String?=null,
    @SerializedName("description")
    val description: String?=String(),
    @SerializedName("history")
    val history: String?=String()
) : Parcelable