package designpatterns.chain.of.responsibility.validators;

import designpatterns.chain.of.responsibility.entity.Person;

public abstract class Validator {

    private Validator nextValidator;

    public Validator setNextValidator(Validator nextValidator) {
        this.nextValidator = nextValidator;
        return this.nextValidator;
    }

    public Validator getNextValidator() {
        return nextValidator;
    }

    public abstract void validate(Person person);
}
