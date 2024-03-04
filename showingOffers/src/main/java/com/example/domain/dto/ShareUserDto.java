package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ShareUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String patronymic;
}
