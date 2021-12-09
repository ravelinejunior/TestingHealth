package br.com.raveline.testinghealth.domain.di

import br.com.raveline.testinghealth.domain.repository.BreedsRepository
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun providesGetBreedsUseCase(repository: BreedsRepository): GetBreedsUseCase =
        GetBreedsUseCase(repository)

}