package com.example.tsalida.destination

enum class MoreDestination(
    val route: String,
    val label: String
){
    READINGLIST("readinglist", "Responsive Reading"),
    READING("reading/{pageNo}", "Responsive Reading"),
    ENDLIST("endlist", "End Pages"),
    END("end/{pageNo}", "End Pages"),
    THEME("theme", "Theme"),
    SUPPORT("support", "Support")
}