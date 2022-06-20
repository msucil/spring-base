package com.msucil.dev.springbase.web.api.v1.system.users;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.msucil.dev.springbase.core.validator.notemptyvalue.NotEmptyValue;
import com.msucil.dev.springbase.core.validator.verifyproperty.VerifyProperty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@VerifyProperty(
    property = "password",
    verifyWith = "verifyPassword")
public class SystemUserForm {

    @NotEmptyValue
    private String fullName;

    @NotEmptyValue
    private String username;

    @NotEmptyValue
    @Email
    private String email;

    @NotEmptyValue
    private String password;

    @NotEmptyValue
    private String verifyPassword;
}
