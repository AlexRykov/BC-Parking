package com.example.bc_parking.domain

class DeleteParkItemUseCase(private val parkRepository: ParkRepository) {

    fun deleteParkItem(parkItem: ParkItem){
        parkRepository.deleteParkItem(parkItem)
    }
}
