package dev.mhandharbeni.termoapps20.models;

public class WhoIsItMM {
    String data;
    String hash;

    public WhoIsItMM() {
    }

    public WhoIsItMM(String data, String hash) {
        this.data = data;
        this.hash = hash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
