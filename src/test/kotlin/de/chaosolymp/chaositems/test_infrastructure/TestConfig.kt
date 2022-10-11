package de.chaosolymp.chaositems.test_infrastructure

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.property.PropertyTesting
import java.util.*

class TestConfig : AbstractProjectConfig() {
    override suspend fun beforeProject() {
        PropertyTesting.defaultIterationCount = 10

        // Special handling for CI
        try {
            val ciEnv = System.getenv("CI")
            if(ciEnv?.lowercase(Locale.getDefault())?.equals("true") == true) {
                PropertyTesting.defaultIterationCount = 30
            }
        } catch (exception: SecurityException) {
            println("Cannot fetch `CI` environment variable")
        }
    }
}