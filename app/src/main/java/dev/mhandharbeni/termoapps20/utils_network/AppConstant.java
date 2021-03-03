package dev.mhandharbeni.termoapps20.utils_network;

import dev.mhandharbeni.termoapps20.BuildConfig;

public class AppConstant {
    public static String HOST_API = "http://wajh.school1.aiseeyou.tech/api/fr/";
    public static boolean SAFE_OKHTTP = false;

    public static final String PARENT = "facethermal";

    public static final String DATEPATTERN = "dd/MM/yyyy";
    public static final String FILENAME = "faces.jpg";
    public static final String DIRNAME = "Photos";


    public static final String NIK = "NIK";
    public static final String NAMA = "NAMA";
    public static final String ABSENIN = "ABSEN_IN";
    public static final String ABSENOUT = "ABSEN_OUT";
    public static final String SUHUIN = "SUHU_IN";
    public static final String SUHUOUT = "SUHU_OUT";

    public static final String UMUM_NAMA = "UMUM_NAMA";
    public static final String UMUM_TUJUAN = "UMUM_TUJUAN";
    public static final String UMUM_IMAGE = "UMUM_IMAGE";
    public static final String UMUM_DATE = "UMUM_DATE";
    public static final String UMUM_SUHU = "UMUM_SUHU";

    private static final String PEGAWAI = "PEGAWAI";
    private static final String UMUM = "UMUM";

    public static final long MILLISINTERVAL = 1000;
    public static final long MILLISINFUTURE = 10 * MILLISINTERVAL;

    public static final boolean MULTIFACE = false;

    public enum STATE_FETCH{
        WHOISIT,
        REALTIMETRAINING,
        UPLOADREG,
        VERIFY,
        ADDFRUSER,
        DELFRUSER,
        WHOISITMM,
        ABSENIN,
        ABSENOUT,
        CHECKIN
    }

    public enum MODE{
        PEGAWAI(AppConstant.PEGAWAI),
        UMUM(AppConstant.UMUM);

        private String value;

        MODE(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    // values have to be globally unique
    public static final String INTENT_ACTION_GRANT_USB = BuildConfig.APPLICATION_ID + ".GRANT_USB";
    public static final String INTENT_ACTION_DISCONNECT = BuildConfig.APPLICATION_ID + ".Disconnect";
    public static final String NOTIFICATION_CHANNEL = BuildConfig.APPLICATION_ID + ".Channel";
    public static final String INTENT_CLASS_MAIN_ACTIVITY = BuildConfig.APPLICATION_ID + ".MainActivity";

    // values have to be unique within each app
    public static final int NOTIFY_MANAGER_START_FOREGROUND_SERVICE = 1001;
}
