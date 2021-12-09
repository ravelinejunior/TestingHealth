package br.com.raveline.testinghealth.utils

import androidx.room.TypeConverter
import br.com.raveline.testinghealth.data.model.Image
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BreedTypeConverter {

    private var gson = Gson()

    //converte para string
    @TypeConverter
    fun imageToString(image: Image): String {
        return gson.toJson(image)
    }

    //converte string para recipes
    @TypeConverter
    fun stringToImage(imageString: String): Image {
        val listType = object : TypeToken<Image>() {}.type
        return gson.fromJson(imageString, listType)
    }


}