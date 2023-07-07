package com.wongislandd.huluverifier

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

@RestController
class RootController {

    private val timeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
    private var latestCode = VerificationCode()

    @RequestMapping("/")
    fun getCode(): String {
        return latestCode.toString()
    }

    @PostMapping("/")
    fun newCode(@RequestBody newCode: String): String {
        latestCode = VerificationCode(newCode, getCurrentTime())
        return "Successfully updated code to $latestCode"
    }

    private fun getCurrentTime(): String {
        return formatTime(LocalDateTime.now())
    }

    private fun formatTime(time: LocalDateTime): String {
        return timeFormatter.format(time)
    }
}