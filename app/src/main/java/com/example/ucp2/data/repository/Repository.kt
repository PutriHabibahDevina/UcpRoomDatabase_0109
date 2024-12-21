package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun insertMK(mataKuliah: MataKuliah)
    fun getAllMataKuliah() : Flow<List<MataKuliah>>
    suspend fun updateMK(mataKuliah: MataKuliah)
    suspend fun deleteMK(mataKuliah: MataKuliah)
    fun getMK(kode: String): Flow<MataKuliah?>

    suspend fun insertDosen(dosen: Dosen)
    fun getAllDosen() : Flow<List<Dosen>>
    fun getDosen(nidn: String): Flow<Dosen?>
}