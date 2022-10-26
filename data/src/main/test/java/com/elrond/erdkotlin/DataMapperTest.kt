package com.elrond.erdkotlin

import org.bouncycastle.util.encoders.Base64
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.math.BigInteger

@RunWith(MockitoJUnitRunner::class)
class DataMapperTest {

    @Test
    fun `query return data must be well decoded and formatted`() {
        val oneHundredBase64 = String(Base64.encode(arrayOf(100.toByte()).toByteArray()))

        val queryResponseData = QueryContractResponse.Data(
            returnData = listOf(oneHundredBase64),
            returnCode = "",
            returnMessage = null,
            gasRemaining = BigInteger.ZERO,
            gasRefund = BigInteger.ZERO,
            outputAccounts = null,
            deletedAccounts = null,
            touchedAccounts = null,
            logs = null
        )

        val returnedData = queryResponseData.toDomain().returnData?.first()!!

        assertEquals(
            "ZA==",
            returnedData.asBase64
        )
        assertEquals(
            "64",
            returnedData.asHex
        )
        assertEquals(
            "d",
            returnedData.asString,
        )
        assertEquals(
            100.toBigInteger(),
            returnedData.asBigInt
        )
    }

    @Test
    fun `esdt properties should be well formatted`() {
        val response = QueryContractResponse.Data(
            returnData = listOf("QWxpY2VUb2tlbnM=",
                "RnVuZ2libGVFU0RU",
                "2DSJxJNAmou8TU9f4WQo7rpyJ822eZVUQYwnabJM5hk=",
                "MTAwMDAwMDAwMDA=",
                "MA==",
                "TnVtRGVjaW1hbHMtNg==",
                "SXNQYXVzZWQtZmFsc2U=",
                "Q2FuVXBncmFkZS10cnVl",
                "Q2FuTWludC10cnVl",
                "Q2FuQnVybi10cnVl",
                "Q2FuQ2hhbmdlT3duZXItZmFsc2U=",
                "Q2FuUGF1c2UtdHJ1ZQ==",
                "Q2FuR