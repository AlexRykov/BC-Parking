package com.example.bc_parking.domain

class EditParkItemUseCase(private val parkRepository: ParkRepository) {

    fun editParkItem(parkItem: ParkItem) {
        parkRepository.editParkItem(parkItem)
    }
}
