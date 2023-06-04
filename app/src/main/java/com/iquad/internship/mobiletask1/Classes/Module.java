package com.iquad.internship.mobiletask1.Classes;

public class Module {
    private String id;
    private int status;
    private String name;

    public Module(String id, int status, String name) {
        this.id = id;
        this.status = status;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Module{" +
                "id='" + id + '\'' +
                ", status=" + status +
                ", name='" + name + '\'' +
                '}';
    }
}
