package designpatterns.chain.of.responsibility;

import designpatterns.chain.of.responsibility.entity.Person;
import designpatterns.chain.of.responsibility.validators.AgeValidator;
import designpatterns.chain.of.responsibility.validators.CityValidator;
import designpatterns.chain.of.responsibility.validators.NameValidator;
import designpatterns.chain.of.responsibility.validators.PersonValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class PersonValidatorTests {

    @Spy
    NameValidator nameValidator;
    @Spy
    AgeValidator ageValidator;
    @Spy
    CityValidator cityValidator;

    @Spy
    @InjectMocks
    PersonValidator personValidator;

    @BeforeEach
    void init() {
        personValidator.configureValidators();
    }

    @Test
    void validateOrder() {
        Person person = new Person("Tom", 39, "Utrecht");
        assertDoesNotThrow(() -> {
           personValidator.validatePerson(person);
        });

        InOrder inOrder = inOrder(nameValidator, ageValidator, cityValidator);

        inOrder.verify(nameValidator, times(1)).validate(eq(person));
        inOrder.verify(ageValidator, times(1)).validate(eq(person));
        inOrder.verify(cityValidator, times(1)).validate(eq(person));
    }

    @Test
    void validateNameValidationFailure() {
        Person person = new Person("Soham", 39, "Utrecht");

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            personValidator.validatePerson(person);
        });

        assertEquals("we cannot allow this guy", thrown.getMessage());

        InOrder inOrder = inOrder(nameValidator, ageValidator, cityValidator);

        inOrder.verify(nameValidator, times(1)).validate(eq(person));
        inOrder.verify(ageValidator, times(0)).validate(eq(person));
        inOrder.verify(cityValidator, times(0)).validate(eq(person));
    }

    @ParameterizedTest
    @MethodSource("testExceptions")
    void validateFailures(Person person, String errorMsg, int timesNameValidator,
                          int timesAgeValidator, int timesCityValidator) {
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            personValidator.validatePerson(person);
        });

        assertEquals(errorMsg, thrown.getMessage());

        InOrder inOrder = inOrder(nameValidator, ageValidator, cityValidator);

        inOrder.verify(nameValidator, times(timesNameValidator)).validate(eq(person));
        inOrder.verify(ageValidator, times(timesAgeValidator)).validate(eq(person));
        inOrder.verify(cityValidator, times(timesCityValidator)).validate(eq(person));
    }

    private static Stream<Arguments> testExceptions() {
        return Stream.of(
                Arguments.of(new Person("Soham", 39, "Utrecht"), "we cannot allow this guy", 1, 0, 0),
                Arguments.of(new Person("Tommy", 16, "Utrecht"), "Don't want to be an 'ageist', but cannot allow this age group.", 1, 1, 0),
                Arguments.of(new Person("Tommy", 39, "Amsterdam"), "Such a busy city..uff!!", 1, 1, 1)
        );
    }
}
