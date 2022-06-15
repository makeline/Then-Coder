package org.aerovek.chartr.data.model.elrond.address

import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.util.toHexString
import org.bitcoinj.core.Bech32
import org.bouncycastle.util.encoders.DecoderException
import org.bouncycastle.util.encoders.Hex
import java.io.ByteArrayOutputStream

@Suppress("DataClassPrivateConstructor")
data class Address private constructor(
    val hex: String
) {
    // Keep `pubKey` and `bech32` outside of the constructor
    val pubKey: ByteArray by lazy { Hex.decode(hex) }
    val bech32: String by lazy { Bech32.encode(HRP, convertBits(pubKey, 8, 5, true)) }

    companion object {
        const val HRP = "erd"
        const val PUBKEY_LENGTH = 32
        const val PUBKEY_STRING_LENGTH = PUBKEY_LENGTH * 2 // hex-encoded
        const val BECH32_LENGTH = 62
        const val ZERO_PUBKEY_STRING =
            "0000000000000000000000000000000000000000000000000000000000000000"

        fun createZeroAddress() = Address(ZERO_PUBKEY_STRING)

        @Throws(ElrondException.AddressException::class, ElrondException.BadAddressHrpException::class)
        fun fromBech32(value: String): Address {
            val bech32Data = try {
                Bech32.decode(value)
            } catch (e: Exception) {
                throw ElrondException.CannotCreateBech32AddressException(value)
            }
            if (bech32Data.hrp