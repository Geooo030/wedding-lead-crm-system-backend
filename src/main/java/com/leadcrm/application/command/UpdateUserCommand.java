package com.leadcrm.application.command;

import lombok.Data;

@Data
public class UpdateUserCommand {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
}