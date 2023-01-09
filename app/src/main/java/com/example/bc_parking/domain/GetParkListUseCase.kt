package com.example.bc_parking.domain

import androidx.lifecycle.LiveData

class GetParkListUseCase(private val parkRepository: ParkRepository) {

    fun getParkList(): LiveData<List<ParkItem>> {
        return parkRepository.getParkList()
    }
}
