package com.backend.library_management_system.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AuthorRequestDto {

    private String name;

    private int age;

    private String mobNo;

    private String email;

}
