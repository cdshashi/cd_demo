plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.8.0"

}

kotlin {
    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    val coroutinesVersion = "1.6.0"
    val serializationVersion = "1.4.1"
    val ktorVersion = "1.6.0"
    val sqlDelightVersion: String by project
    val koinVersion = "3.2.0"

    sourceSets {
        val commonMain by getting {
            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$serializationVersion")
                implementation("io.ktor:ktor-client-serialization:$ktorVersion")
                // it is expected interfaces
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")

                // koin
                implementation("io.insert-koin:koin-core:$koinVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.google.android.material:material:1.2.1")
                // it is actual platform implementations
                implementation("io.ktor:ktor-client-android:$ktorVersion")
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }

        val iosMain by creating {
            dependencies{
                // it is actual platform implementations
                implementation("io.ktor:ktor-client-ios:$ktorVersion")
                implementation("io.ktor:ktor-client-darwin:$ktorVersion")
            }
        }


//        val iosTest by getting
    }
}

android {
    namespace = "com.countrydelight"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}