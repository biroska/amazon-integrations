package br.com.biroska.amazonintegrations.exception.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ErrorResponse {

    private final String field;

    private final List<String> messages;

}
