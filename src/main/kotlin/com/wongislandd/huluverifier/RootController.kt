package com.wongislandd.huluverifier

import org.apache.catalina.manager.StatusTransformer.formatTime
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private const val DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
private const val SUPER_SECRET_AUTH_CODE = "chrisiscool123"

@RestController
class RootController {

    private val timeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)
    private var latestCode = VerificationCode()

    @RequestMapping("/", produces = arrayOf(MediaType.TEXT_HTML_VALUE))
    fun getCodePretty(): String {
        return getFormattedMessage(latestCode)
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

    private fun getFormattedMessage(verificationCode: VerificationCode): String {
        return "<div><h1> Chris's Hulu verification center: </h1></div>" +
                "<div> I built this so I can automate providing you the verification code to my Hulu. This whole workflow checks my email for the Hulu login verification code <i>every minute</i>, parses the email for the code, then updates here!</div>" +
                "<br>" +
                "<div>The verification code you are looking for is: <b>${verificationCode.code}</b></div>" +
                "<div>This was last updated at ${verificationCode.lastUpdated}</div>" +
                "<div>If this isn't the valid code, please wait up to a minute for the thing to do the thingy.</div>" +
                "<br>" +
                "<div>If you have any suggestions for this flow, let me know!</div>"
    }
}