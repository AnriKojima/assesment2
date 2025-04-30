package com.anri0145.assesment2.navigation

import com.anri0145.assesment2.screen.KEY_ID_PENGELUARAN

sealed class Screen(val route: String){
    data object Home: Screen("mainScreen")
    data object Main: Screen("mainScreen")
    data object FormBaru: Screen("detailScreen")
    data object FormUbah: Screen("detailScreen/{$KEY_ID_PENGELUARAN}"){
        fun withId(id: Long) = "detailScreen/$id"
    }
    data object Recycle: Screen("recycleScreen")
}