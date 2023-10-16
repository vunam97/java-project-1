package com.vti.form;

import com.vti.entity.Account;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountCreateForm {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private Account.Role role;
    private Integer departmentId;
}
