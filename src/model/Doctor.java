package model;

import enums.Gender;

public class Doctor {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private int experienceYear;

    public Doctor() {
    }

    public Doctor(Long id, String firstName, String lastName, Gender gender, int experienceYear) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.experienceYear = experienceYear;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getExperienceYear() {
        return experienceYear;
    }

    public void setExperienceYear(int experienceYear) {
        this.experienceYear = experienceYear;
    }

    @Override
    public String toString() {
        return "\nDoctor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", experienceYear=" + experienceYear +
                '}';
    }
}
