package supportoldpeoplespring.supportoldpeoplespring.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import supportoldpeoplespring.supportoldpeoplespring.dtos.UserProfileDto;
import supportoldpeoplespring.supportoldpeoplespring.dtos.UserRolesDto;

import java.time.LocalDateTime;

@Document

public class User {
    @Id
    private String id;

    @Indexed(unique = true)
    private String mobile;

    private LocalDateTime registrationDate;

    private String username;

    private String password;

    private Boolean active;

    private String email;

    private String dni;

    private String address;

    private Role[] roles;
    public User() {
           this.registrationDate = LocalDateTime.now();
        this.active = true;
    }

    public User(String mobile, String username, String password, String dni, String address, String email) {
        this();
        this.mobile = mobile;
        this.username = username;
        this.dni = dni;
        this.address = address;
        this.email = email;
        this.setPassword(password);
        this.roles = new Role[]{Role.CLIENT};
    }

    public User(String mobile, String username, String password) {
        this(mobile, username, password, null, null, null);
    }

    public User(String id, String username, String dni, String email, String address, String password, UserRolesDto userRolesDto) {
        this.id=id;
        this.mobile=userRolesDto.getMobile();
        this.roles=userRolesDto.getRoles();
        this.setUsername(username);
        this.setDni(dni);
        this.setEmail(email);
        this.setAddress(address);
        this.setPassword(password);


    }
    public User(String id, String username, String dni, String email, String address, Role[] roles, UserProfileDto userProfileDto) {
        this.id=id;
        this.mobile=userProfileDto.getMobile();
        this.password=userProfileDto.getPassword();
        this.setPassword(this.password);
        this.setUsername(username);
        this.setDni(dni);
        this.setEmail(email);
        this.setAddress(address);
        this.setRoles(roles);

    }
    public Boolean isActive() {
        return active;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Role[] getRoles() {
        return roles;
    }

    public void setRoles(Role[] roles) {
        this.roles = roles;
    }
}
