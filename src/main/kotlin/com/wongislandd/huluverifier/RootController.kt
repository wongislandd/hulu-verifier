package com.wongislandd.huluverifier

import org.apache.catalina.manager.StatusTransformer.formatTime
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
private const val SUPER_SECRET_AUTH_CODE = "chrisiscool123"

@RestController
class RootController {

    private val timeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
    private var latestCode = VerificationCode()

    @RequestMapping("/")
    fun getCodePretty(): String {
        return "Verification code: ${latestCode.code} ------- Last updated: ${latestCode.lastUpdated}"
    }

    @RequestMapping("/code")
    fun getCode(): String {
        return latestCode.code
    }

    @PostMapping("/")
    fun newCode(@RequestParam auth: String, @RequestBody newCode: String): String {
        return if (auth == SUPER_SECRET_AUTH_CODE) {
            latestCode = VerificationCode(newCode, getCurrentTime())
            "Successfully updated code to $latestCode"
        } else {
            "Invalid authentication!"
        }
    }

    private fun getCurrentTime(): String {
        return formatTime(LocalDateTime.now())
    }

    private fun formatTime(time: LocalDateTime): String {
        return timeFormatter.format(time)
    }
}