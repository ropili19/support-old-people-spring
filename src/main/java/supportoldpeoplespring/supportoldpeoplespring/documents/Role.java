package supportoldpeoplespring.supportoldpeoplespring.documents;

public enum Role {
    /*ADMIN : TODO, MANAGER: VOLUNTARIO, CUSTOMER: CLIENTE,*/
    ADMIN, VOLUNTARY,  CLIENT, AUTHENTICATED;

    public String roleName() {
        return "ROLE_" + this.toString();
    }

    public boolean isRole() {
        for(Role role: Role.values()) {
            if(!role.roleName().equals(this)) {
                return false;
            }
        }
        return true;
    }

}
