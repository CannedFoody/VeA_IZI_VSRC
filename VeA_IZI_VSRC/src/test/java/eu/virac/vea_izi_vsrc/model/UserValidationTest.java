package eu.virac.vea_izi_vsrc.model;

import jakarta.validation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

public class UserValidationTest {
    private Validator validator;

    @BeforeEach
    public void initialize(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validate_correct_user(){
        Department test_department = new Department("Test Department", "Test Description", "Test Goals");

        User test_user = new User("Name", "Surname", "test@gmail.com", "Worker", test_department);

        Set<ConstraintViolation<User>> jpa_violations = validator.validate(test_user);

        assertTrue(jpa_violations.isEmpty());
    }

    @Test
    public void validate_wrong_user(){
        User test_user = new User("", "", "test@gmail.com", "Test", null);

        Set<ConstraintViolation<User>> jpa_violations = validator.validate(test_user);

        assertFalse(jpa_violations.isEmpty());
    }
}
