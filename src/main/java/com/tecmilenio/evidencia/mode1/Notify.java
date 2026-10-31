package com.tecmilenio.evidencia.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Notify {
    private int id;
    private String message;
    private String type;

    public Notify() {}

    public Notify(int id, String message, String type) {
        this.id = id;
        this.message = message;
        this.type = type;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
