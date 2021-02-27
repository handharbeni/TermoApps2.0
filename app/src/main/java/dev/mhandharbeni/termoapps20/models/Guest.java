package dev.mhandharbeni.termoapps20.models;

public class Guest {
    String nama;
    String tujuan;
    String image;
    String date;
    String suhu;

    public Guest() {
    }

    public Guest(String nama, String tujuan, String image, String date, String suhu) {
        this.nama = nama;
        this.tujuan = tujuan;
        this.image = image;
        this.date = date;
        this.suhu = suhu;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTujuan() {
        return tujuan;
    }

    public void setTujuan(String tujuan) {
        this.tujuan = tujuan;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuhu() {
        return suhu;
    }

    public void setSuhu(String suhu) {
        this.suhu = suhu;
    }
}
