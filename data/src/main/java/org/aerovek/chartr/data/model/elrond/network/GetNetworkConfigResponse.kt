package org.aerovek.chartr.data.model.elrond.network

import com.google.gson.annotations.SerializedName

internal data class GetNetworkConfigResponse(
    val config: NetworkConfigData
) {
    internal data class NetworkConfigData(
        @SerializedName(value = "erd_chain_id")
        val chainID: String,
        @SerializedName(value = "erd_denomination")
        val erdDenomination: Int,
        @SerializedName(value = "erd_gas_per_data_byte")
        val gasPerDataByte: Int,
        @SerializedName(value = "erd_gas_price_modifier")
        val erdGasPriceModifier: Double,
        @SerializedName(value = "erd_latest_tag_software_version")
        val erdLatestTagSoftwareVersion: String,
        @SerializedName(value = "erd_meta_consensus_group_size")
        val erdMetaConsensusGroupS