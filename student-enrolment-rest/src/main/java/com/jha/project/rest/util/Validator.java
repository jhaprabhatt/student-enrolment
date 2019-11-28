package com.jha.project.rest.util;

import com.jha.project.model.Student;

import static com.jha.project.rest.common.Messages.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class Validator {

    public static final void validateOnAdd(Student student) {
        if (student.getId() <= 0) {
            throw new IllegalArgumentException(ID_SHOULD_BE_GREATER_THAN_0);
        }
        if (isEmpty(student.getFirstName())) {
            throw new IllegalArgumentException(FIRSTNAME_CANNOT_BE_BLANK);
        }
        if (isEmpty(student.getLastName())) {
            throw new IllegalArgumentException(LASTNAME_CANNOT_BE_BLANK);
        }
        if (isEmpty(student.getGrade())) {
            throw new IllegalArgumentException(CLASS_CANNOT_BE_BLANK);
        }
        if (isEmpty(student.getNationality())) {
            throw new IllegalArgumentException(NATIONALITY_CANNOT_BE_BLANK);
        }
    }

    public static final void validateOnUpdate(Student student) {
        if (student.getId() <= 0) {
            throw new IllegalArgumentException(ID_SHOULD_BE_GREATER_THAN_0);
        }
        if (
                isEmpty(student.getFirstName())
                        && isEmpty(student.getLastName())
                        && isEmpty(student.getGrade())
                        && isEmpty(student.getNationality())
        ) {
            throw new IllegalArgumentException(ONE_OF_THE_PARAMETERS_SHOULD_BE_NON_BLANK);
        }
    }

    public static final void validateOnDelete(Student student) {
        if (student.getId() <= 0) {
            throw new IllegalArgumentException(ID_SHOULD_BE_GREATER_THAN_0);
        }
    }

    public static final void validateOnSearch(final Long id) {
        if (id <= 0) {
            throw new IllegalArgumentException(PLEASE_PROVIDE_A_VALID_ID);
        }
    }
}
