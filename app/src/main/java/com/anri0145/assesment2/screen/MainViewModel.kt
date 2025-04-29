package com.anri0145.assesment2.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anri0145.assesment2.database.PengeluaranDao
import com.anri0145.assesment2.model.Pengeluaran
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class MainViewModel(dao: PengeluaranDao) : ViewModel() {

    val data: StateFlow<List<Pengeluaran>> = dao.getPengeluara().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = emptyList()
    )

}