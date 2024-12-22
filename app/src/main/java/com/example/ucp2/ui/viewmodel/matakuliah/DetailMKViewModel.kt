package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.data.repository.Repository
import com.example.ucp2.ui.navigation.DestinasiMKDetail
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailMKViewModel (
    savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel(){
    private val _kode: String = checkNotNull(savedStateHandle[DestinasiMKDetail.KODE])
    val detailUiState: StateFlow<DetailUiStateMK> = repository.getMK(_kode)
        .filterNotNull()
        .map {
            DetailUiStateMK(
                detailUiEvent = it.toDetailUiEvent(),
                isLoading = false
            )
        }
        .onStart {
            emit(DetailUiStateMK(isLoading = true))
            delay(600)
        }
        .catch {
            emit(
                DetailUiStateMK(
                    isLoading = false,
                    isError = true,
                    errorMessage = it.message ?: "Terjadi kesalahan",
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(2000),
            initialValue = DetailUiStateMK(
                isLoading = true,
            )
        )

    fun deleteMK(){
        detailUiState.value.detailUiEvent.toMataKuliahEntity().let {
            viewModelScope.launch {
                repository.deleteMK(it)
            }
        }
    }
}

data class DetailUiStateMK(
    val detailUiEvent: MataKuliahEvent = MataKuliahEvent(),
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = ""
) {
    val isUiEventEmpty: Boolean
        get() = detailUiEvent == MataKuliahEvent()
    val isUiEventNotEmpty: Boolean
        get() = detailUiEvent != MataKuliahEvent()
}

fun MataKuliah.toDetailUiEvent(): MataKuliahEvent{
    return MataKuliahEvent(
        kode = kode,
        namaMK = namaMK,
        sks = sks,
        sem = sem,
        jenis = jenis,
        dosenPengampu = dosenPengampu
    )
}