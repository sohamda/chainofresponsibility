package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class NameValidator extends Validator {
    @Override
    public void validate(Person person) {
        if(person.getName().equals("Soham")) {
            throw new RuntimeException("we cannot allow this guy");
        }
    }
}
