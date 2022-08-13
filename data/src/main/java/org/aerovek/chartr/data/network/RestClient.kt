package org.aerovek.chartr.data.network

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.aerovek.chartr.data.model.ResponseBase
import java.io.IOException

/**
 * This class has two different versions of GET and POST methods. The main reason
 * being that the El