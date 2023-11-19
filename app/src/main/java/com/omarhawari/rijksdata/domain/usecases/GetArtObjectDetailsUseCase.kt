package com.omarhawari.rijksdata.domain.usecases

import com.omarhawari.rijksdata.domain.RijkRepositoryContract
import javax.inject.Inject

class GetArtObjectDetailsUseCase @Inject constructor(private val repository: RijkRepositoryContract) {

    suspend operator fun invoke(
        objectNumber: String,
    ) = repository.getArtObjectDetails(objectNumber = objectNumber)

}