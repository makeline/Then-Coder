package org.aerovek.chartr.data.model.primetrust

import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName


//region $CreateUser

data class PTCreateUserData(
    val data: PTUser
)

data class PTUser(
    val type: String,
    val attributes: PTCreateUserAttributes
)

data class PTCreateUserAttributes(
    val email: String,
    val name: String,
    val password: String
)

data class PTCreateUserResponseData(
    val data: PTCreateUserResponse
)

data class PTCreateUserResponse(
    val type: String,
    val id: String,
    val attributes: PTCreateUserRespon