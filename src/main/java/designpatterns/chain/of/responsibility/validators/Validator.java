package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;

public abstract class Validator {

    private Validator nextValidator;

    public Validator setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
        return this.nextValidator;
    }

    public void executeValidations(Person person) {
        validate(person);
        if(this.nextValidator != null) {
            this.nextValidator.executeValidations(person);
        }
    }

    public abstract void validate(Person person);
}
