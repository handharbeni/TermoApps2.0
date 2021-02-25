package dev.mhandharbeni.termoapps20;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.mlkit.vision.common.InputImage;
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
import com.otaliastudios.cameraview.size.AspectRatio;
import com.otaliastudios.cameraview.size.SizeSelector;
import com.otaliastudios.cameraview.size.SizeSelectors;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;

@SuppressLint("NonConstantResourceId")
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

    boolean isPlay = false;
    FaceDetector detector;

    int delayTakePicture = 5 * 1000;
    boolean isTakingPicture = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                        .setClassificationMode(FaceDetectorOptions.CLASSIFICATION_MODE_ALL)
                        .enableTracking()
                        .build();

        detector = FaceDetection.getClient(realTimeOpts);
        cameraView.addFrameProcessor(frame -> {
            InputImage image = InputImage.fromByteArray(frame.getData(),
                    frame.getSize().getWidth(),
                    frame.getSize().getHeight(),
                    frame.getRotationToView(),
                    InputImage.IMAGE_FORMAT_NV21
            );
            detector.process(image)
                    .addOnSuccessListener(
                            faces -> {
                                new Handler().postDelayed(() -> {
                                    if (isPlay){
                                        if (faces.size() > 0 && !isTakingPicture){
                                            cameraView.clearFrameProcessors();
                                            cameraTakePicture();
                                            isTakingPicture = true;
                                            detector.close();
                                        }
                                    }
                                }, 500);
                            })
                    .addOnFailureListener(
                            Throwable::printStackTrace);
        });
    }
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

        cameraView.addCameraListener(new CameraListener() {
            @Override
            public void onPictureTaken(@NonNull @NotNull PictureResult result) {
                result.toFile(
                        new File(getExternalFilesDir("Photos"), "faces.jpg")
                        , file -> {
                            BottomsheetResult bottomsheetResult = new BottomsheetResult(MainActivity.this, file, MainActivity.this);
                            bottomsheetResult.show(getSupportFragmentManager(), bottomsheetResult.getTag());
                        });
            }
        });
        cameraView.open();
    }

    public void cameraTakePicture(){
        if (!isTakingPicture) cameraView.takePicture();
    }

    @OnClick(R.id.switchCamera)
    public void switchCamera(){
        if (cameraView.getFacing() == Facing.FRONT){
            cameraView.setFacing(Facing.BACK);
        } else {
            cameraView.setFacing(Facing.FRONT);
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @OnClick(R.id.controlFrame)
    public void controlFrame(){
        if (!isPlay){
            isPlay = true;
            frameProcessor();
            controlFrame.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_frame));
        } else {
            isPlay = false;
            cameraView.clearFrameProcessors();
            detector.close();
            controlFrame.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_frame));
        }
    }

    @OnClick(R.id.showLog)
    public void showLog(){
        Intent intent = new Intent(MainActivity.this, ReportActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
        startCamera();
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
            frameProcessor();
        } catch (Exception e){}
    }
}