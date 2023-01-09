package com.example.bc_parking.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bc_parking.data.ParkRepositoryImpl
import com.example.bc_parking.domain.AddParkItemUseCase
import com.example.bc_parking.domain.EditParkItemUseCase
import com.example.bc_parking.domain.GetParkItemUseCase
import com.example.bc_parking.domain.ParkItem

class ParkItemViewModel : ViewModel(){
    private val repository = ParkRepositoryImpl

    private val getParkItemUseCase = GetParkItemUseCase(repository)
    private val addParkItemUseCase = AddParkItemUseCase(repository)
    private val editParkItemUseCase = EditParkItemUseCase(repository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputFirm = MutableLiveData<Boolean>()
    val errorInputFirm: LiveData<Boolean>
        get() = _errorInputFirm

    private val _errorInputDateFrom = MutableLiveData<Boolean>()
    val errorInputDateFrom: LiveData<Boolean>
        get() = _errorInputDateFrom

    private val _errorInputDateTo = MutableLiveData<Boolean>()
    val errorInputDateTo: LiveData<Boolean>
        get() = _errorInputDateTo

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _parkItem = MutableLiveData<ParkItem>()
    val parkItem: LiveData<ParkItem>
        get() = _parkItem

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun getParkItem(parkItemId: Int) {
        val item = getParkItemUseCase.getParkItem(parkItemId)
        _parkItem.value = item
    }

    fun addParkItem(
        inputName: String?,
        inputDateFrom: String?,
        inputDateTo: String?,
        inputFirm: String?,
        inputCount: String?
    ) {
        val name = parseName(inputName)
        val firm = parseName(inputFirm)
        val dateFrom = parseName(inputDateFrom)
        val dateTo = parseName(inputDateTo)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, firm, dateFrom, dateTo, count)
        if (fieldsValid) {
            val parkItem = ParkItem(name, firm, dateFrom, dateTo, count)
            addParkItemUseCase.addParkItem(parkItem)
            finishWork()
        }
    }

    fun editParkItem(
        inputName: String?,
        inputFirm: String?,
        inputDateFrom: String?,
        inputDateTo: String?,
        inputCount: String?
    ) {
        val name = parseName(inputName)
        val firm = parseName(inputFirm)
        val dateFrom = parseName(inputDateFrom)
        val dateTo = parseName(inputDateTo)
        val count = parseCount(inputCount)
        val fieldsValid = validateInput(name, firm, dateFrom, dateTo, count)
        if (fieldsValid) {
            _parkItem.value?.let {
                val item = it.copy(
                    name = name,
                    firm = firm,
                    dateFrom = dateFrom,
                    dateTo = dateTo,
                    count = count
                )
                editParkItemUseCase.editParkItem(item)
                finishWork()
            }
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(
        name: String,
        firm: String,
        dateFrom: String,
        dateTo: String,
        count: Int
    ): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (firm.isBlank()) {
            _errorInputFirm.value = true
            result = false
        }
        if (dateFrom.isBlank()) {
            _errorInputDateFrom.value = true
            result = false
        }
        if (dateTo.isBlank()) {
            _errorInputDateTo.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputFirm() {
        _errorInputFirm.value = false
    }

    fun resetErrorInputDateFrom() {
        _errorInputDateFrom.value = false
    }

    fun resetErrorInputDateTo() {
        _errorInputDateTo.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}