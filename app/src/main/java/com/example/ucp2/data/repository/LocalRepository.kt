package com.example.ucp2.data.repository

import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.dao.MataKuliahDao
import com.example.ucp2.data.entity.Dosen
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val dosenDao: DosenDao, private val mataKuliahDao: MataKuliahDao):Repository
{
    //Fungsi Dosen
    override suspend fun insertDosen(dosen: Dosen) {
        dosenDao.insertDosen(dosen)
    }
    override fun getAllDosen():Flow<List<Dosen>> {
        return dosenDao.getAllDosen()
    }

    //Fungsi MataKuliah
    override suspend fun insertMK(mataKuliah: MataKuliah) {
        mataKuliahDao.insertMataKuliah(mataKuliah)
    }
    override fun getAllMataKuliah(): Flow<List<MataKuliah>> {
        return mataKuliahDao.getAllMataKuliah()
    }
    override suspend fun updateMK(mataKuliah: MataKuliah) {
        mataKuliahDao.updateMataKuliah(mataKuliah)
    }
    override suspend fun deleteMK(mataKuliah: MataKuliah) {
        mataKuliahDao.deleteMataKuliah(mataKuliah)
    }

    override fun getMK(kode: String): Flow<MataKuliah?> {
        return mataKuliahDao.getMataKuliah(kode)
    }
}