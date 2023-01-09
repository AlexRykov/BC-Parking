package com.example.bc_parking.domain

import androidx.lifecycle.LiveData

interface ParkRepository {

        fun editParkItem(parkItem: ParkItem)

        fun addParkItem(parkItem: ParkItem)

        fun deleteParkItem(parkItem: ParkItem)

        fun getParkItem(parkItemId: Int): ParkItem

        fun getParkList(): LiveData<List<ParkItem>>
}