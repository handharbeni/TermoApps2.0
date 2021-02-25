package dev.mhandharbeni.termoapps20.utils_network;

import com.manishkprboilerplate.web_services.RxUtil;

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
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TermoSDK {
    public static TermoSDK termoSDK;
    public static synchronized TermoSDK getInstance(){
        if (termoSDK == null) termoSDK = new TermoSDK();
        return termoSDK;
    }


    public Subscription whoIsIt(Subscriber<WhoIsItResponse> subscribe, WhoIsIt whoIsIt){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .whoIsIt(whoIsIt)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }

    public Subscription realTimeTraining(Subscriber<RealTimeTrainingResponse> subscribe, RealTimeTraining realTimeTraining){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .realTimeTraining(realTimeTraining)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }


    public Subscription uploadReg(Subscriber<UploadReg> subscribe, UploadReg uploadReg){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .uploadReg(uploadReg)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }


    public Subscription verify(Subscriber<VerifyResponse> subscribe, Verify verify){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .verify(verify)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }


    public Subscription addFrUser(Subscriber<AddFRUserResponse> subscribe, AddFrUser addFrUser){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .addFrUser(addFrUser)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }


    public Subscription delFrUser(Subscriber<DeleteFrUser> subscribe, DeleteFrUser deleteFrUser){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .delFrUser(deleteFrUser)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }


    public Subscription whoIsItMM(Subscriber<WhoIsItMMResponse> subscribe, WhoIsItMM whoIsItMM){
        Subscription mSubscription = null;
        RxUtil.unSubscribe(mSubscription);

        mSubscription = WebClient.getInstance()
                .getNewClient(AppConstant.HOST_API)
                .create(ApiService.class)
                .whoIsItMM(whoIsItMM)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);

        return mSubscription;
    }

}
