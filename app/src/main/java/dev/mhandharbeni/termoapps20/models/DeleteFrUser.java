package dev.mhandharbeni.termoapps20.models;

public class DeleteFrUser {
    String nik;
    String nama;
    String trial;

    public DeleteFrUser() {
    }

    public DeleteFrUser(String nik, String nama, String trial) {
        this.nik = nik;
        this.nama = nama;
        this.trial = trial;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
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
}
