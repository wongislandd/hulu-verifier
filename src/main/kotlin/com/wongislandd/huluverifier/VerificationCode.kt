package com.wongislandd.huluverifier

private const val DEFAULT_CODE = "default"
private const val DEFAULT_TIME_UPDATED = "never"

data class VerificationCode(val code: String =  DEFAULT_CODE, val lastUpdated: String = DEFAULT_TIME_UPDATED)