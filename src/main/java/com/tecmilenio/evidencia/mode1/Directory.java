package com.tecmilenio.evidencia.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Directory {
    private int id;
    private String path;

    public Directory() {}

    public Directory(int id, String path) {
        this.id = id;
        this.path = path;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}
