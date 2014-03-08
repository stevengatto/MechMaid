package com.overthink.mechmaid.webservices;

import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Scanner;

/**
 * Provides helper methods to make web calls. All calls are synchronous and thus should only be run in the context of
 * an asynchronous thread.
 */
public class WebServiceUtils {

    public static final String TAG = WebServiceUtils.class.getName();
    // Empty web service request body
    public static final String EMPTY_REQUEST_BODY = "";
    // Connection time out and download time out
    private static final int CONNECT_TIMEOUT = 2000;
    private static final int READ_TIMEOUT = 3000;
    // Request properties
    public static final String REQUEST_PROPERTY_KEY_CONTENT_TYPE = "Content-Type";
    public static final String REQUEST_PROPERTY_APPLICATION = "application/json";

    /**
     * Make a web call to the provided URL, using the specified method, and return pertinent web service response data
     *
     *
     *
     * @param url the URL_TEMPLATE of the web service to call, as a string
     * @param requestBody the body of the request (optional)
     * @return the response from the server
     */
    public static WebServiceResponse makeHttpPostRequestWith(String url, String requestBody) {
        // HTTP response code (e.g., 200, 401, 404, etc)
        int responseCode;
        // Result of web service call (response data, response code, and exception if any)
        WebServiceResponse response = new WebServiceResponse();
        // URL connection to web service and response code
        HttpURLConnection connection = null;

        // Log request
        Log.i(TAG, "Making HTTP POST request with URL: " + url);
        // Log request body
        Log.i(TAG, "Request body: " + requestBody);

        try {
            // Create a URL from the supplied string
            URL webServiceUrl = new URL(url);

            // Initiate connection
            connection = (HttpURLConnection) webServiceUrl.openConnection();
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            // Indicate that this is a POST request.
            connection.setRequestMethod("POST");

            // Set body length
            connection.setFixedLengthStreamingMode(requestBody.getBytes().length);
            connection.setRequestProperty(REQUEST_PROPERTY_KEY_CONTENT_TYPE, REQUEST_PROPERTY_APPLICATION);

            // Write out data
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            outputStream.write(requestBody.getBytes());
            outputStream.flush();
            outputStream.close();

            // Handle HTTP response code TODO: Handle response code in calling method?
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.e(TAG, String.format("HTTP Unauthorized (%d)", responseCode));
            }
            else if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, String.format("HTTP Error %s", responseCode));
            }

            // Add HTTP response code to return data
            response.setHttpResponseCode(responseCode);

            // get raw response body
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String responseBody = new Scanner(in).useDelimiter("\\A").next();

            // Add response body to return data
            response.setRawResponseBody(responseBody);

            return response;
        }
        catch (MalformedURLException e) {
            Log.e(TAG, "Malformed request URL");
            response.setException(e);
        }
        catch (SocketTimeoutException e) {
            Log.d(TAG, "data retrieval or connection timed out\n" + e.toString());
            response.setException(e);
        }
        catch (IOException e) {
            Log.e(TAG, "I/O Exception in openConnection()");
            response.setException(e);
        }
        finally {
            // Log HTTP response code
            Log.i(TAG, "Web service response code: " + response.getHttpResponseCode());

            // Log response body
            Log.i(TAG, "Web service response: " + response.getRawResponseBody());

            // Always close connection on completion
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }

    /**
     * Make a web call to the provided URL, using the specified method, and return pertinent web service response data
     *
     *
     * @param url the URL_TEMPLATE of the web service to call, as a string
     * @return the response from the server
     */
    public static WebServiceResponse makeHttpGetRequestWith(String url) {
        int responseCode;
        // Result of web service call (response data, response code, and exception if any)
        WebServiceResponse response = new WebServiceResponse();
        // URL connection to web service and response code
        HttpURLConnection connection = null;

        // Log request
        Log.i(TAG, "Making HTTP GET request with URL: " + url);

        try {
            // Create a URL from the supplied string
            URL webServiceUrl = new URL(url);

            // Initiate connection
            connection = (HttpURLConnection) webServiceUrl.openConnection();

            // Set timeout limits
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);

            // Indicate that this is a GET request
            connection.setRequestMethod("GET");

            // Handle HTTP response code TODO: Handle response code in calling method?
            responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
                Log.e(TAG, String.format("HTTP Unauthorized (%d)", responseCode));
            }
            else if (responseCode != HttpURLConnection.HTTP_OK) {
                Log.e(TAG, String.format("HTTP Error %s", responseCode));
            }

            // Add HTTP response code to return data
            response.setHttpResponseCode(responseCode);

            // get raw response body
            InputStream in = new BufferedInputStream(connection.getInputStream());
            String responseBody = new Scanner(in).useDelimiter("\\A").next();

            // Add response body to return data
            response.setRawResponseBody(responseBody);

            return response;
        }
        catch (MalformedURLException e) {
            Log.e(TAG, "Malformed request URL");
            response.setException(e);
        }
        catch (SocketTimeoutException e) {
            Log.d(TAG, "data retrieval or connection timed out\n" + e.toString());
            response.setException(e);
        }
        catch (IOException e) {
            Log.e(TAG, "I/O Exception in openConnection()");
            response.setException(e);
        }
        finally {
            // Log HTTP response code
            Log.i(TAG, "Web service response code: " + response.getHttpResponseCode());

            // Log response body
            Log.i(TAG, "Web service response: " + response.getRawResponseBody());

            // Always close connection on completion
            if (connection != null) {
                connection.disconnect();
            }
        }
        return response;
    }
}
