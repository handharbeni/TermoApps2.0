package dev.mhandharbeni.termoapps20.models;

public class UploadReg {
    String data;
    String hash;
    String nik;
    String file_name;
    String nama;
    String trial;
    Double threshold;

    public UploadReg() {
    }

    public UploadReg(String data, String hash, String nik, String file_name, String nama, String trial, Double threshold) {
        this.data = data;
        this.hash = hash;
        this.nik = nik;
        this.file_name = file_name;
        this.nama = nama;
        this.trial = trial;
        this.threshold = threshold;
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

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTrial() {
        return trial;
    }

    public void setTrial(String trial) {
        this.trial = trial;
    }

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }
}
