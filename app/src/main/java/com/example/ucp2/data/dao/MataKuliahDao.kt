package com.example.ucp2.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.ucp2.data.entity.MataKuliah
import kotlinx.coroutines.flow.Flow

@Dao
interface MataKuliahDao {
    //Create
    @Insert
    suspend fun insertMataKuliah(mataKuliah: MataKuliah)

    //Read
    @Query("SELECT * FROM matakuliah ORDER BY namaMK ASC ")
    fun getAllMataKuliah(): Flow<List<MataKuliah>>

    //Update
    @Update
    suspend fun updateMataKuliah(mataKuliah: MataKuliah)

    //Delete
    @Delete
    suspend fun deleteMataKuliah(mataKuliah: MataKuliah)

    @Query("SELECT * FROM matakuliah WHERE kode = :kode ")
    fun getMataKuliah(kode: String): Flow<MataKuliah>
}