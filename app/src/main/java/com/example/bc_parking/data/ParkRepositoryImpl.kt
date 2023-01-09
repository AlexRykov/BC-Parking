package com.example.bc_parking.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bc_parking.domain.ParkItem
import com.example.bc_parking.domain.ParkRepository

object ParkRepositoryImpl : ParkRepository {

    private val parkListLD = MutableLiveData<List<ParkItem>>()
    private val parkList = sortedSetOf<ParkItem>({ o1, o2 -> o1.id.compareTo(o2.id) })

    private var autoIncrementId = 0

    init {
        for (i in 0 until 10) {
            val item = ParkItem(
                "Name $i",
                "firm $i",
                "date_from",
                "date_to",
                i
            )
            addParkItem(item)
        }
    }

    override fun editParkItem(parkItem: ParkItem) {
        val oldElement = getParkItem(parkItem.id)
        parkList.remove(oldElement)
        addParkItem(parkItem)
    }

    override fun addParkItem(parkItem: ParkItem) {
        if (parkItem.id == ParkItem.UNDEFINED_ID) {
            parkItem.id = autoIncrementId++
        }
        parkList.add(parkItem)
        updateList()
    }

    override fun getParkItem(parkItemId: Int): ParkItem {
        return parkList.find {
            it.id == parkItemId
        } ?: throw RuntimeException("Element with id $parkItemId not found")
    }

    override fun getParkList(): LiveData<List<ParkItem>> {
        return parkListLD
    }

    override fun deleteParkItem(parkItem: ParkItem) {
        parkList.remove(parkItem)
        updateList()
    }

    private fun updateList() {
        parkListLD.value = parkList.toList()
    }
}
