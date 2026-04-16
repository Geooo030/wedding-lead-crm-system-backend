package com.leadcrm.application.command;

import lombok.Data;

@Data
public class CreateUserCommand {
    private String username;
    private String password;
    private String email;
    private String role;
}