package org.aerovek.chartr.data.repository

import okhttp3.MediaType
import okhttp3.RequestBody
import org.aerovek.chartr.data.model.primetrust.*
import org.aerovek.chartr.data.network.PrimeTrustService
import java.io.IOException
import java.lang.Exception

interface PrimeTrustRepository {
    suspend fun createUser(email: String, name: String, password: String): PTCreateUserResponseData?
}

internal class PrimeTrustRepositoryImpl(
    private val primeTrustService: PrimeTrustService
): PrimeTrustRepository {
    override suspend fun createUser(email: String, name: String, password: String): PTCreateUserResponseData? {
        return try {
            val user = PTUser("user"