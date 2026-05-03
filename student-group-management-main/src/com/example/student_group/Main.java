package com.example.student_group;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Group group = new Group("PI-21");

        Student st1 = new Student("Olena", "Tkachenko", 18, "S001", "CS", false, 90.0);
        Student st2 = new Student("Andrii", "Bondar", 19, "S002", "CS", true, 81.5);
        Student st3 = new Student("Iryna", "Melnyk", 18, "S003", "Math", false, 95.0);
        Student st4 = new Student("Maksym", "Savchuk", 20, "S004", "Physics", true, 74.0);
        Student st5 = new Student("Dmytro", "Koval", 19, "S005", "Economics", true, 88.0);
        Student st6 = new Student("Nazar", "Hrytsenko", 20, "S006", "History", true, 69.0);
        Student st7 = new Student("Yana", "Marchenko", 18, "S007", "Biology", false, 91.0);
        Student st8 = new Student("Vlad", "Shevchenko", 19, "S008", "Chemistry", true, 78.0);
        Student st9 = new Student("Sofia", "Kravets", 18, "S009", "Law", false, 84.0);
        Student st10 = new Student("Roman", "Ivanchuk", 21, "S010", "Philology", true, 72.5);
        Student st11 = new Student("Artem", "Petrenko", 22, "S011", "Medicine", true, 93.0);

        try {
            group.addStudent(st1);
            group.addStudent(st2);
            group.addStudent(st3);
            group.addStudent(st4);
            group.addStudent(st5);
            group.addStudent(st6);
            group.addStudent(st7);
            group.addStudent(st8);
            group.addStudent(st9);
            group.addStudent(st10);
            group.addStudent(st11);
        } catch (GroupOverflowException e) {
            System.out.println("Cannot add student: " + e.getMessage());
        }

        System.out.println("Found student:");
        System.out.println(group.searchStudentByLastName("Koval"));
        group.removeStudentByLastName("Koval");

        group.sortByLastName();
        System.out.println("Sorted by last name:");
        System.out.println(group);

        group.sortByParameter("успеваемость");
        System.out.println("Sorted by average score:");
        System.out.println(group);

        Student[] recruits = group.getMaleStudentsOver18();
        System.out.println("Voenkom list:");
        for (Student recruit : recruits) {
            System.out.println(recruit);
        }

        String filePath = "group_PI-21.csv";
        try {
            group.saveToFile(filePath);
        } catch (IOException e) {
            System.out.println("Save error: " + e.getMessage());
        }

        try {
            Group loadedGroup = Group.loadFromFile(filePath);
            System.out.println("\nLoaded group from file:");
            System.out.println(loadedGroup);
        } catch (IOException | GroupOverflowException e) {
            System.out.println("Load error: " + e.getMessage());
        }
    }
}
