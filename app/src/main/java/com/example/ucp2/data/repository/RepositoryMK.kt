package com.example.ucp2.data.repository

import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

interface RepositoryMK {
    suspend fun insertMK(mataKuliah: MataKuliah)
    fun getAllMataKuliah() : Flow<List<MataKuliah>>
    suspend fun updateMK(mataKuliah: MataKuliah)
    suspend fun deleteMK(mataKuliah: MataKuliah)
    fun getDetailMataKuliah(kode: String): Flow<MataKuliah?>
}