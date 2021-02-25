package dev.mhandharbeni.termoapps20.models;

public class AddFrUser {
    String nik;
    String nama;

    public AddFrUser() {
    }

    public AddFrUser(String nik, String nama) {
        this.nik = nik;
        this.nama = nama;
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
}
