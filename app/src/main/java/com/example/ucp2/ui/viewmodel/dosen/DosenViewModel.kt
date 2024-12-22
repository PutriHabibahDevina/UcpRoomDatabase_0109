package com.example.ucp2.ui.viewmodel.dosen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.repository.Repository
import kotlinx.coroutines.launch

class DosenViewModel(private val repository: Repository) : ViewModel()
{
    var uiState by mutableStateOf(DosenUIState())

    //Memperbarui state berdasarkan input pengguna
    fun updateState(dosenEvent: DosenEvent){
        uiState = uiState.copy(
            dosenEvent = dosenEvent,
        )
    }
    //Validasi data input pengguna
    private fun validateFields():Boolean {
        val event = uiState.dosenEvent
        val errorState = FormErrorStateDosen(
            nidn = if (event.nidn.isNotEmpty()) null else "NIDN tidak boleh kosong",
            nama = if (event.nama.isNotEmpty()) null else "Nama tidak boleh kosong",
            jenisKelamin = if (event.jenisKelamin.isNotEmpty()) null else "Jenis Kelamin tidak boleh kosong"
        )
        uiState = uiState.copy(isEntryValid = errorState)
        return errorState.isValid()
    }
    //Menyimpan data ke repository
    fun saveData(){
        val currentEvent = uiState.dosenEvent
        if(validateFields()){ //Kalo validasinya bener
            viewModelScope.launch {
                try {
                    repository.insertDosen(currentEvent.toDosenEntity())
                    uiState = uiState.copy(
                        snackBarMessage = "Data berhasil disimpan",
                        dosenEvent = DosenEvent(), //Reset input form
                        isEntryValid = FormErrorStateDosen() //Reset error state
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
data class DosenUIState(
    val dosenEvent: DosenEvent = DosenEvent(),
    val isEntryValid: FormErrorStateDosen = FormErrorStateDosen(),
    val snackBarMessage: String? = null //kayak pop up
)

//Untuk validasi
data class FormErrorStateDosen(
    val nidn: String? = null,
    val nama: String? = null,
    val jenisKelamin: String? = null
){
    fun isValid():Boolean{
        return nidn == null && nama == null && jenisKelamin == null
    }
}

//Menyimpan input form ke dalam entity
fun DosenEvent.toDosenEntity(): Dosen = Dosen( //Data yang diinputkan akan diimasukkan ke dalam Entity/entitas
    nidn = nidn,
    nama = nama,
    jenisKelamin = jenisKelamin
)

//Data class variabel yang menyimpan data input form
data class DosenEvent(
    val nidn: String = "",
    val nama: String = "",
    val jenisKelamin: String = ""
)