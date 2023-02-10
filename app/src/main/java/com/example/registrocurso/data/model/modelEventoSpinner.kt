package com.example.registrocurso.data.model

class modelEventoSpinner {
    private var namedescription: String? = null
    private var name: String? = null

    constructor() {}
    constructor(namedescription: String?, name: String?) {
        this.namedescription = namedescription
        this.name = name
    }

    fun getnamedescription(): String? {
        return namedescription
    }

    fun setnamedescription(namedescription: String?) {
        this.namedescription = namedescription
    }

    fun getname(): String? {
        return name
    }

    fun setname(name: String?) {
        this.name = name
    }

    override fun toString(): String {
        return namedescription!!
    }
}