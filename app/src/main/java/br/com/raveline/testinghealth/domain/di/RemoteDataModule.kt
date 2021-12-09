package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.data.api.BreedsService
import br.com.raveline.testinghealth.data.datasources.BreedRemoteDataSource
import br.com.raveline.testinghealth.data.datasources.BreedRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Provides
    @Singleton
    fun provideBreedRemoteDataSource(breedsService: BreedsService):BreedRemoteDataSource =
        BreedRemoteDataSourceImpl(breedsService)


}