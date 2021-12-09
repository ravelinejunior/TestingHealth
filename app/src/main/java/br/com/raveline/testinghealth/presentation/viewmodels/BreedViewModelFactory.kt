package br.com.raveline.testinghealth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase

class BreedViewModelFactory(
    private val getBreedsUseCase: GetBreedsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BreedViewModel::class.java))
            return BreedViewModel(getBreedsUseCase) as T
        throw IllegalArgumentException("Wrong view model")
    }
}