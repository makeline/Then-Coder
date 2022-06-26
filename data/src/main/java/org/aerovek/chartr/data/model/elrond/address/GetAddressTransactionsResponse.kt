package org.aerovek.chartr.data.model.elrond.address

import java.math.BigInteger

internal data class GetAddressTransactionsResponse(
    val transactions: List<TransactionOnNetworkData>
) {
    data class TransactionOnNetworkData(
        val sender: String,
        val receiver: String,
        val senderUsername: String?,
        val receiverUsername: String?,
        val nonce: Long,
        val value: BigInteger,
        v