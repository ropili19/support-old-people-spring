package supportoldpeoplespring.supportoldpeoplespring.dtos;

import supportoldpeoplespring.supportoldpeoplespring.documents.Role;

import java.util.Arrays;

public class UserRolesDto {

    private String mobile;
    private Role[] roles;

    public UserRolesDto() {}

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "UserRolesDto{" +
                ", mobile='" + mobile + '\'' +
                ", roles=" + Arrays.toString(roles) +
                '}';
    }

}
