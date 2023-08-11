package com.thiagomonteiro.cards.framework.network.interceptor

import com.thiagomonteiro.cards.framework.network.NetworkConstants
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String,
    private val apiHost: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(NetworkConstants.HEADER_PARAMETER_API_KEY, apiKey)
            .addHeader(NetworkConstants.HEADER_PARAMETER_API_HOST, apiHost)
            .build()

        return chain.proceed(newRequest)
    }
}