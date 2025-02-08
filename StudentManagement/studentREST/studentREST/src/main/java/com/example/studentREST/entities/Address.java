package com.example.studentREST.entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-generated ID
    private Long id;

    private String street;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "City should contain only alphabets and spaces")
    private String city;

    @Min(value = 100000, message = "Pin must be a six-digit number")
    @Max(value = 999999, message = "Pin must be a six-digit number")
    @Column(nullable = false)
    private int pin;

    @NotBlank
    @Column(nullable = false)
    @Pattern(regexp = "^[A-Za-z ]+$", message = "State should contain only alphabets and spaces")
    private String state;
}
