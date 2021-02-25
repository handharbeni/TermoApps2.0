package dev.mhandharbeni.termoapps20;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.WorkerThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;

import com.google.android.gms.common.util.Base64Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceContour;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;
import com.google.mlkit.vision.face.FaceDetectorOptions;
import com.google.mlkit.vision.face.FaceLandmark;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.otaliastudios.cameraview.BitmapCallback;
import com.otaliastudios.cameraview.CameraListener;
import com.otaliastudios.cameraview.CameraView;
import com.otaliastudios.cameraview.FileCallback;
import com.otaliastudios.cameraview.PictureResult;
import com.otaliastudios.cameraview.controls.Control;
import com.otaliastudios.cameraview.controls.Facing;
import com.otaliastudios.cameraview.controls.Hdr;
import com.otaliastudios.cameraview.controls.PictureFormat;
import com.otaliastudios.cameraview.frame.Frame;
import com.otaliastudios.cameraview.size.AspectRatio;
import com.otaliastudios.cameraview.size.SizeSelector;
import com.otaliastudios.cameraview.size.SizeSelectors;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.fotoapparat.facedetector.Rectangle;
import io.fotoapparat.facedetector.processor.FaceDetectorProcessor;
import io.fotoapparat.facedetector.view.RectanglesView;

//import io.fotoapparat.facedetector.FaceDetector;

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
                    InputImage.IMAGE_FORMAT_NV21 // or IMAGE_FORMAT_YV12
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
                                }, 2000);
                            })
                    .addOnFailureListener(
                            Throwable::printStackTrace);
        });
    }
    void startCamera(){
        SizeSelector width = SizeSelectors.minWidth(10);
        SizeSelector height = SizeSelectors.minHeight(10);
        SizeSelector dimensions = SizeSelectors.and(width, height); // Matches sizes bigger than 1000x2000.
        SizeSelector ratio = SizeSelectors.aspectRatio(AspectRatio.of(1, 1), 0); // Matches 1:1 sizes.

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
//        frameProcessor();
    }

    @OnClick(R.id.cameraView)
    public void cameraTakePicture(){
        if (!isTakingPicture) cameraView.takePicture();
//            runOnUiThread(() -> );
    }

    String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    void drawFace(List<Face> faces){
        try {
            ShapeDrawable sd = new ShapeDrawable(new RectShape());
            sd.getPaint().setColor(0xFFFFFFFF);
            sd.getPaint().setStyle(Paint.Style.STROKE);
            sd.getPaint().setStrokeWidth(2);
            View shapeView = new View(this);
            shapeView.setTag("SHAPEFACE");
            shapeView.setBackground(sd);
//            cameraView.removeView(cameraView.findViewWithTag("SHAPEVIEW"));
            for (Face face : faces) {
                shapeView.setId(face.getTrackingId());
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        Math.round(face.getBoundingBox().width()/2f),
                        Math.round(face.getBoundingBox().height()/2f)
                );
                final int left = (int) (face.getBoundingBox().centerX() * face.getBoundingBox().width());
                final int top = (int) (face.getBoundingBox().centerY() * face.getBoundingBox().height());
                final int right = left + (int) (face.getBoundingBox().width() * face.getBoundingBox().width());
                final int bottom = top + (int) (face.getBoundingBox().height() * face.getBoundingBox().height());

                params.setMargins(left, top, right, bottom);
                cameraView.addView(shapeView, params);
            }
        } catch (Exception e){}
    }

    private String encodeImage(File file){
        try {


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
//                byte[] fileContent = FileUtils.readFileToByteArray(file);

                Log.d(TAG, "onFileReady: absolutePath "+file.getAbsolutePath());
                Log.d(TAG, "onFileReady: absolutePath "+file.length());
                Bitmap src=BitmapFactory.decodeFile(file.getAbsolutePath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                Log.d(TAG, "onFileReady: absolutePath "+src.getByteCount());
                return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
            } else {
                FileInputStream fis = new FileInputStream(file);
                Bitmap bitmap = BitmapFactory.decodeStream(fis);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                return Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
            }
        } catch (Exception ignored){
            return null;
        }
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
//            fotoapparat.start();
        } catch (Exception e){}
    }
}