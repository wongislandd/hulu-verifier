import logging

import azure.functions as func
import re


def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')
    fullBody = req.get_body().decode()
    logging.info(fullBody)
    maybeVerificationCodeIdentified = re.findall(r'>\d{6}<', fullBody)
    if maybeVerificationCodeIdentified:
        verificationCodeParsed = re.findall(r'\d{6}', maybeVerificationCodeIdentified[0])[0]
        logging.info(f'Verification code found: {verificationCodeParsed}')
        return func.HttpResponse(verificationCodeParsed)
    else:
        return func.HttpResponse(
             "No verification code found.",
             status_code=404
        )