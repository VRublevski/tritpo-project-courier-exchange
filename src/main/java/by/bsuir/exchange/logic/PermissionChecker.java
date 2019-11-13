package by.bsuir.exchange.logic;

import by.bsuir.exchange.command.CommandEnum;
import by.bsuir.exchange.entity.PermissionEnum;
import by.bsuir.exchange.entity.ResourceEnum;
import by.bsuir.exchange.entity.RoleEnum;

import java.util.EnumSet;
import java.util.Objects;

import static by.bsuir.exchange.entity.PermissionEnum.CREATE;
import static by.bsuir.exchange.entity.PermissionEnum.UPDATE;

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
    private final static int N_COMMANDS = 4;
    private final static int N_RESOURCES = 2;
    private final static int N_ROLES = 4;

    private static PermissionChecker instance;

    private static void addLoginCommandCompetencies(){
        int i = CommandEnum.LOGIN.ordinal();
        EnumSet<PermissionEnum> sessionPermissions = EnumSet.of(CREATE, UPDATE);
        instance.commandCompetencies[i][ResourceEnum.HTTP_SESSION.ordinal()] = new Permission(sessionPermissions);
    }

    private static void addGuestCompetencies(){
        int i = RoleEnum.GUEST.ordinal();
        EnumSet<PermissionEnum> sessionPermissions = EnumSet.of(CREATE, UPDATE);
        instance.roleCompetencies[i][RoleEnum.GUEST.ordinal()] = new Permission(sessionPermissions);
    }

    public static PermissionChecker getInstance(){
        if (instance == null){
            instance = new PermissionChecker();
            instance.commandCompetencies = new Permission[N_ROLES][N_RESOURCES];
            instance.roleCompetencies = new Permission[N_COMMANDS][N_RESOURCES];

            addLoginCommandCompetencies();
            addGuestCompetencies();
        }

        return instance;
    }

    private Permission[][] roleCompetencies;
    private Permission[][] commandCompetencies;
    private PermissionChecker(){}


    public boolean checkPermission(RoleEnum role, CommandEnum command){
        int iRole = role.ordinal();
        int iCommand = command.ordinal();
        for (int j = 0; j < N_RESOURCES; j++){
            if (commandCompetencies[iCommand][j] == null && roleCompetencies[iRole][j] == null){
                continue;
            }
            if (!commandCompetencies[iCommand][j].equals(roleCompetencies[iRole][j])){
                return false;
            }
        }
        return true;
    }
}
