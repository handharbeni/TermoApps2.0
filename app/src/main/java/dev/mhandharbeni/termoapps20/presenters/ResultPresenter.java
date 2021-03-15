package dev.mhandharbeni.termoapps20.presenters;

import android.app.Activity;

import com.manishkprboilerplate.base.BasePresenter;
import com.manishkprboilerplate.web_services.RxUtil;

import dev.mhandharbeni.termoapps20.databases.FirestoreModule;
import dev.mhandharbeni.termoapps20.interfaces.ResultCallback;
import dev.mhandharbeni.termoapps20.models.AddFrUser;
import dev.mhandharbeni.termoapps20.models.DeleteFrUser;
import dev.mhandharbeni.termoapps20.models.RealTimeTraining;
import dev.mhandharbeni.termoapps20.models.UploadReg;
import dev.mhandharbeni.termoapps20.models.Verify;
import dev.mhandharbeni.termoapps20.models.WhoIsIt;
import dev.mhandharbeni.termoapps20.models.WhoIsItMM;
import dev.mhandharbeni.termoapps20.responses.addfruser.AddFRUserResponse;
import dev.mhandharbeni.termoapps20.responses.realtimetraining.RealTimeTrainingResponse;
import dev.mhandharbeni.termoapps20.responses.verify.VerifyResponse;
import dev.mhandharbeni.termoapps20.responses.whoisit.WhoIsItResponse;
import dev.mhandharbeni.termoapps20.responses.whoisitmm.WhoIsItMMResponse;
import dev.mhandharbeni.termoapps20.utils.Utils;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;
import dev.mhandharbeni.termoapps20.utils_network.TermoSDK;
import rx.Subscriber;
import rx.Subscription;

import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.ABSENIN;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.ABSENOUT;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.SUHUIN;
import static dev.mhandharbeni.termoapps20.utils_network.AppConstant.SUHUOUT;

public class ResultPresenter extends BasePresenter<ResultCallback.ResultResponseCallback> {
    Activity activity;
    private Subscription mSubscription;
    private FirestoreModule firestoreModule;

    public ResultPresenter(Activity activity, Subscription mSubscription, FirestoreModule firestoreModule) {
        this.activity = activity;
        this.mSubscription = mSubscription;
        this.firestoreModule = firestoreModule;
    }

    @Override
    public void attachView(ResultCallback.ResultResponseCallback mvpView) {
        super.attachView(mvpView);
    }

