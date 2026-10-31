package com.tecmilenio.evidencia.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class File {
    private int id;
    private String name;
    private long sizeBytes;

    public File() {}

    public File(int id, String name, long sizeBytes) {
        this.id = id;
        this.name = name;
        this.sizeBytes = sizeBytes;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }
}
