package dev.mhandharbeni.termoapps20.models;

public class WhoIsIt {
    String data;
    String hash;

    public WhoIsIt() {
    }

    public WhoIsIt(String data, String hash) {
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
