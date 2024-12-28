package com.challenge.votation.application.dto.response;

import java.time.LocalDateTime;

public record SessionResponseDTO( Long id,
                                  String name,
                                  String description,
                                  Integer duration,
                                  LocalDateTime createdAt,
                                  AgendaResponseDTO agenda ) {}