    public void whoIsIt(WhoIsIt whoIsIt){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().whoIsIt(new Subscriber<WhoIsItResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(WhoIsItResponse whoIsItResponse) {
                getMvpView().fetchSuccess(whoIsItResponse);
            }
        }, whoIsIt);
    }

    public void realTimeTraining(RealTimeTraining realTimeTraining){
        getMvpView().fetchLoad("Load Real Time Training");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().realTimeTraining(new Subscriber<RealTimeTrainingResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(RealTimeTrainingResponse realTimeTrainingResponse) {
                getMvpView().fetchSuccess(realTimeTrainingResponse);
            }
        }, realTimeTraining);
    }

    public void uploadReg(UploadReg uploadReg){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().uploadReg(new Subscriber<UploadReg>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(UploadReg uploadReg) {
                getMvpView().fetchSuccess(uploadReg);
            }
        }, uploadReg);
    }

    public void verify(Verify verify){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().verify(new Subscriber<VerifyResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(VerifyResponse verifyResponse) {
                getMvpView().fetchSuccess(verifyResponse);
            }
        }, verify);
    }

    public void addFrUser(AddFrUser addFrUser){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().addFrUser(new Subscriber<AddFRUserResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(AddFRUserResponse addFRUserResponse) {
                getMvpView().fetchSuccess(addFRUserResponse);
            }
        }, addFrUser);
    }

    public void delFrUser(DeleteFrUser deleteFrUser){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().delFrUser(new Subscriber<DeleteFrUser>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(DeleteFrUser deleteFrUser) {
                getMvpView().fetchSuccess(deleteFrUser);
            }
        }, deleteFrUser);
    }

    public void whoIsItMM(WhoIsItMM whoIsItMM){
        getMvpView().fetchLoad("Load Who Is It");

        RxUtil.unSubscribe(mSubscription);
        mSubscription = null;

        mSubscription = TermoSDK.getInstance().whoIsItMM(new Subscriber<WhoIsItMMResponse>() {
            @Override
            public void onCompleted() {
                getMvpView().fetchFailed();
            }

            @Override
            public void onError(Throwable e) {
                getMvpView().fetchFailed();
            }

            @Override
            public void onNext(WhoIsItMMResponse whoIsItMMResponse) {
                getMvpView().fetchSuccess(whoIsItMMResponse);
            }
        }, whoIsItMM);
    }

    public void absenIn(String sNama, String sNik, String suhu){
        getMvpView().fetchLoad("SAVE ABSEN IN");
        firestoreModule.getDataFromStore(
                AppConstant.PARENT,
                AppConstant.MODE.PEGAWAI.getValue(),
                sNik)
                .addOnSuccessListener(documentSnapshot -> {
                    String sAbsenIn, sAbsenOut, sSuhuIn, sSuhuOut;
                    if (documentSnapshot.exists()){
                        if (documentSnapshot.get(ABSENIN).toString().equalsIgnoreCase("0")){
                            sAbsenIn = String.valueOf(System.currentTimeMillis());
                        } else {
                            sAbsenIn = documentSnapshot.get(ABSENIN).toString();
                        }
                        sAbsenOut = documentSnapshot.get(ABSENOUT).toString();
                        sSuhuIn = documentSnapshot.get(SUHUIN).toString();
                        sSuhuOut = documentSnapshot.get(SUHUOUT).toString();
                    } else {
                        sAbsenIn = String.valueOf(System.currentTimeMillis());
                        sAbsenOut = "0";
                        sSuhuIn = suhu;
                        sSuhuOut = "0";
                    }
                    firestoreModule.writeLogToStore(
                            AppConstant.PARENT,
                            AppConstant.MODE.PEGAWAI.getValue(),
                            sNik,
                            Utils.getData(
                                    sNik,
                                    sNama,
                                    sAbsenIn,
                                    sAbsenOut,
                                    sSuhuIn,
                                    sSuhuOut
                            )
                    );

                    getMvpView().fetchSuccess(null);
                });
    }
    public void absenOut(String sNama, String sNik, String suhu){
        getMvpView().fetchLoad("SAVE ABSEN OUT");
        firestoreModule.getDataFromStore(
                AppConstant.PARENT,
                AppConstant.MODE.PEGAWAI.getValue(),
                sNik)
                .addOnSuccessListener(documentSnapshot -> {
                    String sAbsenIn, sAbsenOut, sSuhuIn, sSuhuOut;
                    if (documentSnapshot.exists()){
                        if (documentSnapshot.get(ABSENOUT).toString().equalsIgnoreCase("0")){
                            sAbsenOut = String.valueOf(System.currentTimeMillis());
                        } else {
                            sAbsenOut = documentSnapshot.get(ABSENOUT).toString();
                        }
                        sAbsenIn = documentSnapshot.get(ABSENIN).toString();
                        sSuhuIn = documentSnapshot.get(SUHUIN).toString();
                        sSuhuOut = documentSnapshot.get(SUHUOUT).toString();
                    } else {
                        sAbsenIn = "0";
                        sAbsenOut = String.valueOf(System.currentTimeMillis());
                        sSuhuIn = "0";
                        sSuhuOut = suhu;
                    }
                    firestoreModule.writeLogToStore(
                            AppConstant.PARENT,
                            AppConstant.MODE.PEGAWAI.getValue(),
                            sNik,
                            Utils.getData(
                                    sNik,
                                    sNama,
                                    sAbsenIn,
                                    sAbsenOut,
                                    sSuhuIn,
                                    sSuhuOut
                            )
                    );

                    getMvpView().fetchSuccess(null);
                });
    }
}
