package kushnirenko.ivan.for_testing;

/**
 * Created by ivan on 16/11/15.
 */
public class Visitor {

    private String name;

    private int permissions;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPermissions() {
        return permissions;
    }

    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

    public Visitor(String name, int permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return "Visitor{" +
                "name='" + name + '\'' +
                ", permissions=" + permissions +
                '}';
    }
}
