package org.aerovek.chartr.data.model.elrond.account

data class AccountToken(
    val identifier: String,
    val name: String,
    val ticker: String,
    val owner: String,
    val decimals: Int,
    val isP