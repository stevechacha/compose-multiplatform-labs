package com.chachadev.compose_multiplatform_labs

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform