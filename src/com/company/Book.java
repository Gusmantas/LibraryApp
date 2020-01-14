package com.company;

import java.time.LocalDateTime;
import java.util.ArrayList;


public class Book {

    private String title, writer, summary;
    private boolean isAvailable = true;
    private LocalDateTime currentTime = LocalDateTime.now();
    private LocalDateTime deadline = currentTime.plusWeeks(2);

    public Book(String title, String writer, String summary) {
        this.title = title;
        this.writer = writer;
        this.summary = summary;
    }

    public void startReminder() {
        System.out.println(deadline);
        if (currentTime == deadline) {
            System.out.printf("Time for %s is over!", title);
        }
    }


    public void showDueTime(){
        System.out.println(deadline.toLocalDate());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format("Title: %s. Author: %s.", title, writer);
    }
}
