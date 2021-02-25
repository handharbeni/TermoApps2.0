package dev.mhandharbeni.termoapps20.utils;

import android.annotation.SuppressLint;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import dev.mhandharbeni.termoapps20.utils_network.AppConstant;

import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.ABSENIN;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.ABSENOUT;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.NAMA;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.NIK;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.SUHUIN;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.SUHUOUT;

@SuppressLint("SimpleDateFormat")
public class Utils {

    /**
     * @param text
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] sha256(String text) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        return digest.digest(text.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * @param hash
     * @return
     */
    public static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder(2 * hash.length);
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i]);
            if(hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * @return
     */
    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df =
                new SimpleDateFormat(AppConstant.DATEPATTERN);
        return df.format(c.getTime());
    }

    /**
     * @return
     */
    public static String getDate(){
        Date date = new Date();
        SimpleDateFormat formatter =
                new SimpleDateFormat(AppConstant.DATEPATTERN);
        try {
            date = formatter.parse(getCurrentDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return String.valueOf(date.getTime());
    }

    /**
     * @param millis
     * @return
     */
    public static String getDate(long millis){
        return getDate(millis, AppConstant.DATEPATTERN);
    }

    /**
     * @param millis
     * @param pattern
     * @return
     */
    public static String getDate(long millis, String pattern){
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return formatter.format(calendar.getTime());
    }

    /**
     * @param nik
     * @param nama
     * @param absenin
     * @param absenout
     * @param suhuin
     * @param suhuout
     * @return
     */
    public static HashMap<String, Object> getData(
            String nik,
            String nama,
            String absenin,
            String absenout,
            String suhuin,
            String suhuout){
        HashMap<String, Object> data = new HashMap<>();
        data.put(NIK, nik);
        data.put(NAMA, nama);
        data.put(ABSENIN, absenin);
        data.put(ABSENOUT, absenout);
        data.put(SUHUIN, suhuin);
        data.put(SUHUOUT, suhuout);
        return data;
    }
}
