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

    override suspend fun getBreeds(page: Int):List<BreedsItem>{
        return getBreedsFromDatabase(page)
    }

    override suspend fun getBreedsFromDb(): List<BreedsItem> {
        return breedLocalDataSource.getBreeds()
    }

    override suspend fun getBreedsBySearch(query: String): List<BreedBySearchItem>{
       return getBreedsBySearchFromApi(query)
    }

    override suspend fun getBreedsOrdered(): List<BreedsItem> {
        return getOrderedBreeds()
    }


    private suspend fun getBreedsFromApi(page:Int): Breeds {
        return breedRemoteDataSource.getBreeds(page).body()!!
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

    private suspend fun getBreedsFromDatabase(page:Int): List<BreedsItem> {
        lateinit var breedsList: List<BreedsItem>

        try {
            breedsList = breedLocalDataSource.getBreeds()
        } catch (e: Exception) {
            Log.e("ApiRepositoryImpl", e.message.toString())
        }

        if (breedsList.isNotEmpty()) {
            return breedsList
        } else {
            breedLocalDataSource.deleteBreeds()
            breedsList = getBreedsFromApi(page)
            breedLocalDataSource.insertBreeds(breedsList)
        }

        return breedsList
    }

     private suspend fun getOrderedBreeds(): List<BreedsItem>{
        lateinit var breedsList: List<BreedsItem>

        try {
          //  breedLocalDataSource.deleteBreeds()
            breedsList = breedLocalDataSource.getBreedsOrderedByName()
        } catch (e: Exception) {
            Log.e("ApiRepositoryImpl", e.message.toString())
        }

        if (breedsList.isNotEmpty()) {
            return breedsList
        } else {
            try{
                breedsList = getBreedsFromApi(0)
                breedLocalDataSource.deleteBreeds()
                breedLocalDataSource.insertBreeds(breedsList)
            }catch (e:Exception){
                e.printStackTrace()
            }

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