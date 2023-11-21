package com.omarhawari.rijksdata

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.core.asDataResult
import com.omarhawari.rijksdata.data.RijkRepositoryNetwork
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

@ExperimentalCoroutinesApi
class GetArtObjectsListUseCaseUnitTest {

    @Test
    fun `usecase must return a Success object if endpoint call was successful`() = runTest {

        val expectedResult = asDataResult {
            Pair(
                0, listOf(
                    TestData.artObject, TestData.artObject
                )
            )
        }

        val repoMock = mock<RijkRepositoryNetwork> {
            onBlocking {
                getArtObjects(0, 10, "artist")
            } doReturn expectedResult
        }

        val getArtObjectListUseCase = GetArtObjectListUseCase(repoMock)

        assertTrue(getArtObjectListUseCase(0, 10, "artist") is DataResult.Success)

    }

    @Test
    fun `usecase must return a Failure object if there's an exception`() = runTest {

        val expectedResult = DataResult.Failure(Exception())

        val repoMock = mock<RijkRepositoryNetwork> {
            onBlocking {
                getArtObjects(0, 10, "artist")
            } doReturn expectedResult
        }

        val getArtObjectListUseCase = GetArtObjectListUseCase(repoMock)

        assertTrue(getArtObjectListUseCase(0, 10, "artist") is DataResult.Failure)

    }


}