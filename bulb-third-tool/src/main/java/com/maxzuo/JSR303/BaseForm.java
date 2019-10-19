package com.maxzuo.JSR303;


import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Iterator;
import java.util.Set;

/**
 * Form实体基础类
 * Created by zfh on 2018/10/22
 */
public class BaseForm {

    /**
     * JSR 303参数校验
     */
    protected void validateParam () {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BaseForm>> validate = validator.validate(this);
        Iterator<ConstraintViolation<BaseForm>> iterator = validate.iterator();
        if (iterator.hasNext()) {
            throw new RuntimeException(iterator.next().getMessage());
        }
    }
}
