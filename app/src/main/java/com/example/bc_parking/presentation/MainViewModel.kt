package com.example.bc_parking.presentation

import androidx.lifecycle.ViewModel
import com.example.bc_parking.data.ParkRepositoryImpl
import com.example.bc_parking.domain.DeleteParkItemUseCase
import com.example.bc_parking.domain.EditParkItemUseCase
import com.example.bc_parking.domain.GetParkListUseCase
import com.example.bc_parking.domain.ParkItem

class MainViewModel : ViewModel() {

    private val repository = ParkRepositoryImpl

    private val getParkListUseCase = GetParkListUseCase(repository)
    private val deleteParkItemUseCase = DeleteParkItemUseCase(repository)
    private val editParkItemUseCase = EditParkItemUseCase(repository)

    val parkList = getParkListUseCase.getParkList()

    fun deleteParkItem(parkItem: ParkItem) {
        deleteParkItemUseCase.deleteParkItem(parkItem)
    }
    fun changeEnableState(parkItem: ParkItem) {
        val newItem = parkItem.copy()
        editParkItemUseCase.editParkItem(newItem)
    }

}
