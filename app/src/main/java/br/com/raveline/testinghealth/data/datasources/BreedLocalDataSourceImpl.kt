package br.com.raveline.testinghealth.data.datasources

import br.com.raveline.testinghealth.data.database.dao.BreedsDao
import br.com.raveline.testinghealth.data.model.BreedBySearchItem
import br.com.raveline.testinghealth.data.model.BreedsItem

class BreedLocalDataSourceImpl(
    private val dao: BreedsDao
) : BreedLocalDataSource {
    override suspend fun getBreeds(): List<BreedsItem> {
        return dao.getAllBreeds()
    }

    override suspend fun getBreedsBySearch(): List<BreedBySearchItem> {
        return dao.getBreedsBySearch()
    }

    override suspend fun deleteBreeds() {
        dao.deleteFromBreeds()
    }

    override suspend fun getBreedsOrderedByName(): List<BreedsItem> {
        return dao.getAllBreedsByName()
    }

    override suspend fun insertBreeds(breeds: List<BreedsItem>) {
        return dao.insertBreeds(breeds)
    }

    override suspend fun insertBreedsBySearch(breedsBySearch: List<BreedBySearchItem>) {
        return dao.insertBreedsBySearch(breedsBySearch)
    }
}