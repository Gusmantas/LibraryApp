package com.company;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class Book implements Serializable {

    private String title, writer, summary;
    private boolean isAvailable = true;
    private LocalDateTime bookDeadline;

    public Book(String title, String writer, String summary) {
        this.title = title;
        this.writer = writer;
        this.summary = summary;
    }

    //Due books time is set to 1 minute for debugging.
    //Kept "LocalDateTime bookDeadline;" as field variable. Different deadline is now set for all borrowed books.
    public void setBookDeadline() {
        Timestamp currentTime = Timestamp.valueOf(LocalDateTime.now());
        LocalDateTime deadline = currentTime.toLocalDateTime().plusSeconds(10);
        bookDeadline = deadline;
    }

    public void showDueTime() {
        System.out.println(bookDeadline.toLocalDate());
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getSummary() {
        return summary;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public LocalDateTime getBookDeadline() {
        return bookDeadline;
    }

    @Override
    public String toString() {
        return String.format("Title: %s. Author: %s.", title, writer);
    }

}
