package com.example.bc_parking.domain

class AddParkItemUseCase(private val parkRepository: ParkRepository) {

    fun addParkItem(parkItem: ParkItem) {
        parkRepository.addParkItem(parkItem)
    }
}
