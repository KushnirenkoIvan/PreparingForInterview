package for_testing;


import kushnirenko.ivan.for_testing.PermissionChecker;
import kushnirenko.ivan.for_testing.Visitor;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

public class JUnitTest {

    private Visitor visitor_guest;
    private Visitor visitor_user;
    private Visitor visitor_admin;

    private PermissionChecker permissionChecker;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final Timeout timeout = new Timeout(1000);

    @Before
    public void initialize() {
        visitor_guest = new Visitor("GUEST Vasja", 1);
        visitor_user = new Visitor("USER Petja", 2);
        visitor_admin = new Visitor("ADMIN Kolja", 4);
        permissionChecker = PermissionChecker.getInstance();
    }

    @Test
    public void testCheckPermissionsCorrect() {
        assertTrue(permissionChecker.checkPermissions(visitor_guest, 1));
        assertTrue(permissionChecker.checkPermissions(visitor_user, 2));
        assertTrue(permissionChecker.checkPermissions(visitor_admin, 4));
    }

    @Test
    public void testCheckPermissionsIncorrect() {
        assertFalse(permissionChecker.checkPermissions(visitor_guest, 4));
        assertFalse(permissionChecker.checkPermissions(visitor_guest, 2));
        assertFalse(permissionChecker.checkPermissions(visitor_user, 4));
        assertFalse(permissionChecker.checkPermissions(visitor_user, 1));
        assertFalse(permissionChecker.checkPermissions(visitor_admin, 1));
        assertFalse(permissionChecker.checkPermissions(visitor_admin, 2));
    }

    @Test(expected = NullPointerException.class)
    public void testCheckPermissionsException() {
        permissionChecker.checkPermissions(null, 1);
    }

    @Ignore
    @Test(expected = IllegalArgumentException.class)
    public void testCheckPermissionsExceptionIgnore() {
        //Some code is here
    }

    @Test
    public void testCheckPermissionsIllegalArgument() {
        thrown.expect(IllegalArgumentException.class);
        permissionChecker.checkPermissions(visitor_admin, 5);
    }

    @After
    public void finalize() {
        visitor_admin = null;
        visitor_user = null;
        visitor_guest = null;
        permissionChecker = null;
        System.out.println("Testing have finished.");
    }

}
