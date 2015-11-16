package for_testing;

import kushnirenko.ivan.for_testing.PermissionChecker;
import kushnirenko.ivan.for_testing.Visitor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    @Mock
    PermissionChecker permissionChecker;
    Visitor visitor;


    @Before
    public void workingExample() {
        visitor = new Visitor("Don Pedro", PermissionChecker.ROLE_USER);
        permissionChecker.checkPermissions(visitor, PermissionChecker.ROLE_USER);

    }

    @Test
    public void test() {
        verify(permissionChecker).checkPermissions(visitor, 2);
    }

    /**
     * If we have to work with Stub:
     */
    @Test
    public void stubTest() {
        stub(permissionChecker.checkPermissions(visitor, PermissionChecker.ROLE_ADMIN)).toReturn(true);
        assertEquals(true, permissionChecker.checkPermissions(visitor, PermissionChecker.ROLE_ADMIN));
    }

    @Ignore
    @Test
    public void testNothing() {
        doNothing().when(permissionChecker.checkPermissions(visitor, 2));
    }

    @Test
    public void checkTimes() {
        verify(permissionChecker, times(1)).checkPermissions(visitor, 2);
    }

    @Ignore
    @Test
    public void checkHandling() {
        verifyZeroInteractions(permissionChecker);
    }


}
