package com.revature.library.dto;

import io.soabase.recordbuilder.core.RecordInterface;

import java.time.LocalDateTime;

@RecordInterface
public interface ErrorResponse {
    String message();

    int status();

    LocalDateTime timestamp();
}
