package by.bsuir.exchange.logic;

import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.PermissionEnum;
import by.bsuir.exchange.entity.ResourceEnum;
import by.bsuir.exchange.entity.RoleEnum;

import java.util.EnumSet;
import java.util.Objects;

import static by.bsuir.exchange.entity.PermissionEnum.*;

class Permission{
    private EnumSet<PermissionEnum> permissions;

    Permission(EnumSet<PermissionEnum> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permission that = (Permission) o;
        return Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permissions);
    }

}

public class PermissionChecker {
    private final static int N_COMMANDS = 6;
    private final static int N_RESOURCES = 3;
    private final static int N_ROLES = 4;

    private Permission[][] roleCompetencies;
    private Permission[][] commandCompetencies;
    private PermissionChecker(){}

    private static PermissionChecker instance;

    public static PermissionChecker getInstance(){
        if (instance == null){
            instance = new PermissionChecker();
            instance.commandCompetencies = new Permission[N_ROLES][N_RESOURCES];
            instance.roleCompetencies = new Permission[N_COMMANDS][N_RESOURCES];

            addLoginCommandCompetencies();
            addRegisterCommandCompetencies();
            addGetCourierCommandCompetencies();

            addGuestCompetencies();
            addClientCompetencies();
        }

        return instance;
    }


    private static void addLoginCommandCompetencies(){
        int i = CommandEnum.LOGIN.ordinal();
        EnumSet<PermissionEnum> sessionPermissions = EnumSet.of(CREATE, UPDATE);
        instance.commandCompetencies[i][ResourceEnum.HTTP_SESSION.ordinal()] = new Permission(sessionPermissions);
    }
    /*Fixme admin creation*/
    private static void addGuestCompetencies(){
        int i = RoleEnum.GUEST.ordinal();
        EnumSet<PermissionEnum> sessionPermissions = EnumSet.of(CREATE, UPDATE);
        EnumSet<PermissionEnum> userPermissions = EnumSet.of(CREATE, UPDATE);
        instance.roleCompetencies[i][ResourceEnum.HTTP_SESSION.ordinal()] = new Permission(sessionPermissions);
        instance.roleCompetencies[i][ResourceEnum.USER.ordinal()] = new Permission(userPermissions);
    }

    private static void addRegisterCommandCompetencies() {
        int i = CommandEnum.REGISTER.ordinal();
        EnumSet<PermissionEnum> userPermissions = EnumSet.of(CREATE, UPDATE);
        instance.commandCompetencies[i][ResourceEnum.USER.ordinal()] = new Permission(userPermissions);
    }

    private static void addGetCourierCommandCompetencies(){
        int i = CommandEnum.GET_COURIERS.ordinal();
        EnumSet<PermissionEnum> courierPermissions = EnumSet.of(READ);
        instance.commandCompetencies[i][ResourceEnum.COURIER.ordinal()] = new Permission(courierPermissions);
    }

    private static void addClientCompetencies(){
        int i = RoleEnum.CLIENT.ordinal();
        EnumSet<PermissionEnum> courierPermissions = EnumSet.of(READ);
        instance.roleCompetencies[i][ResourceEnum.COURIER.ordinal()] = new Permission(courierPermissions);
    }

    public boolean checkPermission(RoleEnum role, CommandEnum command){
        int iRole = role.ordinal();
        int iCommand = command.ordinal();
        for (int j = 0; j < N_RESOURCES; j++){
            if (commandCompetencies[iCommand][j] == null){
                continue;
            }
            if (!commandCompetencies[iCommand][j].equals(roleCompetencies[iRole][j])){
                return false;
            }
        }
        return true;
    }
}
