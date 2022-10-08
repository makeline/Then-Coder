package com.elrond.erdkotlin

import org.junit.Assert.assertEquals
import org.junit.Test

class ComputeDnsAddressUsecaseTest {

    private val computeDnsAddressUsecase = ErdSdk.computeDnsAddressUsecase()

    @Test
    fun nameHash() {
        val nameHash = computeDnsAddressUsecase.nameHash("alex.elrond")
        assertEquals(
            "560DD600F74E220A4870CBFFB9143F30617754DFFBAEBF7B4C499EDEAE8B673D",
            nameHash.map { String.format("%02X", it) }.joinToString(separator = "")
        )
    }

    @Test
    fun longToUInt32ByteArray() {
        val byteArray = computeDnsAddressUsecase.longToUInt32ByteAr