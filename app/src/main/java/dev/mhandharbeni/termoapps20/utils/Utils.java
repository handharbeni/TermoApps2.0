package dev.mhandharbeni.termoapps20.utils;

import android.annotation.SuppressLint;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;

import androidx.annotation.ColorInt;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
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

    @ColorInt
    static int caretBackground = 0xff666666;

    public final static String newline_crlf = "\r\n";
    public final static String newline_lf = "\n";



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

    public static String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(StandardCharsets.US_ASCII)));
    }

    public static byte[] fromHexString(final CharSequence s) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        byte b = 0;
        int nibble = 0;
        for(int pos = 0; pos<s.length(); pos++) {
            if(nibble==2) {
                buf.write(b);
                nibble = 0;
                b = 0;
            }
            int c = s.charAt(pos);
            if(c>='0' && c<='9') { nibble++; b *= 16; b += c-'0';    }
            if(c>='A' && c<='F') { nibble++; b *= 16; b += c-'A'+10; }
            if(c>='a' && c<='f') { nibble++; b *= 16; b += c-'a'+10; }
        }
        if(nibble>0)
            buf.write(b);
        return buf.toByteArray();
    }

    public static String toHexString(final byte[] buf) {
        return toHexString(buf, 0, buf.length);
    }

    static String toHexString(final byte[] buf, int begin, int end) {
        StringBuilder sb = new StringBuilder(3*(end-begin));
        toHexString(sb, buf, begin, end);
        return sb.toString();
    }

    public static void toHexString(StringBuilder sb, final byte[] buf) {
        toHexString(sb, buf, 0, buf.length);
    }

    static void toHexString(StringBuilder sb, final byte[] buf, int begin, int end) {
        for(int pos=begin; pos<end; pos++) {
            if(sb.length()>0)
                sb.append(' ');
            int c;
            c = (buf[pos]&0xff) / 16;
            if(c >= 10) c += 'A'-10;
            else        c += '0';
            sb.append((char)c);
            c = (buf[pos]&0xff) % 16;
            if(c >= 10) c += 'A'-10;
            else        c += '0';
            sb.append((char)c);
        }
    }

    /**
     * use https://en.wikipedia.org/wiki/Caret_notation to avoid invisible control characters
     */
    public static CharSequence toCaretString(CharSequence s, boolean keepNewline) {
        return toCaretString(s, keepNewline, s.length());
    }

    static CharSequence toCaretString(CharSequence s, boolean keepNewline, int length) {
        boolean found = false;
        for (int pos = 0; pos < length; pos++) {
            if (s.charAt(pos) < 32 && (!keepNewline ||s.charAt(pos)!='\n')) {
                found = true;
                break;
            }
        }
        if(!found)
            return s;
        SpannableStringBuilder sb = new SpannableStringBuilder();
        for(int pos=0; pos<length; pos++)
            if (s.charAt(pos) < 32 && (!keepNewline ||s.charAt(pos)!='\n')) {
                sb.append('^');
                sb.append((char)(s.charAt(pos) + 64));
                sb.setSpan(new BackgroundColorSpan(caretBackground), sb.length()-2, sb.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            } else {
                sb.append(s.charAt(pos));
            }
        return sb;
    }
}
