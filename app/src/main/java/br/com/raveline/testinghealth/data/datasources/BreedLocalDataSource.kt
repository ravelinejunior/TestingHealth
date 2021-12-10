package br.com.raveline.testinghealth.data.datasources

import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.BreedsItem

interface BreedLocalDataSource {
    suspend fun getBreeds(): List<BreedsItem>
    suspend fun getBreedsBySearch():List<BreedBySearchItem>
    suspend fun getBreedsOrderedByName(): List<BreedsItem>
    suspend fun insertBreeds(breeds: List<BreedsItem>)
    suspend fun insertBreedsBySearch(breedsBySearch: List<BreedBySearchItem>)
}