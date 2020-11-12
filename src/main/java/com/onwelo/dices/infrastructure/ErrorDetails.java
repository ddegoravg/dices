package com.onwelo.dices.infrastructure;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDetails {

    private LocalDateTime timestamp;
    private String message;
    private String details;

}
