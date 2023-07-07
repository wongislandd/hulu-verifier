import logging

import azure.functions as func


def main(req: func.HttpRequest) -> func.HttpResponse:
    logging.info('Python HTTP trigger function processed a request.')

    word = req.params.get('word')
    associationCount = req.params.get('associations')

    if word and associationCount:
        result = "AHHHHHHHH"
        return func.HttpResponse(result)
    else:
        return func.HttpResponse(
             "This HTTP triggered function executed successfully but required input was not found.",
             status_code=200
        )