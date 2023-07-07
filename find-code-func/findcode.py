import logging

import azure.functions as func
import re


def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    word = req.get_body().decode()
    maybeVerificationCodes = re.findall(r'\d{6}', word)

    if maybeVerificationCodes:
        verificationCode = maybeVerificationCodes[0]
        logging.info(f'Verification code found: {verificationCode}')
        return func.HttpResponse(verificationCode)
    else:
        return func.HttpResponse(
             "No verification code found.",
             status_code=404
        )