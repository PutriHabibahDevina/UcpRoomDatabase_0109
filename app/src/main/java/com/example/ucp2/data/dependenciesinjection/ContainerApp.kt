package com.example.ucp2.data.dependenciesinjection

import android.content.Context
import com.example.ucp2.data.database.KrsDatabase
import com.example.ucp2.data.repository.LocalRepository
import com.example.ucp2.data.repository.Repository

interface InterfaceContainerApp {
    val repository:Repository
}

class ContainerApp(private val context: Context) : InterfaceContainerApp{
    override val repository:Repository by lazy {
        val database = KrsDatabase.getDatabase(context)
        LocalRepository(
            dosenDao = database.dosenDao(),
            mataKuliahDao = database.mataKuliahDao()
        )
    }
}