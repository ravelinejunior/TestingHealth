package br.com.raveline.testinghealth.data.datasources

import br.com.raveline.testinghealth.data.api.BreedsService
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import retrofit2.Response

class BreedRemoteDataSourceImpl(
    private val breedsService: BreedsService,
    ):BreedRemoteDataSource {
    override suspend fun getBreeds(): Response<Breeds> {
        return breedsService.getDogsBreed()
    }

    override suspend fun getBreedsBySearch(query: String): Response<BreedsBySearch> {
       return breedsService.getDogsBreedBySearch(query)
    }



}