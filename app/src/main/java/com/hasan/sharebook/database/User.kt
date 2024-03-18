package com.hasan.sharebook.database

data class User(
    val username: String?,
    val id: String? = "${username}_123",
    val password: String?,
    val phone_number: String?,
    val description: String? = "",
) {
    constructor() : this(null, null, null, null, null)
}
