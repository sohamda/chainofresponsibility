package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class PersonValidator {
    @Autowired
    private NameValidator nameValidator;
    @Autowired
    private AgeValidator ageValidator;
    @Autowired
    private CityValidator cityValidator;

    @PostConstruct
    public void configureValidators() {
        this.nameValidator.setNextValidator(this.ageValidator).setNextValidator(this.cityValidator);
    }

    public void validatePerson(Person person) {
       this.nameValidator.executeValidations(person);
    }
}
