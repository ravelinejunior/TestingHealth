package br.com.raveline.testinghealth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase

class BreedViewModel(
    private val getBreedsUseCase: GetBreedsUseCase
):ViewModel() {
    fun getBreeds() = liveData {
        val breeds = getBreedsUseCase.executeBreeds()
        emit(breeds)
    }

    fun getBreedsBySearch(query:String) = liveData {
        val breedsBySearch = getBreedsUseCase.executeBreedsById(query)
        emit(breedsBySearch)
    }

    fun getOrderedBreed() = liveData {
        val orderedBreed = getBreedsUseCase.executeOrderedBreeds()
        emit(orderedBreed)
    }
}