package com.vti.form;

import com.vti.entity.Account;
import com.vti.validation.DepartmentNameNotExists;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Getter
@Setter
public class DepartmentCreateForm {
    @NotBlank(message = "{DepartmentForm.name.NotBlank}")
    @Length(max = 50, message = "{DepartmentForm.name.Length}")
    @DepartmentNameNotExists
    private String name;

    @NotNull(message = "Số lượng nhân viên không được để trống")
    @Positive(message = "Số lượng nhân viên phải > = 0")
    private Integer totalMembers;

    @Pattern(
            regexp = "DEVELOPER|TESTER|SCRUM_MASTER|PROJECT_MANAGER",
            message = "Loại phòng ban không hợp lệ"
    )
    private String type;
    private List<@Valid AccountCreateForm> accounts;

    @Getter
    @Setter
    public static class AccountCreateForm {
        @NotBlank(message = "Tài khoản không được để trống")
        @Length(max = 50, message = "Tài khoản có tối đa 50 ký tự")
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private Account.Role role;
    }
}
