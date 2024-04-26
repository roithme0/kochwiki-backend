package org.acme.ErrorResponse;

public class ErrorResponse {
    /**
     * Status code of the error.
     */
    private int statusCode;

    /**
     * Message of the error.
     */
    private String message;

    /**
     * Constructor.
     * 
     * @param statusCode status code of the error
     * @param message    message of the error
     */
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}