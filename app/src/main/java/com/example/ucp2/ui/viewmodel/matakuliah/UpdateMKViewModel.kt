package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.data.repository.Repository
import com.example.ucp2.ui.navigation.DestinasiMKUpdate
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class UpdateMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
): ViewModel(){
    var updateUiState by mutableStateOf(MataKuliahUIState())
        private set

    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMKUpdate.KODE])

    init{
        viewModelScope.launch {
            updateUiState = repository.getMK(_kode)
                .filterNotNull()
                .first()
                .toUIStateMataKuliah()
        }
    }

    fun updateState(mataKuliahEvent: MataKuliahEvent) {
        updateUiState = updateUiState.copy(
            mataKuliahEvent = mataKuliahEvent
        )
    }

    fun validateFields(): Boolean{
        val event = updateUiState.mataKuliahEvent
        val errorState = FormErrorStateMK(
            kode = if (event.kode.isNotEmpty()) null else "Kode matakuliah tidak boleh kosong",
            namaMK = if (event.namaMK.isNotEmpty()) null else "Nama matakuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            sem = if (event.sem.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis matakuliah tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong",
        )
        updateUiState = updateUiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }

    fun updateData(){
        val currentEvent = updateUiState.mataKuliahEvent

        if(validateFields()) {
            viewModelScope.launch {
                try {
                    repository.updateMK(currentEvent.toMataKuliahEntity())
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data berhasil diupdate",
                        mataKuliahEvent = MataKuliahEvent(),
                        isEntryValid = FormErrorStateMK()
                    )
                    println("snackBarMessage diatur: ${updateUiState.snackBarMessage}")
                } catch (e: Exception) {
                    updateUiState = updateUiState.copy(
                        snackBarMessage = "Data gagal diupdate"
                    )
                }
            }
        } else{
            updateUiState = updateUiState.copy(
                snackBarMessage = "Data gagal diupdate"
            )
        }
    }

    fun resetSnackBarMessage(){
        updateUiState = updateUiState.copy(snackBarMessage = null)
    }

    fun MataKuliah.toUIStateMataKuliah(): MataKuliahUIState = MataKuliahUIState(
        mataKuliahEvent = this.toDetailUiEvent()
    )
}