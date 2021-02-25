package dev.mhandharbeni.termoapps20.models;

public class Absen {
    String image;
    String nik;
    String name;
    String absenIn;
    String absenOut;
    String suhuin;
    String suhuOut;

    public Absen() {
    }

    public Absen(String image, String nik, String name, String absenIn, String absenOut, String suhuin, String suhuOut) {
        this.image = image;
        this.nik = nik;
        this.name = name;
        this.absenIn = absenIn;
        this.absenOut = absenOut;
        this.suhuin = suhuin;
        this.suhuOut = suhuOut;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsenIn() {
        return absenIn;
    }

    public void setAbsenIn(String absenIn) {
        this.absenIn = absenIn;
    }

    public String getAbsenOut() {
        return absenOut;
    }

    public void setAbsenOut(String absenOut) {
        this.absenOut = absenOut;
    }

    public String getSuhuin() {
        return suhuin;
    }

    public void setSuhuin(String suhuin) {
        this.suhuin = suhuin;
    }

    public String getSuhuOut() {
        return suhuOut;
    }

    public void setSuhuOut(String suhuOut) {
        this.suhuOut = suhuOut;
    }
}
