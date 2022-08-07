package org.aerovek.chartr.data.network

import org.aerovek.chartr.data.model.elrond.esdt.EsdtConstants

/**
 * The chosen build variant will ultimately dictate which URLs are currently active.
 * If 'productionRelease' is the selected build variant, MainNet will be used,
 * all other build variants will use the DevNet object.
 * The scAddress should be the smart contract address that stores account information.
 * Refer to the createAccount logic to see how that works.
 */
sealed class ElrondNetwork(
    open val apiUrl: String,
    open val gatewayUrl: String,
    open val aeroTokenId: String,
    open val scAddress: Stri