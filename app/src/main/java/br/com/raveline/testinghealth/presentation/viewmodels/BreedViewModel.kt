package br.com.raveline.testinghealth.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase

class BreedViewModel(
    private val getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val isStaggered = mutableStateOf(false)

    fun getBreeds(page: Int) = liveData {
        val breeds = getBreedsUseCase.executeBreeds(page)
        emit(breeds)
    }

    fun getBreedsFromDb() = liveData {
        val breeds = getBreedsUseCase.executeBreedsFromDb()
        emit(breeds)
    }

    fun getBreedsBySearch(query: String) = liveData {
        val breedsBySearch = getBreedsUseCase.executeBreedsById(query)
        emit(breedsBySearch)
    }

    fun getOrderedBreed() = liveData {
        val orderedBreed = getBreedsUseCase.executeOrderedBreeds()
        emit(orderedBreed)
    }
}