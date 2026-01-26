package com.syncup.syncup_backend

import com.syncup.syncup_backend.jwt.JwtProperties
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties::class)
class SyncupBackendApplication

fun main(args: Array<String>) {
	runApplication<SyncupBackendApplication>(*args)
}
