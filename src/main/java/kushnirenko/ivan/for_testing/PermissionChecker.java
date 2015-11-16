package kushnirenko.ivan.for_testing;


/**
 * The class PermissionChecker wrote using
 * Singleton (non lazy with getter) pattern.
 */
public class PermissionChecker {

    static final PermissionChecker permissionChecker = new PermissionChecker();

    public final byte ROLE_GUEST = 1;
    public final byte ROLE_USER = 2;
    public final byte ROLE_ADMIN = 4;

    private PermissionChecker() {
        System.out.println("New checker was created.");
    }

    public static PermissionChecker getInstance() {
        return permissionChecker;
    }

    public boolean checkPermissions(Visitor visitor, int permission) {
        if (permission != ROLE_GUEST && permission != ROLE_USER && permission != ROLE_ADMIN) {
            throw new IllegalArgumentException();
        }
        return (visitor.getPermissions() & permission) > 0 ? true : false;
    }

}
