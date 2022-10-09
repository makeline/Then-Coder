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
        val byteArray = computeDnsAddressUsecase.longToUInt32ByteArray(123211089L, 8)
        assertEquals(
            byteArrayOf(81, 13, 88, 7, 0, 0, 0, 0).toList(),
            byteArray.toList()
        )
    }

    @Test
    fun computeAddress() {
        val address = computeDnsAddressUsecase.computeAddress(Account(
            address = Address.fromBech32("erd1qyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqyqszqgpqq7se967nn")
        ))
        assertEquals(
            "erd1qqqqqqqqqqqqqpgq2u60t6gppp8uyrtutng27k9quk42xw6qqq7s63qz5v