package com.axbg.shelf.security;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Jwt {
    private String token;
}
