package com.example.ucp2.ui.viewmodel

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.praktikum7.ui.viewmodel.HomeDosenViewModel
import com.example.ucp2.KrsApp
import com.example.ucp2.ui.viewmodel.dosen.DetailDosenViewModel
import com.example.ucp2.ui.viewmodel.dosen.DosenViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.DetailMKViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.HomeMKViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.UpdateMKViewModel

object PenyediaViewModel {
    val Factory = viewModelFactory {
        initializer {
            DosenViewModel(
                krsApp().containerApp.repository
            )
            MataKuliahViewModel(
                krsApp().containerApp.repository
            )
        }

        initializer {
            HomeDosenViewModel(
                krsApp().containerApp.repository
            )
            HomeMKViewModel(
                krsApp().containerApp.repository
            )
        }

        initializer {
            DetailDosenViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repository
            )
            DetailMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repository
            )
        }

        initializer {
            UpdateMKViewModel(
                createSavedStateHandle(),
                krsApp().containerApp.repository
            )
        }
    }
}

fun CreationExtras.krsApp(): KrsApp =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as KrsApp)