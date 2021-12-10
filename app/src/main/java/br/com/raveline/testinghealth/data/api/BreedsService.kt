package br.com.raveline.testinghealth.data.api

import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import br.com.raveline.testinghealth.utils.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BreedsService {

    @GET("breeds")
    suspend fun getDogsBreed(
        @Query("page") page:Int,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<Breeds>

    @GET("breeds/search")
    suspend fun getDogsBreedBySearch(
        @Query("q") query: String,
        @Query("api_key") apiKey: String = API_KEY
    ): Response<BreedsBySearch>
}