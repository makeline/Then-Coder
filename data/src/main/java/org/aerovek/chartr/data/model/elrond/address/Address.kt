package org.aerovek.chartr.data.model.elrond.address

import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.util.toHexString
import org.bitcoinj.core.Bech32
import org.bouncycastle.util.encoders.DecoderException
import org.bouncycastle.util.encoders.Hex
import java.io.ByteArrayOutputStream

@Suppress("DataClassPrivateConstructor")
data class Address private cons