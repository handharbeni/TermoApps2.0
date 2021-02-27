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
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.UMUM_DATE;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.UMUM_IMAGE;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.UMUM_NAMA;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.UMUM_SUHU;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.UMUM_TUJUAN;

@SuppressLint("SimpleDateFormat")
public class Utils {

    /**
     * @param text
     * @return
     */
    public static byte[] sha256(String text) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return digest.digest(text.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            return new byte[0];
        }
    }

    /**
     * @param hash
     * @return
     */
    public static String bytesToHex(byte[] hash) {
        try {
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if(hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e){
            return "-";
        }
    }

    /**
     * @return
     */
    public static String getCurrentDate() {
        try {
            Calendar c = Calendar.getInstance();
            SimpleDateFormat df =
                    new SimpleDateFormat(AppConstant.DATEPATTERN);
            return df.format(c.getTime());
        } catch (Exception e){
            return "-";
        }
    }

    /**
     * @return
     */
    public static String getDate(){
        try {
            Date date = new Date();
            SimpleDateFormat formatter =
                    new SimpleDateFormat(AppConstant.DATEPATTERN);
            try {
                date = formatter.parse(getCurrentDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return String.valueOf(date.getTime());
        } catch (Exception e){
            return "-";
        }
    }

    public static String getMonth(){
        try {
            Date date = new Date();
            SimpleDateFormat formatter =
                    new SimpleDateFormat(AppConstant.DATEPATTERN);
            try {
                date = formatter.parse(getCurrentDate());
                date.setDate(1);
                date.setHours(0);
                date.setMinutes(0);
                date.setSeconds(0);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return String.valueOf(date.getTime());
        } catch (Exception e){
            return "-";
        }
    }

    public static String getFirstDayInWeek(){
        try {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.clear(Calendar.MINUTE);
            cal.clear(Calendar.SECOND);
            cal.clear(Calendar.MILLISECOND);

            cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
            return String.valueOf(cal.getTimeInMillis());
        } catch (Exception e){
            return "-";
        }
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
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(millis);
            return formatter.format(calendar.getTime());
        } catch (Exception e){
            return "-";
        }
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

    public static HashMap<String, Object> getDataGuest(
            String nama,
            String tujuan,
            String image,
            String date,
            String suhu
    ){
        HashMap<String, Object> data = new HashMap<>();
        data.put(UMUM_NAMA, nama);
        data.put(UMUM_TUJUAN, tujuan);
        data.put(UMUM_IMAGE, image);
        data.put(UMUM_DATE, date);
        data.put(UMUM_SUHU, suhu);
        return data;
    }
}
