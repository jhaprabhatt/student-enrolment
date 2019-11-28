package com.jha.project.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "student")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student implements Serializable {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @EqualsAndHashCode.Exclude
    private String firstName;

    @Column(name = "last_name")
    @EqualsAndHashCode.Exclude
    private String lastName;

    @JsonProperty(value = "class")
    @Column(name = "grade")
    @EqualsAndHashCode.Exclude
    private String grade;

    @Column(name = "nationality")
    @EqualsAndHashCode.Exclude
    private String nationality;

    @Column(name = "status")
    @EqualsAndHashCode.Exclude
    private String status;
}
