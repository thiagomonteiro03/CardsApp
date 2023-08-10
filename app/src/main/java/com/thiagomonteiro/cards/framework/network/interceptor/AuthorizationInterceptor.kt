package com.thiagomonteiro.cards.framework.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(
    private val apiKey: String,
    private val apiHost: String
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = request.newBuilder()
            .addHeader(QUERY_PARAMETER_API_KEY, apiKey)
            .addHeader(QUERY_PARAMETER_API_HOST, apiHost)
            .build()
        return chain.proceed(newRequest)
    }

    companion object{
        private const val QUERY_PARAMETER_API_KEY = "X-RapidAPI-Key"
        private const val QUERY_PARAMETER_API_HOST = "X-RapidAPI-Host"
    }
}