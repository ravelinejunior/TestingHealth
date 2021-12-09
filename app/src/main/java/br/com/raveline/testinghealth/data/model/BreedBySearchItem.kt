package br.com.raveline.testinghealth.data.model


import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
@Entity(tableName = "bread_by_search_table")
data class BreedBySearchItem(
    @SerializedName("bred_for")
    val bredFor: String?="Unknown",
    @SerializedName("breed_group")
    val breedGroup: String?="Unknown",

    @SerializedName("id")
    val id: Int,

    @PrimaryKey(autoGenerate = true)
    val idLocal:Int,

    @SerializedName("life_span")
    val lifeSpan: String?="Unknown",

    @SerializedName("origin")
    val origin: String?="Unknown",

    @SerializedName("name")
    val name: String?="Unknown",
    @SerializedName("reference_image_id")
    val referenceImageId: String?="Unknown",
    @SerializedName("temperament")
    val temperament: String?="Unknown",

) : Parcelable