package org.aerovek.chartr.data.model.elrond.transaction

import com.google.gson.GsonBuilder
import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.model.elrond.address.Address
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import org.bouncycastle.util.encoders.Base64

data class Transaction(
    val sender: Address,
    val receiver: Address,
    val chainID: String,
    val senderUsername: String? = null,
    val receiverUsername: String? = null,
    val nonce: Long = 0,
    val value: BigInteger = BigInteger.ZERO,
    val gasPrice: Long = 1000000000,
    val gasLimit: Long? = null,
    val version: Int = VERSION_DEFAULT,
    val data: String? = null,
    val option: Int = OPTION_NONE,
    val signature: String = "",
    val txHash: String = ""
) {

    val isSigned = signature.isNotEmpty()
    val isSent = txHash.isNotEmpty()

    @Throws(ElrondException.CannotSerializeTransactionException::class)
    fun serialize(): String = try {
        gson.toJson(toMap())
    } catch (error: ElrondException.AddressException) {
        throw ElrondException.CannotSerializeTransactionException()
    }

    @Throws(ElrondException.AddressException::class)
    private fun toMap(): Map<String, Any> {
        return mutableMapOf<String, Any>().apply {
            put("nonce", nonce)
            put("value", value.toString(10))
            put("receiver", receiver.bec