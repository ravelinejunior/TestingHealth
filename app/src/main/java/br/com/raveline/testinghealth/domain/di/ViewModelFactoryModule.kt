package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase
import br.com.raveline.testinghealth.presentation.viewmodels.BreedViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {

    @Singleton
    @Provides
    fun providesBreedViewModelFactory(
        getBreedsUseCase: GetBreedsUseCase
    ):BreedViewModelFactory{
        return BreedViewModelFactory(getBreedsUseCase)
    }
}