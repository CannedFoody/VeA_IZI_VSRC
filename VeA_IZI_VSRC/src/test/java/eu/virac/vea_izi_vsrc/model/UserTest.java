package eu.virac.vea_izi_vsrc.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    @Test
    void testUserCreation() {
        Department test_department = new Department("Test Department", "Test Description", "Test Goals");

        User test_user = new User("Name", "Surname", "test@gmail.com", "Worker", test_department);
        assertEquals("Name", test_user.getName());
        assertEquals("Surname", test_user.getSurname());
        assertEquals("test@gmail.com", test_user.getEmail());
        assertEquals("Worker", test_user.getRole());
        assertEquals(test_department, test_user.getDepartment());
    }

    @Test
    void testUserSetters(){
        Department test_department = new Department("Test Department", "Test Description", "Test Goals");

        User test_user = new User("Name", "Surname", "test@gmail.com", "Worker", test_department);
        test_user.setName("Testname");
        test_user.setSurname("Testsurname");
        test_user.setEmail("test@yahoo.com");
        test_user.setRole("Admin");

        test_department.setName("Test Department again");
        test_user.setDepartment(test_department);

        assertEquals("Testname", test_user.getName());
        assertEquals("Testsurname", test_user.getSurname());
        assertEquals("test@yahoo.com", test_user.getEmail());
        assertEquals("Admin", test_user.getRole());
        assertEquals(test_department, test_user.getDepartment());
    }

    @Test
    void testUserStringFunction(){
        Department test_department = new Department("Test Department", "Test Description", "Test Goals");

        User test_user = new User("Name", "Surname", "test@gmail.com", "Worker", test_department);

        assertNotEquals(null, test_user.toString());
    }
}
