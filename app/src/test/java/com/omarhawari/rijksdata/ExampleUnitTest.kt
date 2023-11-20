package com.omarhawari.rijksdata

import com.omarhawari.rijksdata.core.DataResult
import com.omarhawari.rijksdata.core.asDataResult
import com.omarhawari.rijksdata.data.RijkRepositoryNetwork
import com.omarhawari.rijksdata.domain.usecases.GetArtObjectListUseCase
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GetArtObjectsListUseCaseUnitTest {

    @Test
    fun `execute with non-empty data should return success result`() = runTest {

        val repoMock = mockk<RijkRepositoryNetwork> {
        }

        val getArtObjectListUseCase = GetArtObjectListUseCase(repoMock)

        assertEquals(getArtObjectListUseCase(0, 10, "artist"), asDataResult {
            Pair(
                0, listOf(
                    TestData.artObject, TestData.artObject
                )
            )
        })

    }


}