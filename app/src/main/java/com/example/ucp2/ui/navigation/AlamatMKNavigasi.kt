package com.example.ucp2.ui.navigation

interface AlamatMKNavigasi {
    val route: String
}
object DestinasiMKHome : AlamatMKNavigasi {
    override val route = "home"
}
object DestinasiMKDetail : AlamatMKNavigasi {
    override val route = "detail"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}
object DestinasiMKUpdate : AlamatMKNavigasi {
    override val route = "update"
    const val KODE = "kode"
    val routesWithArg = "$route/{$KODE}"
}