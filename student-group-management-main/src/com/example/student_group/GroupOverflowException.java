package com.example.student_group;

public class GroupOverflowException extends Exception {
    public GroupOverflowException() {
    }

    public GroupOverflowException(String message) {
        super(message);
    }
}

