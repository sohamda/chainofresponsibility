package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;
import org.springframework.stereotype.Component;

@Component
public class CityValidator extends Validator {
    @Override
    public void validate(Person person) {
        if(person.getCity().equals("Amsterdam")) {
            throw new RuntimeException("Such a busy city..uff!!");
        }
        if(getNextValidator() != null) {
            getNextValidator().validate(person);
        }
    }
}
