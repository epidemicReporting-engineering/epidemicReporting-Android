package com.reporting.epidemic.epidemicreporting.Model;

public class PatientRequestModel {
    private String name;
    private String sex;
    private int age;
    private String career;
    private String symptom;
    private String fabing;
    private String treatment;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getFabing() {
        return fabing;
    }

    public void setFabing(String fabing) {
        this.fabing = fabing;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
}