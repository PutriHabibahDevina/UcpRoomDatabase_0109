package com.example.ucp2

import android.app.Application
import com.example.ucp2.data.dependenciesinjection.ContainerApp

class KrsApp : Application() {
    //Fungsinya untuk menyimpan instance ContainerApp
    lateinit var containerApp: ContainerApp

    override fun onCreate(){
        super.onCreate()
        //Membuat instance ContainerApp
        containerApp = ContainerApp(this)
        //Instance adalah objek yang dibuat dari class
    }
}