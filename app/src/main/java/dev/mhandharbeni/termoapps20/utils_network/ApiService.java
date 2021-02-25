package dev.mhandharbeni.termoapps20.utils_network;

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
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ApiService {
    @POST("whoisit")
    Observable<WhoIsItResponse> whoIsIt(@Body WhoIsIt whoIsIt);

    @POST("rtm")
    Observable<RealTimeTrainingResponse> realTimeTraining(@Body RealTimeTraining realTimeTraining);

    @POST("uploadreg")
    Observable<UploadReg> uploadReg(@Body UploadReg uploadReg);

    @POST("verify")
    Observable<VerifyResponse> verify(@Body Verify verify);

    @POST("addfruser")
    Observable<AddFRUserResponse> addFrUser(@Body AddFrUser addFrUser);

    @POST("delfruser")
    Observable<DeleteFrUser> delFrUser(@Body DeleteFrUser deleteFrUser);

    @POST("whoisitmm")
    Observable<WhoIsItMMResponse> whoIsItMM(@Body WhoIsItMM whoIsItMM);
}
