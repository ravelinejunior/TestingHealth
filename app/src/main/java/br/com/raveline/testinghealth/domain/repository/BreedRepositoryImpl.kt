package br.com.raveline.testinghealth.domain.repository

import android.util.Log
import br.com.raveline.testinghealth.data.datasources.BreedLocalDataSource
import br.com.raveline.testinghealth.data.datasources.BreedRemoteDataSource
import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.utils.Resource
import retrofit2.Response

class BreedRepositoryImpl(
    private val breedRemoteDataSource: BreedRemoteDataSource,
    private val breedLocalDataSource: BreedLocalDataSource
) : BreedsRepository {

    override suspend fun getBreeds():List<BreedsItem>{
        return getBreedsFromDatabase()
    }

    override suspend fun getBreedsBySearch(query: String): List<BreedBySearchItem>{
       return getBreedsBySearchFromApi(query)
    }


    private suspend fun getBreedsFromApi(): Breeds {
        /*lateinit var breedsList: Breeds

        try {
            val response = breedRemoteDataSource.getBreeds()
            val body = response.body()

            if (body != null) {
                breedsList = body
            }

            return breedsList
        } catch (e: Exception) {
            Log.e("ApiRepositoryImpl", e.message.toString())
            return Breeds()
        }*/

        return breedRemoteDataSource.getBreeds().body()!!
    }

    private suspend fun getBreedsBySearchFromApi(
        query: String
    ): BreedsBySearch {
        lateinit var breedsList: BreedsBySearch

        try {
            val response = breedRemoteDataSource.getBreedsBySearch(query)
            val body = response.body()

            if (body != null && body.isNotEmpty()) {
                breedsList = body
            }

            return breedsList
        } catch (e: Exception) {
            Log.e("ApiRepositoryImpl", e.message.toString())
            return BreedsBySearch()
        }
    }

    private suspend fun getBreedsFromDatabase(): List<BreedsItem> {
        lateinit var breedsList: List<BreedsItem>

        try {
            breedsList = breedLocalDataSource.getBreeds()
        } catch (e: Exception) {
            Log.e("ApiRepositoryImpl", e.message.toString())
        }

        if (breedsList.isNotEmpty()) {
            return breedsList
        } else {
            breedsList = getBreedsFromApi()
            breedLocalDataSource.insertBreeds(breedsList)
        }

        return breedsList
    }



    private fun responseToResource(response: Response<Breeds>): Resource<Breeds> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


    private fun responseSearchToResource(response: Response<BreedsBySearch>):
            Resource<BreedsBySearch> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }


}