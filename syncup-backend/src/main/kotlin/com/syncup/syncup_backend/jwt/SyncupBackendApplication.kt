package com.syncup.syncup_backend.jwt

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties(JwtProperties::class)
@SpringBootApplication
class SyncupBackendApplication
