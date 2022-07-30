package fr.jacgrana.springsecurityjpa.enums;

public enum UserRoleEnum {
    ROLE_ADMIN,
    ROLE_USER;

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
        }
        return toReturn;
    }
}
