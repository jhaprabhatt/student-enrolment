package com.jha.project.rest.controller.functional;

import com.jha.project.model.Student;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.Map;

import static com.jha.project.model.Status.ADDED;
import static io.swagger.models.HttpMethod.GET;
import static io.swagger.models.HttpMethod.POST;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
public class StudentEnrollmentStepDefs {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private Student request = null;

    private Student response = null;

    @Given("Student with proper parameters")
    public void student_with_proper_parameters(Map<String, String> params) {
        log.info("{}", params);

        request = Student.builder()
                .firstName(params.get("firstName"))
                .lastName(params.get("lastName"))
                .grade(params.get("class"))
                .nationality(params.get("nationality"))
                .build();

        String id = params.get("id");
        if (!StringUtils.isEmpty(id)) {
            request.setId(Long.parseLong(id));
        }

        log.info("Student : {} ", request);

    }

    @When("I submit a {string} request to {string}")
    public void i_submit_request_to_endpoint(String httpMethod, String endpointURL) {
        final String url = "http://localhost:" + port + endpointURL;
        if (POST.name().equals(httpMethod)) {
            String response = restTemplate.postForEntity(
                    url,
                    new HttpEntity<>(request, new HttpHeaders()),
                    String.class).getBody();
            log.info("Response obtained {}", response);
        } else if (GET.name().equals(httpMethod)) {
            response = restTemplate.getForObject(url, Student.class);
            log.info("Response obtained {}", response);
        }
    }

    @Then("student is saved in the database")
    public void student_is_saved_in_the_database() {
        assertThat(request.getId(), is(223445L));
    }

    @Then("I get {int} student record")
    public void i_get_one_student_record(Integer count) {
        assertThat(1, is(count));
        assertThat(response, notNullValue());
        assertThat(response.getStatus(), is(ADDED.getStatus()));
    }
}
