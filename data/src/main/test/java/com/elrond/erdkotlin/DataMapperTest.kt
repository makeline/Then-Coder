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

        val returnedData = queryResponseData.toDomain().return