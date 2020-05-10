package supportoldpeoplespring.supportoldpeoplespring.dtos;

public class UserQueryDto {

    private String username;

    private String mobile;

    private String dni;

    private String address;

    private boolean onlyCustomer;

    public UserQueryDto() {

    }

    public UserQueryDto(String username, String mobile, String dni, String address, boolean onlyCustomer) {
        this.username = username;
        this.mobile = mobile;
        this.dni = dni;
        this.address = address;
        this.onlyCustomer = onlyCustomer;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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


    public boolean isOnlyCustomer() {
        return onlyCustomer;
    }

    public void setOnlyCustomer(boolean onlyCustomer) {
        this.onlyCustomer = onlyCustomer;
    }

    @Override
    public String toString() {
        return "UserQueryDto{" +
                "username='" + username + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dni='" + dni + '\'' +
                ", address='" + address + '\'' +
                ", onlyCustomer=" + onlyCustomer +
                '}';
    }
}
