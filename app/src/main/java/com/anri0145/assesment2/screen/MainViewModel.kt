package com.anri0145.assesment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anri0145.assesment2.database.PengeluaranDao
import com.anri0145.assesment2.model.Pengeluaran
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MainViewModel(private val dao: PengeluaranDao) : ViewModel() {

    val data: StateFlow<List<Pengeluaran>> = dao.getPengeluaran()

        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = emptyList()
        )
    fun undoDelete(id: Long){
        viewModelScope.launch (Dispatchers.IO){
            dao.undoDeleteById(id)
        }
    }
    fun deletePermanent(id: Long){
        viewModelScope.launch (Dispatchers.IO){
            dao.deleteById(id)
        }
    }
}