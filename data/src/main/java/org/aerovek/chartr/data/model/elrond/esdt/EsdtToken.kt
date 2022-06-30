package org.aerovek.chartr.data.model.elrond.esdt

data class EsdtToken(
    val identifier: String,
    val name: String,
    val ticker: String,
    val owner: String,
    val minted: String,
    val burnt: String,
    val initialMinted: String,
    val decimals: Int,
    val isPaused: Boolean,
    val assets: EsdtAssets?,
    val transactions: Int,
    val accounts: Int,
    val canUpgrade: Boolean,
    