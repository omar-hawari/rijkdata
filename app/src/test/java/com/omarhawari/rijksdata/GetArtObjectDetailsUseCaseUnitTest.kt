package com.omarhawari.rijksdata

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.core.asDataResult
import com.omarhawari.rijksdata.data.RijkRepositoryNetwork
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectDetailsUseCase
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class GetArtObjectDetailsUseCaseUnitTest {

    @Test
    fun `usecase must return a Success object if endpoint call was successful`() = runTest {

        val expectedResult = asDataResult {
            TestData.artObjectDetails
        }

        val repoMock = mock<RijkRepositoryNetwork> {
            onBlocking {
                getArtObjectDetails(objectNumber = "someNumber")
            } doReturn expectedResult
        }

        val getArtObjectDetailsUseCase = GetArtObjectDetailsUseCase(repoMock)

        assertTrue(getArtObjectDetailsUseCase("someNumber") is DataResult.Success)

    }

    @Test
    fun `usecase must return a Failure object if there's an exception`() = runTest {

        val expectedResult = DataResult.Failure(Exception())

        val repoMock = mock<RijkRepositoryNetwork> {
            onBlocking {
                getArtObjectDetails(objectNumber = "someNumber")
            } doReturn expectedResult
        }

        val getArtObjectDetailsUseCase = GetArtObjectDetailsUseCase(repoMock)

        assertTrue(getArtObjectDetailsUseCase("someNumber") is DataResult.Failure)

    }

}