package com.vitulc.sessionmng.dtos;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDto(@NotBlank(message = "A username is required to login") String username,
                              @NotBlank(message = "A password is required to login") String password) { }
