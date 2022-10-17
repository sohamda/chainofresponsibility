package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class AgeValidator extends Validator {
    @Override
    public void validate(Person person) {
        if(person.getAge() < 18 || person.getAge() > 90) {
            throw new RuntimeException("Don't want to be an 'ageist', but cannot allow this age group.");
        }
    }
}
