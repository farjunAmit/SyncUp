package com.example.syncup.data.net

import com.example.syncup.data.session.SessionStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val sessionStore: SessionStore
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val token = runBlocking { sessionStore.tokenFlow.first() }

        if (token.isNullOrBlank()) return chain.proceed(original)

        val authed = original.newBuilder()
            .header("Authorization", "Bearer $token")
            .build()

        return chain.proceed(authed)
    }
}
