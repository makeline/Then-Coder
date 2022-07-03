package org.aerovek.chartr.data.model.elrond.network

import com.google.gson.annotations.SerializedName

internal data class GetNetworkConfigResponse(
    val config: NetworkConfigData
) {
    internal data class NetworkConfigData(
        @SerializedName(value = "erd_chain_id")
        val chainI