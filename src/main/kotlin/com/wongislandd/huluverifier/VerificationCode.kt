package com.wongislandd.huluverifier

private const val DEFAULT_CODE = "default"

data class VerificationCode(val code: String =  DEFAULT_CODE, val lastUpdated: Long = System.currentTimeMillis())