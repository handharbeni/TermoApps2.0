package dev.mhandharbeni.termoapps20;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.felhr.utils.HexData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.PictureFormat;
import com.otaliastudios.cameraview.markers.AutoFocusMarker;
import com.otaliastudios.cameraview.markers.AutoFocusTrigger;
import com.otaliastudios.cameraview.size.AspectRatio;
import com.otaliastudios.cameraview.size.Size;
import com.otaliastudios.cameraview.size.SizeSelector;
import com.otaliastudios.cameraview.size.SizeSelectors;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.lang.ref.WeakReference;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import dev.mhandharbeni.termoapps20.report_pegawai.ReportActivity;
import dev.mhandharbeni.termoapps20.services.UsbService;
import dev.mhandharbeni.termoapps20.utils.Messages;
import dev.mhandharbeni.termoapps20.utils.Utils;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;
import dev.mhandharbeni.termoapps20.views.FaceBoundOverlay;
import dev.mhandharbeni.termoapps20.views.GraphicOverlay;
import dev.mhandharbeni.termoapps20.views.RectOverlay;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;

@SuppressLint({"NonConstantResourceId", "UseCompatLoadingForDrawables"})
public class MainActivity extends AppCompatActivity implements MultiplePermissionsListener, FaceDetectorProcessor.OnFacesDetectedListener, BottomsheetResult.BottomsheetResultCallback {
    private final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.cameraView)
    CameraView cameraView;

    @BindView(R.id.rectangleView)
    RectanglesView rectangleView;

    @BindView(R.id.switchCamera)
    AppCompatImageButton switchCamera;

    @BindView(R.id.controlFrame)
    AppCompatImageButton controlFrame;

    @BindView(R.id.showLog)
    AppCompatImageButton showLog;

    @BindView(R.id.boundOverlay)
    GraphicOverlay boundOverlay;

    @BindView(R.id.takePicture)
    AppCompatImageButton takePicture;

    @BindView(R.id.showLogGuest)
    AppCompatImageButton showLogGuest;

    boolean isPlay = false;
    FaceDetector detector;

    int delayTakePicture = 5 * 1000;
    boolean isTakingPicture = false;

    boolean hasReturn = false;
    String currentSuhu = "0";
    File files;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHandler = new MainHandler(this);
        ButterKnife.bind(this);
        checkPermission();
    }

    void checkPermission(){
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,
                        Manifest.permission.RECORD_AUDIO
                )
                .withListener(this)
                .check();
    }

    void frameProcessor(){
        FaceDetectorOptions realTimeOpts =
                new FaceDetectorOptions.Builder()
                        .setContourMode(FaceDetectorOptions.CONTOUR_MODE_ALL)
                        .setLandmarkMode(FaceDetectorOptions.LANDMARK_MODE_ALL)
                        .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_FAST)
                        .enableTracking()
                        .build();

        detector = FaceDetection.getClient(realTimeOpts);
        cameraView.addFrameProcessor(frame -> {
            InputImage image = InputImage.fromByteArray(frame.getData(),
                    frame.getSize().getWidth(),
                    frame.getSize().getHeight(),
                    frame.getRotationToUser(),
                    InputImage.IMAGE_FORMAT_NV21
            );
            detector.process(image)
                    .addOnSuccessListener(
                            faces -> {
                                cameraView.getHandler().postDelayed(() -> {
                                    if (isPlay){
                                        if (faces.size() > 0 && !isTakingPicture){
                                            cameraTakePicture();
                                            isTakingPicture = true;
                                        }
                                    }
                                }, 2000);
                                closeFrameProcessor();
                            })
                    .addOnFailureListener(e -> Messages.showAlertMessage(
                            this,
                            "FACE RECOGNITION",
                            "SOMETHING WRONG TO DETECT A FACE "
                    ));
        });
    }

    int pWidth = 0;
    int pHeight = 0;
    void startCamera(){
        SizeSelector width = SizeSelectors.minWidth(10);
        SizeSelector height = SizeSelectors.minHeight(10);
        SizeSelector dimensions = SizeSelectors.and(width, height);
        SizeSelector ratio = SizeSelectors.aspectRatio(AspectRatio.of(1, 1), 0);

        SizeSelector result = SizeSelectors.or(
                SizeSelectors.smallest()
        );
        cameraView.setLifecycleOwner(this);
        cameraView.setPictureFormat(PictureFormat.JPEG);
        cameraView.setHdr(Hdr.OFF);
        cameraView.setRequestPermissions(true);
        cameraView.setPictureMetering(true);
        cameraView.setPictureSize(result);
        cameraView.setPictureMetering(true);
        cameraView.setPreviewStreamSize(source -> {
            if (source.size() > 0) {
                pWidth = source.get(0).getWidth();
                pHeight = source.get(0).getHeight();
            }
            return source;
        });

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull @NotNull PictureResult result) {
                result.toFile(
                        new File(getExternalFilesDir(AppConstant.DIRNAME), AppConstant.FILENAME)
                        , file -> {
                            files = file;

                            closeFrameProcessor();
                            BottomsheetResult bottomsheetResult = new BottomsheetResult(MainActivity.this, file, MainActivity.this);
                            bottomsheetResult.setCancelable(false);
                            bottomsheetResult.show(getSupportFragmentManager(), bottomsheetResult.getTag());
                        });
            }
        });
        cameraView.open();
    }

    @OnClick(R.id.takePicture)
    public void cameraTakePicture(){
        if (usbService != null){
            if (deviceConnected){
                usbService.write(("F2010100"+ Utils.newline_crlf).getBytes());
                usbService.write(("F1010100"+ Utils.newline_crlf).getBytes());
            } else {
                Messages.showAlertMessage(this, "USB Services", "No Sensor detected");
                cameraView.takePicture();
            }
        }
    }

    @OnClick(R.id.switchCamera)
    public void switchCamera(){
        if (cameraView.getFacing() == Facing.FRONT){
            cameraView.setFacing(Facing.BACK);
        } else {
            cameraView.setFacing(Facing.FRONT);
        }
        restartFrameProcessor();
    }

    @OnClick(R.id.controlFrame)
    public void controlFrame(){
        isPlay = !isPlay;

        if (isPlay) openFrameProcessor();
        else closeFrameProcessor();

        changePlayIcon(isPlay);
    }

    void openFrameProcessor(){
        frameProcessor();
    }

    void closeFrameProcessor(){
        cameraView.clearFrameProcessors();
        detector.close();
    }

    void restartFrameProcessor(){
        closeFrameProcessor();
        openFrameProcessor();
    }

    public void changePlayIcon(boolean isPlay){
        if (isPlay) controlFrame.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_frame));
        else controlFrame.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_frame));
    }

    @OnClick(R.id.showLog)
    public void showLog(){
        Intent intent = new Intent(MainActivity.this, ReportActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        try {
            startCamera();
            openFrameProcessor();
        } catch (Exception ignored){}
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
    }

    @Override
    public void onFacesDetected(List<Rectangle> faces) {
        if (faces.size() > 0){
            Log.d(TAG, "onFacesDetected: Face Detected");
        }
    }

    @Override
    public void dismissDialog() {
        try {
            isTakingPicture = false;
            openFrameProcessor();
        } catch (Exception e){}
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            setFilters();
            startService(UsbService.class, usbConnection, null);
        } catch (Exception ignored){}
    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            unregisterReceiver(mUsbReceiver);
            unbindService(usbConnection);
        } catch (Exception ignored){}
    }

    public void convertSuhu(String buffer){
        try {
            String[] sBuffer = buffer.split(Utils.newline_crlf);
            for (String s : sBuffer) {
                boolean header = s.startsWith("F1");
                if (header){
                    hasReturn = true;
                    String sData = s.substring(2);
                    String totalData = sData.substring(0, 2);
                    String keyData = sData.substring(2, 4);
                    String contentData = sData.substring(4, sData.length()-2);
                    String checkSum = sData.substring(sData.length() - 2);

                    StringBuilder sContentData = new StringBuilder();
                    for (int i = 0; i < (contentData.length() / 2); i++) {
                        String aData = contentData.substring(i*2, (i+1)*2);
                        String arg = "0x"+(aData.charAt(1) + "" + aData.charAt(0));
                        byte b = (byte) (Integer.decode(arg).byteValue() ^ Integer.decode("0x"+keyData).byteValue());
                        sContentData.append(String.format("%02X ", b));
                    }

                    long i = Long.parseLong(sContentData.toString().replace(" ", ""), 16);
                    float f = Float.intBitsToFloat((int) i);
                    currentSuhu = Utils.round(f, 2).toString();
                }
            }
        } catch (Exception ignored){

        } finally {
//            if (hasReturn){
//
//            }
            Messages.showSuccessMessage(MainActivity.this, "USB Service", "Suhu anda "+currentSuhu);
            cameraView.takePicture();
        }
    }

    private void startService(Class<?> service, ServiceConnection serviceConnection, Bundle extras) {
        if (!UsbService.SERVICE_CONNECTED) {
            Intent startService = new Intent(this, service);
            if (extras != null && !extras.isEmpty()) {
                Set<String> keys = extras.keySet();
                for (String key : keys) {
                    String extra = extras.getString(key);
                    startService.putExtra(key, extra);
                }
            }
            startService(startService);
        }
        Intent bindingIntent = new Intent(this, service);
        bindService(bindingIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void setFilters() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UsbService.ACTION_USB_PERMISSION_GRANTED);
        filter.addAction(UsbService.ACTION_NO_USB);
        filter.addAction(UsbService.ACTION_USB_DISCONNECTED);
        filter.addAction(UsbService.ACTION_USB_NOT_SUPPORTED);
        filter.addAction(UsbService.ACTION_USB_PERMISSION_NOT_GRANTED);
        registerReceiver(mUsbReceiver, filter);
    }
    /*
     * Notifications from UsbService will be received here.
     */
    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case UsbService.ACTION_USB_PERMISSION_GRANTED: // USB PERMISSION GRANTED
                    deviceConnected = true;
                    Messages.showSuccessMessage(MainActivity.this, "USB Service", "USB Ready");
                    break;
                case UsbService.ACTION_USB_PERMISSION_NOT_GRANTED: // USB PERMISSION NOT GRANTED
                    deviceConnected = false;
                    Messages.showAlertMessage(MainActivity.this, "USB Service", "USB Permission not granted");
                    break;
                case UsbService.ACTION_NO_USB: // NO USB CONNECTED
                    deviceConnected = false;
                    Messages.showWarningMessage(MainActivity.this, "USB Service", "No USB connected");
                    break;
                case UsbService.ACTION_USB_DISCONNECTED: // USB DISCONNECTED
                    deviceConnected = false;
                    Messages.showAlertMessage(MainActivity.this, "USB Service", "USB disconnected");
                    break;
                case UsbService.ACTION_USB_NOT_SUPPORTED: // USB NOT SUPPORTED
                    deviceConnected = false;
                    Messages.showWarningMessage(MainActivity.this, "USB Service", "USB device not supported");
                    break;
            }
        }
    };
    private boolean deviceConnected = false;
    private UsbService usbService;
    private MainHandler mHandler;
    private final ServiceConnection usbConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName arg0, IBinder arg1) {
            usbService = ((UsbService.UsbBinder) arg1).getService();
            usbService.setHandler(mHandler);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            usbService = null;
        }
    };

    /*
     * This handler will be passed to UsbService. Data received from serial port is displayed through this handler
     */
    private static class MainHandler extends Handler {
        private final WeakReference<MainActivity> mActivity;

        public MainHandler(MainActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UsbService.MESSAGE_FROM_SERIAL_PORT:
                    String data = (String) msg.obj;
//                    mActivity.get().display.append(data);
                    break;
                case UsbService.CTS_CHANGE:
                case UsbService.DSR_CHANGE:
                    break;
                case UsbService.SYNC_READ:
                    String buffer = (String) msg.obj;
                    mActivity.get().convertSuhu(buffer);
                    break;
            }
        }
    }

}