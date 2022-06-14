package com.msucil.dev.springbase.core.web;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDetail {

    private int status;
    private String title;
    private Object detail;
    private final LocalDateTime timestamp = LocalDateTime.now();
    private int errorCount = 0;
    private Object errors;
    private String message;
    private String path;
}
