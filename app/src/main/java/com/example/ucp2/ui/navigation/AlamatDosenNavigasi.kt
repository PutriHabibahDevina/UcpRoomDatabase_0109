package com.example.ucp2.ui.navigation

interface AlamatDosenNavigasi {
    val route: String
}
object DestinasiDosenHome : AlamatDosenNavigasi {
    override val route = "home"
}
object DestinasiDosenDetail : AlamatDosenNavigasi {
    override val route = "detail"
    const val NIDN = "nidn"
    val routesWithArg = "$route/{$NIDN}"
}