package ru.atavrel.restclientdemo.dto;

import java.util.Set;

public class UserDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Set<RoleDTO> roles;
    private Integer age;

    public UserDTO() {
    }

    public UserDTO(String firstName, String lastName, String email, String password, Set<RoleDTO> roles) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.age = 0;
        this.roles = roles;
        this.password = password;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
