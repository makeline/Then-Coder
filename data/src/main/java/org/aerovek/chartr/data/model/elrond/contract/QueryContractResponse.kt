package org.aerovek.chartr.data.model.elrond.contract

import com.google.gson.internal.LinkedTreeMap
import java.math.BigInteger

internal data class QueryContractResponse(
    val data: Data
) {

    data class Data(
        val returnData: List<String>?, // ex: ["Aw=="]
        val returnCode: String, // ex: "ok"
        val 