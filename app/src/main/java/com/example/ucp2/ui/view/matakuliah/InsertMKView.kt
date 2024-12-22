package com.example.ucp2.ui.view.matakuliah

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.navigation.AlamatMKNavigasi
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahEvent
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahViewModel
import com.example.ucp2.ui.viewmodel.PenyediaViewModel
import com.example.ucp2.ui.viewmodel.matakuliah.FormErrorStateMK
import com.example.ucp2.ui.viewmodel.matakuliah.MataKuliahUIState
import kotlinx.coroutines.launch

object DestinasiInsert : AlamatMKNavigasi {
    override val route: String = "insert_matakuliah"
}

@Composable
fun InsertMKView(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    mataKuliahViewModel: MataKuliahViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val mataKuliahUiState = mataKuliahViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() } //Snackbar state
    val coroutineScope = rememberCoroutineScope()

    //Observasi perubahan snackbarMessage
    LaunchedEffect(mataKuliahUiState.snackBarMessage)
    {
        mataKuliahUiState.snackBarMessage?.let { message ->
            coroutineScope.launch {
                snackbarHostState.showSnackbar(message)
                mataKuliahViewModel.resetSnackBarMessage()
            }
        }
    }
    Scaffold (
        modifier = Modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) } //Tempat snackbar di scaffold
    ) { padding ->
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Data",
                modifier = modifier
            )
            // Body untuk form input matakuliah
            InsertBodyMK(
                uiState = mataKuliahUiState,
                onValueChange = {updateEvent ->
                    mataKuliahViewModel.updateState(updateEvent) //Update state di ViewModel
                },
                onClick = {
                    mataKuliahViewModel.saveData() //Simpan data
                    onNavigate()
                }
            )
        }
    }
}

@Composable
fun InsertBodyMK(
    modifier: Modifier = Modifier,
    onValueChange: (MataKuliahEvent) -> Unit,
    uiState: MataKuliahUIState,
    onClick: () -> Unit
){
    Column (
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        FormMK(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FormMK(
    modifier: Modifier = Modifier,
    mataKuliahEvent: MataKuliahEvent = MataKuliahEvent(),
    onValueChange: (MataKuliahEvent) -> Unit = {},
    errorState: FormErrorStateMK = FormErrorStateMK()
) {
    val jenis = listOf("Wajib", "Peminatan")

    Column(modifier = modifier.fillMaxWidth()) {
        // Field Nama Matakuliah
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.namaMK,
            onValueChange = { onValueChange(mataKuliahEvent.copy(namaMK = it)) },
            label = { Text("Nama Matakuliah") },
            isError = errorState.namaMK != null,
            placeholder = { Text("Masukkan Nama Matakuliah") }
        )
        Text(
            text = errorState.namaMK ?: "",
            color = Color.Red
        )

        // Field Kode Matakuliah
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = { onValueChange(mataKuliahEvent.copy(kode = it)) },
            label = { Text("Kode Matakuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Matakuliah") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        // Field SKS Matakuliah
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sks,
            onValueChange = { onValueChange(mataKuliahEvent.copy(sks = it)) },
            label = { Text("SKS") },
            isError = errorState.sks != null,
            placeholder = { Text("Masukkan Jumlah SKS") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.sks ?: "",
            color = Color.Red
        )

        // Field Semester Matakuliah
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.sem,
            onValueChange = { onValueChange(mataKuliahEvent.copy(sem = it)) },
            label = { Text("Semester") },
            isError = errorState.sem != null,
            placeholder = { Text("Masukkan Semeter") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.sem ?: "",
            color = Color.Red
        )

        // Field Jenis Matakuliah
        Text(text = "Jenis Matakuliah")
        Row(modifier = Modifier.fillMaxWidth())
        {
            jenis.forEach { jenis ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    RadioButton(
                        selected = mataKuliahEvent.jenis == jenis,
                        onClick = {
                            onValueChange(mataKuliahEvent.copy(jenis = jenis))
                        },
                    )
                    Text(
                        text = jenis,
                    )
                }
            }
        }

        // Field Dosen Pengampu Matakuliah
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.dosenPengampu,
            onValueChange = { onValueChange(mataKuliahEvent.copy(dosenPengampu = it)) },
            label = { Text("Dosen Pengampu") },
            isError = errorState.dosenPengampu != null,
            placeholder = { Text("Masukkan Nama Dosen Pengampu") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Text(
            text = errorState.dosenPengampu ?: "",
            color = Color.Red
        )
    }
}