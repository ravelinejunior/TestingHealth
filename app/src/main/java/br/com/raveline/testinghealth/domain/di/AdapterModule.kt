package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.presentation.adapter.BreedSearchAdapter
import br.com.raveline.testinghealth.presentation.adapter.BreedsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {

    @Provides
    @Singleton
    fun providesBreedsAdapter():BreedsAdapter = BreedsAdapter()

    @Provides
    @Singleton
    fun providesBreedSearchAdapter():BreedSearchAdapter = BreedSearchAdapter()
}