package br.com.biroska.amazonintegrations.exception.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OriginEnum {

    EXCEPTION_HANDLER("ExceptionHandler"),
    BUSINESS_VALIDATION("BusinessValidation");

    private final String value;

}
