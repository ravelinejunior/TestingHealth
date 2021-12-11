package br.com.raveline.testinghealth.presentation.viewmodels


import br.com.raveline.testinghealth.data.model.Breeds
import br.com.raveline.testinghealth.domain.repository.BreedsRepository
import br.com.raveline.testinghealth.domain.usecase.GetBreedsUseCase
import br.com.raveline.testinghealth.utils.BaseUnitTest
import br.com.raveline.testinghealth.utils.getValueForTest
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test
import java.lang.RuntimeException

@ExperimentalCoroutinesApi
class BreedViewModelTest: BaseUnitTest() {
    private val repository: BreedsRepository = mock()

    private val breeds = mock<Breeds>()
    private val getBreedsUseCase = GetBreedsUseCase(repository)

    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getBreedsFromRepository() = runBlockingTest {
        val breedsViewModel = mockSuccessfulCase()

        breedsViewModel.getBreeds(0).getValueForTest()
        verify(repository,times(1)).getBreeds(0)

    }

    @Test
    fun getBreedsFromDb() = runBlockingTest {
        val breedViewModel = mockSuccessfulCaseFromDb()

        breedViewModel.getBreedsFromDb().getValueForTest()
        verify(repository, times(1)).getBreedsFromDb()
    }

    @Test
    fun emitBreedsFromRepository() = runBlockingTest {
        val breedsViewModel = mockSuccessfulCase()
        assertEquals(breeds,breedsViewModel.getBreeds(0).getValueForTest())
    }


    private fun TestCoroutineScope.mockSuccessfulCase():BreedViewModel{
        runBlockingTest {
            whenever(repository.getBreeds(0)).thenReturn(
                breeds
            )
        }

        return BreedViewModel(getBreedsUseCase)
    }

    private fun TestCoroutineScope.mockSuccessfulCaseFromDb():BreedViewModel{
        runBlockingTest {
            whenever(repository.getBreedsFromDb()).thenReturn(
                breeds
            )
        }

        return BreedViewModel(getBreedsUseCase)
    }
}