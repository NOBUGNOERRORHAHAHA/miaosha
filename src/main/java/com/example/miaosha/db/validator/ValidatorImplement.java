package com.example.miaosha.db.validator;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


/**
 * @author FanQie
 * @date 2019/8/11 13:29
 */
@Component
public class ValidatorImplement implements InitializingBean {

    private Validator validator;

    public ValidationResult validate(Object bean) {
        ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constrainViolationSet = validator.validate(bean);
        if (constrainViolationSet.size() > 0) {
            result.setHasErrors(true);
            constrainViolationSet.forEach(constrainViolation->{
                String errMsg = constrainViolation.getMessage();
                String propertyName = constrainViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName,errMsg);
            });
        }
        return  result;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
