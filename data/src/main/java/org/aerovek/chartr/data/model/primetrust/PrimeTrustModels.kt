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
    val attributes: PTCreateUserResponseAttributes,
    val links: PTCreateUserResponseLinks,
    val relationships: PTCreateUserResponseRelationships
)

data class PTCreateUserResponseAttributes(
    val claims: Any?,
    @SerializedName("created-at")
    val createdAt: String,
    val disabled: Boolean,
    val email: String,
    val mfaTypes: List<Any>?,
    val name: String,
    @SerializedName("updated-at")
    val updatedAt: String
)

data class PTCreateUserResponseLinks(
    val self: String
)

data class PTCreateUserRes