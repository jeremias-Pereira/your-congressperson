package com.jpgsolution.youcongressperson.model.datamodel

data class CongressPersons(
    val dados: ArrayList<CongressPerson>,
)

data class CongressPerson(
    val id: String,
    val nome: String,
    val siglaPartido: String,
    val siglaUf: String,
    val urlFoto: String,
)
