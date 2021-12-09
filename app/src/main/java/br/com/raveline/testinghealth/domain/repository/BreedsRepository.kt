package br.com.raveline.testinghealth.domain.repository

import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import br.com.raveline.testinghealth.data.model.BreedsItem

interface BreedsRepository {
    suspend fun getBreeds():List<BreedsItem>
    suspend fun getBreedsBySearch(query:String):List<BreedBySearchItem>
}