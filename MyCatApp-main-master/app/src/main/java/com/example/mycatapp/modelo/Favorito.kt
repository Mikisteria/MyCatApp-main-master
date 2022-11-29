package com.example.mycatapp.modelo

data class Favorito (
    val id: String? = null,
    val name: String? = null,
    val description: String? = null,
    val origin: String? = null,
    val temperament: String? = null,
    val image: CatImage? = null,
    val favorito: Boolean = false
)

//keytool -list -v -keystore C:\Users\Mika\Desktop\MyCatApp-main-master\MyCatApp-main-master\keystore\keystore.jks -alias apkTPO