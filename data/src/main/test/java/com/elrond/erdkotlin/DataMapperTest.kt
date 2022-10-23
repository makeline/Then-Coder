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
        val oneHundredBase64 = Str