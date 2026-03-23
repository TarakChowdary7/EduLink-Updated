package com.edulink.complianceservice.dto;

public class CreateSchoolRequest {
    private String schoolId;
    private String name;
    private String address;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String email;

    public CreateSchoolRequest() {}

    public CreateSchoolRequest(String schoolId, String name, String address, String city, String state, String country, String phone, String email) {
        this.schoolId = schoolId;
        this.name = name;
        this.address = address;
        this.city = city;
        this.state = state;
        this.country = country;
        this.phone = phone;
        this.email = email;
    }

    // Getters and Setters
    public String getSchoolId() { return schoolId; }
    public void setSchoolId(String schoolId) { this.schoolId = schoolId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}