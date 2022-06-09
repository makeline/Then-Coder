package org.aerovek.chartr.data.model

import org.aerovek.chartr.data.exceptions.RestClientRequestException

class ResponseBase<T> {
    val data: T? = null
    val error: String? = null
    val code: 