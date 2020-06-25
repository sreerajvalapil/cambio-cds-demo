package com.cambio.cds.rest.error;

import lombok.*;

@ToString
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class CdsViolation {
    private CDS_ERROR_CODES errorCode;
    private String errorMessage;
}
