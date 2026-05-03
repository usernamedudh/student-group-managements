package com.example.student_group;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Group implements Voenkom {
    private final String groupName;
    private final Student[] students = new Student[10];

    public Group() {
        this("");
    }

    public Group(String groupName) {
        this.groupName = groupName;
    }

    public void addStudent(Student student) throws GroupOverflowException {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }

        int freeIndex = findFreeIndex();
        if (freeIndex == -1) {
            throw new GroupOverflowException("Group is full. Max students: 10");
        }

        students[freeIndex] = student;
    }

    public void addStudent(Person person) throws GroupOverflowException {
        if (person instanceof Student) {
            addStudent((Student) person);
        } else {
            throw new IllegalArgumentException("Only Student can be added to group");
        }
    }

    public void addStudentInteractive() throws GroupOverflowException {
        addStudentInteractive(new Scanner(System.in));
    }

    public void addStudentInteractive(Scanner scanner) throws GroupOverflowException {
        Student student = new Student();

        System.out.print("First name: ");
        student.setFirstName(scanner.nextLine().trim());

        System.out.print("Last name: ");
        student.setLastName(scanner.nextLine().trim());

        System.out.print("Age: ");
        student.setAge(Integer.parseInt(scanner.nextLine().trim()));

        System.out.print("Student ID: ");
        student.setStudentId(scanner.nextLine().trim());

        System.out.print("Speciality: ");
        student.setSpeciality(scanner.nextLine().trim());

        System.out.print("Gender (m/f): ");
        String gender = scanner.nextLine().trim();
        student.setMale(gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("male"));

        System.out.print("Average score: ");
        student.setAverageScore(Double.parseDouble(scanner.nextLine().trim()));

        addStudent(student);
    }

    public void sortByLastName() {
        sortByParameter("lastName");
    }

    public void sortByParameter(String parameter) {
        String field = normalizeSortParameter(parameter);
        if (field == null) {
            throw new IllegalArgumentException("Unsupported sort parameter: " + parameter);
        }

        bubbleSort(field);
    }

    public boolean removeStudentByLastName(String lastName) {
        for (int i = 0; i < students.length; i++) {
            Student current = students[i];
            if (current != null && current.getLastName().equalsIgnoreCase(lastName)) {
                students[i] = null;
                return true;
            }
        }
        return false;
    }

    public Student searchStudentByLastName(String lastName) {
        return searchStudentByLastName(lastName, true);
    }

    public Student searchStudentByLastName(String lastName, boolean ignoreCase) {
        for (Student student : students) {
            if (student == null) {
                continue;
            }
            boolean isMatch;
            if (ignoreCase) {
                isMatch = student.getLastName().equalsIgnoreCase(lastName);
            } else {
                isMatch = student.getLastName().equals(lastName);
            }

            if (isMatch) {
                return student;
            }
        }
        return null;
    }

    private int findFreeIndex() {
        for (int i = 0; i < students.length; i++) {
            if (students[i] == null) {
                return i;
            }
        }
        return -1;
    }

    private Student[] getFilledStudents() {
        Student[] copy = new Student[students.length];
        int size = 0;
        for (Student student : students) {
            if (student != null) {
                copy[size] = student;
                size++;
            }
        }
        return Arrays.copyOf(copy, size);
    }

    private String normalizeSortParameter(String parameter) {
        if (parameter == null) {
            return null;
        }

        String p = parameter.trim().toLowerCase();
        if (p.equals("lastname") || p.equals("surname") || p.equals("фамилия")) {
            return "lastName";
        }
        if (p.equals("firstname") || p.equals("name") || p.equals("имя")) {
            return "firstName";
        }
        if (p.equals("age") || p.equals("возраст")) {
            return "age";
        }
        if (p.equals("averagescore") || p.equals("score") || p.equals("успеваемость")) {
            return "averageScore";
        }
        if (p.equals("studentid") || p.equals("id") || p.equals("номер")) {
            return "studentId";
        }
        if (p.equals("speciality") || p.equals("specialty") || p.equals("специальность")) {
            return "speciality";
        }

        return null;
    }

    private void bubbleSort(String field) {
        for (int i = 0; i < students.length - 1; i++) {
            for (int j = 0; j < students.length - 1 - i; j++) {
                Student left = students[j];
                Student right = students[j + 1];

                if (left == null) {
                    continue;
                }
                if (right == null) {
                    continue;
                }

                if (compareStudents(left, right, field) > 0) {
                    students[j] = right;
                    students[j + 1] = left;
                }
            }
        }
    }

    private int compareStudents(Student a, Student b, String field) {
        if (field.equals("lastName")) {
            int byLastName = a.getLastName().compareToIgnoreCase(b.getLastName());
            if (byLastName != 0) {
                return byLastName;
            }
            return a.getFirstName().compareToIgnoreCase(b.getFirstName());
        }

        if (field.equals("firstName")) {
            int byFirstName = a.getFirstName().compareToIgnoreCase(b.getFirstName());
            if (byFirstName != 0) {
                return byFirstName;
            }
            return a.getLastName().compareToIgnoreCase(b.getLastName());
        }

        if (field.equals("age")) {
            if (a.getAge() == b.getAge()) {
                return a.getLastName().compareToIgnoreCase(b.getLastName());
            }
            return a.getAge() - b.getAge();
        }

        if (field.equals("averageScore")) {
            if (a.getAverageScore() == b.getAverageScore()) {
                return a.getLastName().compareToIgnoreCase(b.getLastName());
            }
            return a.getAverageScore() < b.getAverageScore() ? 1 : -1;
        }

        if (field.equals("studentId")) {
            return a.getStudentId().compareToIgnoreCase(b.getStudentId());
        }

        if (field.equals("speciality")) {
            return a.getSpeciality().compareToIgnoreCase(b.getSpeciality());
        }

        return 0;
    }

    @Override
    public Student[] getMaleStudentsOver18() {
        Student[] result = new Student[students.length];
        int count = 0;

        for (Student student : students) {
            if (student != null && student.isMale() && student.getAge() > 18) {
                result[count] = student;
                count++;
            }
        }

        return Arrays.copyOf(result, count);
    }

    public void saveToFile(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

        writer.write(groupName);
        writer.newLine();

        for (Student student : students) {
            if (student == null) {
                continue;
            }
            String line = student.getFirstName() + ","
                    + student.getLastName() + ","
                    + student.getAge() + ","
                    + student.getStudentId() + ","
                    + student.getSpeciality() + ","
                    + student.isMale() + ","
                    + student.getAverageScore();
            writer.write(line);
            writer.newLine();
        }

        writer.close();
        System.out.println("Group saved to: " + fileName);
    }

    public static Group loadFromFile(String fileName) throws IOException, GroupOverflowException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));

        String groupName = reader.readLine();
        if (groupName == null) {
            reader.close();
            throw new IOException("File is empty: " + fileName);
        }

        Group group = new Group(groupName.trim());

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split(",");
            if (parts.length < 7) {
                continue;
            }

            String firstName    = parts[0].trim();
            String lastName     = parts[1].trim();
            int age             = Integer.parseInt(parts[2].trim());
            String studentId    = parts[3].trim();
            String speciality   = parts[4].trim();
            boolean male        = Boolean.parseBoolean(parts[5].trim());
            double averageScore = Double.parseDouble(parts[6].trim());

            Student student = new Student(firstName, lastName, age, studentId, speciality, male, averageScore);
            group.addStudent(student);
        }

        reader.close();
        System.out.println("Group loaded from: " + fileName);
        return group;
    }

    @Override
    public String toString() {
        Student[] copy = getFilledStudents();
        int size = copy.length;

        StringBuilder sb = new StringBuilder();
        sb.append("Group ").append(groupName).append("\n");
        if (size == 0) {
            sb.append("(no students)");
            return sb.toString();
        }

        for (int i = 0; i < size; i++) {
            sb.append(i + 1).append(") ").append(copy[i]).append("\n");
        }

        return sb.toString();
    }
}

