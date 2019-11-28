package com.jha.project.rest.controller.unit;

import com.jha.project.model.Student;
import com.jha.project.rest.model.Response;
import com.jha.project.rest.exception.model.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.jha.project.model.Status.ADDED;
import static com.jha.project.model.Status.UPDATED;
import static com.jha.project.rest.common.Messages.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.PUT;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentEnrollmentControllerTest {

    private String url = EMPTY;
    private static final Student student_invalid_id = Student
            .builder()
            .id(-1l)
            .build();

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void add_student_invalid_id() {
        url = "http://localhost:" + port + "/enroll/";
        ApiException response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(student_invalid_id, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(ID_SHOULD_BE_GREATER_THAN_0));
    }

    @Test
    void add_student_blank_firstname() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).build();
        ApiException response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(FIRSTNAME_CANNOT_BE_BLANK));
    }

    @Test
    void add_student_blank_lastname() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).firstName("Hello").build();
        ApiException response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(LASTNAME_CANNOT_BE_BLANK));
    }

    @Test
    void add_student_blank_grade() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).firstName("A").lastName("B").build();
        ApiException response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(CLASS_CANNOT_BE_BLANK));
    }

    @Test
    void add_student_blank_nationality() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).firstName("A").lastName("B").grade("C").build();
        ApiException response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(NATIONALITY_CANNOT_BE_BLANK));
    }

    @Test
    void add_student() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).firstName("A").lastName("B").grade("C").nationality("D").build();
        String response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                String.class).getBody();
        assertThat(response, is(ADDED.getStatus()));
    }

    @Test
    void add_student_duplicate() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(2000l).firstName("A").lastName("B").grade("C").nationality("D").build();
        String response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                String.class).getBody();
        assertThat(response, is(ADDED.getStatus()));

        ApiException duplicateResponse = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(duplicateResponse.getStatus(), is(BAD_REQUEST));
        assertThat(duplicateResponse.getCode(), is(BAD_REQUEST.toString()));
        assertThat(duplicateResponse.getReason(), is("Student with id 2000 Already Exists"));
    }

    @Test
    void update_student_invalid_id() {
        url = "http://localhost:" + port + "/enroll/";
        ApiException response = restTemplate.exchange(
                url + "update",
                PUT,
                new HttpEntity<>(student_invalid_id, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(ID_SHOULD_BE_GREATER_THAN_0));
    }

    @Test
    void update_student_to_value_to_update() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(1000l).build();
        ApiException response = restTemplate.exchange(
                url + "update",
                PUT,
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(ONE_OF_THE_PARAMETERS_SHOULD_BE_NON_BLANK));
    }

    @Test
    void update_student_non_existing_record() {
        url = "http://localhost:" + port + "/enroll/";
        Student param = Student.builder().id(88888l).firstName("D").build();
        ApiException response = restTemplate.exchange(
                url + "update",
                PUT,
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(NOT_FOUND));
        assertThat(response.getCode(), is(NOT_FOUND.toString()));
        assertThat(response.getReason(), is("Student with id: 88888 not found"));
    }

    @Test
    void update_student() {
        url = "http://localhost:" + port + "/enroll/";
        final Student pre = Student.builder().id(1000l).firstName("A").lastName("B").grade("C").nationality("D").status(ADDED.getStatus()).build();
        final Student post = Student.builder().id(1000l).firstName("D").lastName("B").grade("C").nationality("D").status(UPDATED.getStatus()).build();
        final Student param = Student.builder().id(1000l).firstName("D").build();
        Response response = restTemplate.exchange(
                url + "update",
                PUT,
                new HttpEntity<>(param, new HttpHeaders()),
                Response.class).getBody();
        assertThat(response.getPre(), is(pre));
        assertThat(response.getPost(), is(post));
    }

    @Test
    void delete_student_invalid_id() {
        url = "http://localhost:" + port + "/enroll/";
        ApiException response = restTemplate.exchange(
                url + "remove",
                DELETE,
                new HttpEntity<>(student_invalid_id, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(ID_SHOULD_BE_GREATER_THAN_0));
    }

    @Test
    void delete_student_record_not_found() {
        final Student param = Student.builder().id(999999l).build();
        url = "http://localhost:" + port + "/enroll/";
        ApiException response = restTemplate.exchange(
                url + "remove",
                DELETE,
                new HttpEntity<>(param, new HttpHeaders()),
                ApiException.class).getBody();
        assertThat(response.getStatus(), is(NOT_FOUND));
        assertThat(response.getCode(), is(NOT_FOUND.toString()));
        assertThat(response.getReason(), is("Student with id: 999999 not found"));
    }

    @Test
    void delete_student() {
        url = "http://localhost:" + port + "/enroll/";
        final Student param = Student.builder().id(33333l).firstName("A").lastName("B").grade("C").nationality("D").build();
        final String response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                String.class).getBody();
        assertThat(response, is(ADDED.getStatus()));

        url = "http://localhost:" + port + "/enroll/";
        Boolean removeResponse = restTemplate.exchange(
                url + "remove",
                DELETE,
                new HttpEntity<>(param, new HttpHeaders()),
                Boolean.class).getBody();
        assertThat(removeResponse, is(true));
    }

    @Test
    void getAllStudents() {
    }

    @Test
    void get_student_by_id_invalid_input() {
        url = "http://localhost:" + port + "/enroll/get/-1";
        final ApiException response = restTemplate.getForObject(url, ApiException.class);
        assertThat(response.getStatus(), is(BAD_REQUEST));
        assertThat(response.getCode(), is(BAD_REQUEST.toString()));
        assertThat(response.getReason(), is(PLEASE_PROVIDE_A_VALID_ID));
    }

    @Test
    void get_student_by_id() {
        url = "http://localhost:" + port + "/enroll/";
        final Student param = Student.builder().id(77777l).firstName("A").lastName("B").grade("C").nationality("D").build();
        final String response = restTemplate.postForEntity(
                url + "add",
                new HttpEntity<>(param, new HttpHeaders()),
                String.class).getBody();
        assertThat(response, is(ADDED.getStatus()));

        final Student expectedResult = Student.builder().id(77777l).firstName("A").lastName("B").grade("C").nationality("D").status(ADDED.getStatus()).build();
        final Student searchResponse = restTemplate.getForObject(url + "get/77777", Student.class);
        assertThat(searchResponse, is(expectedResult));
    }

    @Test
    void get_students_() {
        url = "http://localhost:" + port + "/enroll/getall";
        final List<Student> searchResponse = restTemplate.getForObject(url, List.class);
        assertThat(searchResponse.size(), is(1));
    }
}