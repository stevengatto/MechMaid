package com.overthink.mechmaid.webservices;

/**
 * Contains all pertinent data returned in web service call.
 */
public class WebServiceResponse {

    private String rawResponseBody;
    private  int httpResponseCode;
    private Exception exception;

    public int getHttpResponseCode() { return httpResponseCode; }

    public void setHttpResponseCode(int httpResponseCode) { this.httpResponseCode = httpResponseCode; }

    public Exception getException() { return exception; }

    public void setException(Exception exception) { this.exception = exception; }

    public String getRawResponseBody() { return rawResponseBody; }

    public void setRawResponseBody(String rawResponseBody) { this.rawResponseBody = rawResponseBody; }
}
