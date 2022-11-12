package com.elrond.erdkotlin

import com.elrond.erdkotlin.domain.wallet.models.Wallet
import org.junit.Test

import org.junit.Assert.assertEquals

class WalletTest {

    @Test
    fun generateMnemonic() {
        val words = Wallet.generateMnemonic()
        assertEquals(24, words.size)
    }

    @Test
    fun deriveFromMnemonic() {
        // Emotion spare
        var mnemonic =
            "emotion spare multiply lecture rude machine raise radio ability doll depend equip pass ghost cabin