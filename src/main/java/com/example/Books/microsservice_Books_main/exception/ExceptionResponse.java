package com.example.Books.microsservice_Books_main.exception;

import java.util.Date;

public record ExceptionResponse(Date timestamp, String message, String details) {}
