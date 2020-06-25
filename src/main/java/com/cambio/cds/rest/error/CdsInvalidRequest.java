package com.cambio.cds.rest.error;

import lombok.*;

import java.util.Collection;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class CdsInvalidRequest {
    private @NonNull Collection<CdsViolation> violations;

}


