import logging
import json
import azure.functions as func
import re

def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')
    body_str = req.get_body().decode()
    body = json.loads(body_str)
    sender = body["SenderName"].replace("\"", "")
    snippet = body["Snippet"]
    maybe_verification_code_identified = re.findall(r'\b\d{6}\b', snippet)
    if maybe_verification_code_identified:
        verification_code_parsed = re.findall(r'\d{6}', maybe_verification_code_identified[0])[0]
        logging.info(f'Verification code from {sender} found: {verification_code_parsed}')
        return func.HttpResponse(f"{sender} code: {verification_code_parsed}")
    else:
        return func.HttpResponse(
             body_str,
             status_code=404
        )