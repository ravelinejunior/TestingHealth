package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.data.datasources.BreedLocalDataSource
import br.com.raveline.testinghealth.data.datasources.BreedRemoteDataSource
import br.com.raveline.testinghealth.domain.repository.BreedRepositoryImpl
import br.com.raveline.testinghealth.domain.repository.BreedsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun providesBreedRepository(
        breedLocalDataSource: BreedLocalDataSource,
        breedRemoteDataSource: BreedRemoteDataSource
    ): BreedsRepository =
        BreedRepositoryImpl(breedRemoteDataSource, breedLocalDataSource)

}