package com.jha.project.rest.model;

import com.jha.project.model.Student;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Response {

    private final Student pre;
    private final Student post;
}
