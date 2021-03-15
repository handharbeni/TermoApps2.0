package dev.mhandharbeni.termoapps20.report_guest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.mhandharbeni.termoapps20.R;
import dev.mhandharbeni.termoapps20.adapters.GuestAdapter;
import dev.mhandharbeni.termoapps20.databases.FirestoreModule;
import dev.mhandharbeni.termoapps20.models.Guest;
import dev.mhandharbeni.termoapps20.utils.Messages;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;

public class GuestDetailFragment extends Fragment implements FirestoreModule.FirestoreModuleCallback {
    @BindView(R.id.rvDetail)
    RecyclerView rvDetail;

    Activity activity;
    String dateMillis;

    View view;

    private FirestoreModule firestoreModule;

    private GuestAdapter guestAdapter;
    private List<Guest> listGuest = new ArrayList<>();

    ProgressDialog mProgressDialog;

    public static GuestDetailFragment getInstance(Activity activity, String dateMillis){
        return new GuestDetailFragment(activity, dateMillis);
    }

    public GuestDetailFragment(Activity activity, String dateMillis) {
        this.activity = activity;
        this.dateMillis = dateMillis;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment, container, false);


        ButterKnife.bind(this, view);
        firestoreModule = FirestoreModule.getInstance(this);

        fetchData();

        return view;
    }


    void init(){
        try {
            LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);

            guestAdapter = new GuestAdapter(getActivity().getApplicationContext(), listGuest);

            rvDetail.setLayoutManager(llm);
            rvDetail.setAdapter(guestAdapter);
        } catch (Exception ignored){}
    }

    void fetchData(){
        try {
            mProgressDialog = ProgressDialog.show(activity, "DETAIL REPORT", "FETCHING DATA");
            firestoreModule.getListDataByMode(
                    AppConstant.PARENT,
                    dateMillis,
                    AppConstant.MODE.UMUM.getValue())
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        listGuest.clear();
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Guest guest = new Guest();
                            guest.setNama(document.get(AppConstant.UMUM_NAMA).toString());
                            guest.setDate(document.get(AppConstant.UMUM_DATE).toString());
                            guest.setImage(document.get(AppConstant.UMUM_IMAGE).toString());
                            guest.setSuhu(document.get(AppConstant.UMUM_SUHU).toString());
                            guest.setTujuan(document.get(AppConstant.UMUM_TUJUAN).toString());

                            listGuest.add(guest);
                        }

                        init();

                        if (mProgressDialog != null){
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                    })
                    .addOnFailureListener(e -> {
                        Messages.showAlertMessage(
                                activity,
                                "DETAIL REPORT",
                                "FAILED TO FETCH DATA");
                        if (mProgressDialog != null){
                            mProgressDialog.dismiss();
                            mProgressDialog = null;
                        }
                    });
        } catch (Exception ignored){}
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
}
