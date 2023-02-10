package com.example.registrocurso.data.error

import java.io.IOException

class NoConnectivityException : IOException() {

    override val message: String
        get() = "Sin red de internet"

}