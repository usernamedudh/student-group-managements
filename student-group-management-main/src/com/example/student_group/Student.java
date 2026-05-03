package com.example.student_group;

public class Student extends Person {
    private String studentId;
    private String speciality;
    private boolean male;
    private double averageScore;

    public Student() {
    }

    public Student(String firstName, String lastName, int age, String studentId, String speciality) {
        this(firstName, lastName, age, studentId, speciality, true, 0.0);
    }

    public Student(String firstName, String lastName, int age, String studentId, String speciality, boolean male,
            double averageScore) {
        super(firstName, lastName, age);
        this.studentId = studentId;
        this.speciality = speciality;
        this.male = male;
        this.averageScore = averageScore;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public boolean isMale() {
        return male;
    }

    public void setMale(boolean male) {
        this.male = male;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    @Override
    public void printInfo() {
        System.out.println("Student -> " + this);
    }

    @Override
    public String toString() {
        return "Student{firstName='" + getFirstName() + "', lastName='" + getLastName() + "', age=" + getAge()
                + ", studentId='" + studentId + "', speciality='" + speciality + "', male=" + male
                + ", averageScore=" + averageScore + "}";
    }
}

