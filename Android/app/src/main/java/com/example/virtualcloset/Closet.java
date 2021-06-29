package com.example.virtualcloset;

public class Closet {
    public String notes;
    public byte[] image;
    private int id;

    public Closet(String notes, byte[] image, int id) {
        this.notes = notes;
        this.image = image;
        this.id = id;
    }

    public Closet() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}