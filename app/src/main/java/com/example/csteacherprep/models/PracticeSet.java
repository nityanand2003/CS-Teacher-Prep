package com.example.csteacherprep.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "practice_sets")
public class PracticeSet {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;
    private String exam;
    private String description;
    private long createdAt;

    public PracticeSet() {
    }

    public PracticeSet(String name, String exam, String description, long createdAt) {
        this.name = name;
        this.exam = exam;
        this.description = description;
        this.createdAt = createdAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExam() {
        return exam;
    }

    public void setExam(String exam) {
        this.exam = exam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
}