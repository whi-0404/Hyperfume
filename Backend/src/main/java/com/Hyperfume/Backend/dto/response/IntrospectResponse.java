package com.Hyperfume.Backend.dto.response;

import com.Hyperfume.Backend.exception.ErrorCode;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IntrospectResponse {
    boolean valid;
    ErrorCode errorCode;
}
