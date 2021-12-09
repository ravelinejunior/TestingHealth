package br.com.raveline.testinghealth.domain.usecase

import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.data.model.BreedsBySearch
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.domain.repository.BreedsRepository

class GetBreedsUseCase(
    private val breedsRepository: BreedsRepository
) {
    suspend fun executeBreeds():List<BreedsItem> = breedsRepository.getBreeds()
    suspend fun executeBreedsById(query:String): List<BreedBySearchItem> =
        breedsRepository.getBreedsBySearch(query)
}