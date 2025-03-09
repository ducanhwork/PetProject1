package dev.anhpd.utility;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class DobValidator implements ConstraintValidator<DobConstraint, LocalDate> {
    private  int min;
    @Override
    //xay ra truoc khi check valid, lay ve gia tri cua dob
    public void initialize(DobConstraint dob) {
        ConstraintValidator.super.initialize(dob);
        min = dob.min();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext cxt) {
        if(dob == null){
            return false;
        }
        long years = ChronoUnit.YEARS.between(dob, LocalDate.now());
        return years >= min;
    }
}
