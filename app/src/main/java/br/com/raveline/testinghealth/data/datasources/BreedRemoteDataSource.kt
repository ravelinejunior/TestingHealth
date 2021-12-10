package br.com.raveline.testinghealth.data.datasources

import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import retrofit2.Response

interface BreedRemoteDataSource {
    suspend fun getBreeds(page:Int):Response<Breeds>
    suspend fun getBreedsBySearch(query:String):Response<BreedsBySearch>
}