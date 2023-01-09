package com.example.bc_parking.domain

class GetParkItemUseCase(private val parkRepository: ParkRepository) {

    fun getParkItem(parkItemId: Int): ParkItem {

        return parkRepository.getParkItem(parkItemId)
    }
}
