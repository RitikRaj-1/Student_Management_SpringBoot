package com.example.studentREST.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "StudentNEW")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incrementing ID
    private Long id;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name should contain only alphabets and spaces")
    private String name;

    @Min(value = 1, message = "Class number must be between 1 and 12")
    @Max(value = 12, message = "Class number must be between 1 and 12")
    private int classNumber;

    @Min(value = 10, message = "Age must be between 10 and 40")
    @Max(value = 40, message = "Age must be between 10 and 40")
    private int age;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @Valid
    private Address address;
}
