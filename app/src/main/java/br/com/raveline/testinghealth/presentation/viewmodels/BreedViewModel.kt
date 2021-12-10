package br.com.raveline.testinghealth.presentation.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import br.com.raveline.testinghealth.data.model.BreedsItem
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase

class BreedViewModel(
    private val getBreedsUseCase: GetBreedsUseCase
) : ViewModel() {

    val isStaggered = mutableStateOf(false)
    var listBreeds = emptyList<BreedsItem>()

    fun getBreeds(page: Int) = liveData {
        val breeds = getBreedsUseCase.executeBreeds(page)
        listBreeds = breeds
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