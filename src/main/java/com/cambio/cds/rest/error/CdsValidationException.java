package com.cambio.cds.rest.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collection;

@Getter
@AllArgsConstructor
public class CdsValidationException extends RuntimeException {

    private @NonNull Collection<CdsViolation> violations;

    public CdsValidationException(CdsViolation violation){
        if(violation == null){
            throw new NullPointerException();
        }
        this.violations = Arrays.asList(violation);
    }

    public CdsValidationException(CDS_ERROR_CODES errorCode,String errorMessage){
        super(errorMessage);
        this.violations = Arrays.asList(CdsViolation.builder()
                .errorCode(errorCode)
                .errorMessage(errorMessage)
                .build());
    }

}
