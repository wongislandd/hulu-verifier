package com.wongislandd.huluverifier

private const val DEFAULT_CODE = "default"
private const val DEFAULT_TIME_SET = "never"

data class VerificationCode(private val code: String =  DEFAULT_CODE, private val timeSet: String = DEFAULT_TIME_SET)