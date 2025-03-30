package com.bookonthego.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeleteEventResponseDto {
    private Long eventId;
    private boolean success;
    private String message;
}
