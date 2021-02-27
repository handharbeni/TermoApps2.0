package dev.mhandharbeni.termoapps20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.firestore.DocumentReference;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import dev.mhandharbeni.termoapps20.databases.FirestoreModule;
import dev.mhandharbeni.termoapps20.interfaces.ResultCallback;
import dev.mhandharbeni.termoapps20.models.AddFrUser;
import dev.mhandharbeni.termoapps20.models.Guest;
import dev.mhandharbeni.termoapps20.models.UploadReg;
import dev.mhandharbeni.termoapps20.models.Verify;
import dev.mhandharbeni.termoapps20.models.WhoIsItMM;
import dev.mhandharbeni.termoapps20.presenters.ResultPresenter;
import dev.mhandharbeni.termoapps20.responses.addfruser.AddFRUserResponse;
import dev.mhandharbeni.termoapps20.responses.verify.VerifyResponse;
import dev.mhandharbeni.termoapps20.responses.whoisitmm.WhoIsItMMResponse;
import dev.mhandharbeni.termoapps20.utils.Messages;
import dev.mhandharbeni.termoapps20.utils.Utils;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;
import rx.Subscription;

@SuppressLint({"NonConstantResourceId", "SetTextI18n"})
public class BottomsheetResult extends BottomSheetDialogFragment implements
        ResultCallback.ResultResponseCallback,
        FirestoreModule.FirestoreModuleCallback {
    private final String TAG = BottomsheetResult.class.getSimpleName();
    Activity activity;
    Subscription mSubscription;
    File file;
    BottomsheetResultCallback bottomsheetResultCallback;
    ResultPresenter resultPresenter;
    View view;

    @BindView(R.id.imageResult)
    ImageView imageResult;

    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.nik)
    EditText nik;

    @BindView(R.id.ok)
    Button ok;

    @BindView(R.id.addUser)
    Button addUser;

    @BindView(R.id.addAssets)
    Button addAssets;

    @BindView(R.id.verify)
    Button verify;

    @BindView(R.id.llAbsen)
    LinearLayout llAbsen;

    @BindView(R.id.btnAbsenIn)
    Button btnAbsenIn;

    @BindView(R.id.btnAbsenOut)
    Button btnAbsenOut;

    ProgressDialog progressDialog;

    AppConstant.STATE_FETCH currentState;
    FirestoreModule firestoreModule;

    private boolean isEmployee = false;
    private CountDownTimer countDownTimer;

    public static BottomsheetResult newInstance(
            Activity activity,
            File file,
            BottomsheetResultCallback bottomsheetResultCallback) {
        return new BottomsheetResult(activity, file, bottomsheetResultCallback);
    }

    public BottomsheetResult(
            Activity activity,
            File file,
            BottomsheetResultCallback bottomsheetResultCallback) {
        this.activity = activity;
        this.file = file;
        this.bottomsheetResultCallback = bottomsheetResultCallback;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(
            @NonNull @NotNull LayoutInflater inflater,
            @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
            @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bottomsheet_result, container, false);
        ButterKnife.bind(this, view);

        byte[] decodedString = Base64.decode(encodeImage(file), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        imageResult.setImageBitmap(decodedByte);
        firestoreModule = FirestoreModule.getInstance(this);
        return view;
    }

    @Override
    public void onViewCreated(
            @NonNull @NotNull View view,
            @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        resultPresenter = new ResultPresenter(activity, mSubscription, firestoreModule);
        resultPresenter.attachView(this);

        try {
            currentState = AppConstant.STATE_FETCH.WHOISITMM;

            String encoded = encodeImage(file);
            WhoIsItMM whoIsItMM = new WhoIsItMM();
            whoIsItMM.setData(encoded);
            whoIsItMM.setHash(Utils.bytesToHex(Utils.sha256(encoded)));
            resultPresenter.whoIsItMM(whoIsItMM);
        } catch (Exception ignored) {}
    }



    String encodeBitmap(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    private String encodeImage(File file){
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Bitmap src= BitmapFactory.decodeFile(file.getAbsolutePath());
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                src.compress(Bitmap.CompressFormat.JPEG, 100, baos);
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

    @Override
    public void onDismiss(@NonNull @NotNull DialogInterface dialog) {
        super.onDismiss(dialog);
//        try {
////            progressDialog.dismiss();
////            countDownTimer.cancel();
//        } catch (Exception ignored ){}
    }

    @Override
    public void fetchLoad(String message) {
        progressDialog = ProgressDialog.show(
                getActivity(),
                message,
                "Please wait...",
                true);
    }

    @Override
    public void fetchSuccess(Object anyClass) {
        try {
            progressDialog.dismiss();
            switch (currentState){
                case WHOISIT:
                    break;
                case REALTIMETRAINING:
                    break;
                case UPLOADREG:
                    
                    break;
                case VERIFY:
                    processVerify(anyClass);
                    break;
                case ADDFRUSER:
                    processAddFrUser(anyClass);
                    break;
                case DELFRUSER:
                    break;
                case WHOISITMM:
                    processWhoIsItMM(anyClass);
                    break;
                case ABSENIN:
                    break;
                case ABSENOUT:
                    break;
            }
        } catch (Exception ignored){}
    }

    void processVerify(Object anyClass){
        try {
            VerifyResponse verifyResponse = (VerifyResponse) anyClass;
            if (verifyResponse.getData().get(0).isIsSamePerson()){
                llAbsen.setVisibility(View.VISIBLE);
            }
        } catch (Exception ignored){}
    }

    void processAddFrUser(Object anyClass){
        try {
            // if success continue to upload reg
            AddFRUserResponse addFRUserResponse = (AddFRUserResponse) anyClass;
            processUploadReg();
        } catch (Exception ignored){}
    }

    @OnFocusChange(R.id.name)
    public void onNameFocusChanged(boolean focused){
        try {
            if (focused) countDownTimer.cancel();
        } catch (Exception ignored){}
    }

    @OnClick(R.id.addAssets)
    public void processUploadReg(){
        // done
        try {
            currentState = AppConstant.STATE_FETCH.UPLOADREG;

            String encoded = encodeImage(file);
            UploadReg uploadReg = new UploadReg();
            uploadReg.setNama(name.getText().toString());
            uploadReg.setData(encoded);
            uploadReg.setHash(Utils.bytesToHex(Utils.sha256(encoded)));
            uploadReg.setNik(nik.getText().toString());
            uploadReg.setThreshold(0.1);
            uploadReg.setTrial("yes");
            uploadReg.setFile_name(System.currentTimeMillis()+".jpg");

            resultPresenter.uploadReg(uploadReg);
        } catch (Exception ignored){}
    }

    @OnClick(R.id.verify)
    public void verifyFaces(){
        try {
            currentState = AppConstant.STATE_FETCH.VERIFY;
            String encoded = encodeImage(file);
            Verify verify = new Verify();
            verify.setNama(name.getText().toString());
            verify.setNik(nik.getText().toString());
            verify.setData(encoded);
            verify.setHash(Utils.bytesToHex(Utils.sha256(encoded)));
            verify.setFile_name(AppConstant.FILENAME);

            resultPresenter.verify(verify);
        } catch (Exception ignored){}
    }

    void processWhoIsItMM(Object anyClass){
        try {
            WhoIsItMMResponse whoIsItMMResponse = (WhoIsItMMResponse) anyClass;
            if (whoIsItMMResponse.getData().size() > 0){
                String person = whoIsItMMResponse.getData().get(0).getPersonInPicture();
                if (person.equalsIgnoreCase("unknown")){
                    ok.setText("OK & SAVE GUEST LOG");
                    addUser.setVisibility(View.VISIBLE);
                    countDownTimer = new CountDownTimer(AppConstant.MILLISINFUTURE
                            , AppConstant.MILLISINTERVAL){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            long lResult = millisUntilFinished / 1000;
                            if (lResult == 0) ok.setText("OK & SAVE GUEST LOG");
                            else ok.setText(String.format("OK & SAVE GUEST LOG (%s)", String.valueOf(lResult)));
                        }

                        @Override
                        public void onFinish() {
                            ok.setText("SAVING");
                            proceedGuest();
                        }
                    };
                    countDownTimer.start();
                } else {
                    isEmployee = true;
                    ok.setText("OK");
                    name.setText(person);
                    nik.setText(whoIsItMMResponse.getData().get(0).getPipNik());
                    llAbsen.setVisibility(View.VISIBLE);
                    verify.setVisibility(View.VISIBLE);
                    addAssets.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception ignored){}
    }

    void proceedGuest(){
        try {
            countDownTimer.cancel();
            progressDialog = ProgressDialog.show(activity, "RESULT", "SAVING GUEST");

            String sMillis = String.valueOf(System.currentTimeMillis());
            String sName = "GUEST_"+(name.getText().toString().isEmpty() ?
                    sMillis : name.getText().toString());

            firestoreModule.writeToLogStore(
                    AppConstant.PARENT,
                    AppConstant.MODE.UMUM.getValue(),
                    String.valueOf(System.currentTimeMillis()),
                    Utils.getDataGuest(
                            sName,
                            "-",
                            encodeImage(file),
                            sMillis,
                            "0"
                    )
            ).addOnSuccessListener(unused -> {
                if (progressDialog != null){
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                bottomsheetResultCallback.dismissDialog();
                dismissAllowingStateLoss();
            })
            .addOnFailureListener(e -> Messages.showAlertMessage(activity, "RESULT", "SOMETHING WENT WRONG\n" +
                    "ON SAVING LOG"))
            .addOnCompleteListener(task -> Messages.showSuccessMessage(activity, "RESULT", "SAVING COMPLETE"));
        } catch (Exception ignored){}
    }

    @OnClick(R.id.addUser)
    public void addNewUser(){
        try {
            currentState = AppConstant.STATE_FETCH.ADDFRUSER;

            AddFrUser addFrUser = new AddFrUser();
            addFrUser.setNama(name.getText().toString());
            addFrUser.setNik(nik.getText().toString());

            resultPresenter.addFrUser(addFrUser);
        } catch (Exception ignored){}
    }

    @OnClick(R.id.btnAbsenIn)
    public void absenIn(){
        resultPresenter.absenIn(name.getText().toString(), nik.getText().toString());
    }

    @OnClick(R.id.btnAbsenOut)
    public void absenOut(){
        resultPresenter.absenOut(name.getText().toString(), nik.getText().toString());
    }

    @OnClick(R.id.ok)
    public void dismissBottomSheet(){
        if (isEmployee){
            bottomsheetResultCallback.dismissDialog();
            dismissAllowingStateLoss();
        } else {
            proceedGuest();
        }
    }

    @Override
    public void fetchFailed() {
        progressDialog.dismiss();
    }

    BottomSheetDialog d;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        BottomSheetDialog dialog = (BottomSheetDialog) super.onCreateDialog(savedInstanceState);
        dialog.setOnShowListener(dialog1 -> {
            d = (BottomSheetDialog) dialog1;

            FrameLayout bottomSheet = d.findViewById(com.google.android.material.R.id.design_bottom_sheet);
            if (bottomSheet != null) {
                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        return dialog;
    }

    @Override
    public void onSuccess() {

    }

    @Override
    public void onSuccess(DocumentReference documentReference) {

    }

    @Override
    public void onFailure(Exception e) {

    }

    @Override
    public void onComplete(Task<DocumentReference> task) {

    }

    @Override
    public void onCompleteVoid(Task<Void> task) {

    }


    public interface BottomsheetResultCallback{
        void dismissDialog();
    }
}
