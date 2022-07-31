package fr.jacgrana.springsecurityjpa.enums;

public enum UserRoleEnum {
    ROLE_ADMIN,
    ROLE_USER,
    ROLE_MANAGER;

    @Override
    public String toString() {
        String toReturn = "";
        switch (this) {
            case ROLE_ADMIN :
                toReturn = "ADMIN";
                break;
            case ROLE_USER:
                toReturn = "USER";
                break;
            case ROLE_MANAGER:
                toReturn = "MANAGER";
                break;
        }
        return toReturn;
    }
}
