package com.wongislandd.huluverifier

import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

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
            latestCode = VerificationCode(newCode, System.currentTimeMillis())
            "Successfully updated code to $latestCode"
        } else {
            "Invalid authentication!"
        }
    }

    private fun getTimeDifference(previousTime: Long): String {
        val now = System.currentTimeMillis()
        val difference = now - previousTime
        val secondsDifference = TimeUnit.MILLISECONDS.toSeconds(difference)
        return "$secondsDifference seconds"
    }

    private fun getFormattedMessage(verificationCode: VerificationCode): String {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<style>\n" +
                "body {background-color: #1ce783;}\n" +
                "h1   {color: #151515;}\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>" +
                "<div><h1> Chris's Hulu verification center: </h1></div>" +
                "<div> I built this so I can automate providing you the verification code to my Hulu. This whole workflow checks my email for the Hulu login verification code <i>every minute</i>, parses the email for the code, then updates here!</div>" +
                "<br>" +
                "<div>The verification code you are looking for is:</div>" +
                "<div> <h2>${verificationCode.code}</h2></div>" +
                "<div>This was last updated ${getTimeDifference(verificationCode.lastUpdated)} ago.</div>" +
                "<br>" +
                "<div>If this isn't the valid code, please wait up to a minute for the thing to do the thingy.</div>" +
                "<div>If you have any suggestions for this flow, let me know!</div>" +
                "</body>" +
                "</html>"
    }
}