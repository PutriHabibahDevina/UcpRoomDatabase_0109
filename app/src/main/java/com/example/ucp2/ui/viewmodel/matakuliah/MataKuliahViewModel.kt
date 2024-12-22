package com.example.ucp2.ui.viewmodel.matakuliah

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.MataKuliah
import com.example.ucp2.data.repository.Repository
import kotlinx.coroutines.launch

class MataKuliahViewModel(private val repository: Repository) : ViewModel()
{
    var uiState by mutableStateOf(MataKuliahUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(mataKuliahEvent: MataKuliahEvent){
        uiState = uiState.copy(
            mataKuliahEvent = mataKuliahEvent,
        )
    }
    //Validasi data input pengguna
    private fun validateFields():Boolean {
        val event = uiState.mataKuliahEvent
        val errorState = FormErrorStateMK(
            kode = if (event.kode.isNotEmpty()) null else "Kode matakuliah tidak boleh kosong",
            namaMK = if (event.namaMK.isNotEmpty()) null else "Nama matakuliah tidak boleh kosong",
            sks = if (event.sks.isNotEmpty()) null else "SKS tidak boleh kosong",
            sem = if (event.sem.isNotEmpty()) null else "Semester tidak boleh kosong",
            jenis = if (event.jenis.isNotEmpty()) null else "Jenis matakuliah tidak boleh kosong",
            dosenPengampu = if (event.dosenPengampu.isNotEmpty()) null else "Dosen pengampu tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    //Menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.mataKuliahEvent
        if(validateFields()){ //Kalo validasinya bener
            viewModelScope.launch {
                try {
                    repository.insertMK(currentEvent.toMataKuliahEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        mataKuliahEvent = MataKuliahEvent(), //Reset input form
                        isEntryValid = FormErrorStateMK() //Reset error state
                    )
                } catch (e:Exception){
                    uiState = uiState.copy(
                        snackBarMessage = "Data gagal disimpan"
                    )
                }
            }
        }
    }
    //Reset pesan Snackbar setelah ditampilkan
    fun resetSnackBarMessage(){
        uiState = uiState.copy(snackBarMessage = null)
    }
}

//Untuk menghandle perubahan, misal kaya error kan ntar tampilannya berubah jd error
data class MataKuliahUIState(
    val mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    val isEntryValid: FormErrorStateMK = FormErrorStateMK(),
    val snackBarMessage: String? = null //kayak pop up
)

//Untuk validasi
data class FormErrorStateMK(
    val kode: String? = null,
    val namaMK: String? = null,
    val sks: String? = null,
    val sem: String? = null,
    val jenis: String? = null,
    val dosenPengampu: String? = null
){
    fun isValid():Boolean{
        return kode == null && namaMK == null && sks == null && sem == null
                && jenis == null && dosenPengampu == null
    }
}

//Menyimpan input form ke dalam entity
fun MataKuliahEvent.toMataKuliahEntity():MataKuliah = MataKuliah( //Data yang diinputkan akan diimasukkan ke dalam Entity/entitas
    kode = kode,
    namaMK = namaMK,
    sks = sks,
    sem = sem,
    jenis = jenis,
    dosenPengampu = dosenPengampu
)

//Data class variabel yang menyimpan data input form
data class MataKuliahEvent(
    val kode: String = "",
    val namaMK: String = "",
    val sks: String = "",
    val sem: String = "",
    val jenis: String = "",
    val dosenPengampu: String = ""
)