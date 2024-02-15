package com.vitulc.sessionmng.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserDto(@NotBlank(message = "A name is required to register") String name,
                      @NotBlank(message = "A username is required to register") String username,
                      @NotBlank(message = "A email is required to register") String email,
                      @NotBlank(message = "A password is required to register") String password,
                      @NotBlank(message = "You need to confirm your password to register") String confirmPassword) {
}
