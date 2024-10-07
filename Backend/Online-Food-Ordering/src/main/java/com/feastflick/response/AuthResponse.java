package com.feastflick.response;

import com.feastflick.model.USER_ROLE;
import lombok.Data;

@Data
public class AuthResponse {
    private String token;
    private String message;
    private USER_ROLE role;
}
