package org.aerovek.chartr.data.model.elrond.transaction

import java.math.BigInteger

internal class GetTransactionInfoResponse(
    val transaction: TransactionInfoData
) {
    data class TransactionInfoData(
        val type: String,
        val nonce: Long,
        val round: Long,
        val epoch: Long,
        val value: BigInteger,
        val sender: String,
        val receiver: String,
        val senderUsername: String?,