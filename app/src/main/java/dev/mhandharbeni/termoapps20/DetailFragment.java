package dev.mhandharbeni.termoapps20;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import dev.mhandharbeni.termoapps20.adapters.PegawaiAdapter;
import dev.mhandharbeni.termoapps20.databases.FirestoreModule;
import dev.mhandharbeni.termoapps20.models.Absen;
import dev.mhandharbeni.termoapps20.utils.Messages;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;

@SuppressLint("NonConstantResourceId")
public class DetailFragment extends Fragment implements FirestoreModule.FirestoreModuleCallback {
    @BindView(R.id.rvDetail)
    RecyclerView rvDetail;

    Activity activity;
    String dateMillis;

    View view;

    private FirestoreModule firestoreModule;

    private PegawaiAdapter pegawaiAdapter;
    private List<Absen> listAbsen = new ArrayList<>();

    ProgressDialog mProgressDialog;

    public static DetailFragment getInstance(Activity activity, String dateMillis){
        return new DetailFragment(activity, dateMillis);
    }

    public DetailFragment(Activity activity, String dateMillis) {
        this.activity = activity;
        this.dateMillis = dateMillis;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(
            @NonNull @NotNull LayoutInflater inflater,
            @Nullable @org.jetbrains.annotations.Nullable ViewGroup container,
            @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.detail_fragment, container, false);


        ButterKnife.bind(this, view);
        firestoreModule = FirestoreModule.getInstance(this);

        fetchData();

        return view;
    }

    void init(){
        LinearLayoutManager llm = new LinearLayoutManager(getActivity().getApplicationContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        pegawaiAdapter = new PegawaiAdapter(getActivity().getApplicationContext(), listAbsen);

        rvDetail.setLayoutManager(llm);
        rvDetail.setAdapter(pegawaiAdapter);
    }

    void fetchData(){
        mProgressDialog = ProgressDialog.show(activity, "DETAIL REPORT", "FETCHING DATA");
        firestoreModule.getListDataByMode(
                AppConstant.PARENT,
                dateMillis,
                AppConstant.MODE.PEGAWAI.getValue())
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listAbsen.clear();
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Log.d("DetailFragment", "fetchData: "+document.get(AppConstant.NAMA).toString());
                        Absen absen = new Absen();
                        absen.setName(document.get(AppConstant.NAMA).toString());
                        absen.setNik(document.get(AppConstant.NIK).toString());
                        absen.setAbsenIn(document.get(AppConstant.ABSENIN).toString());
                        absen.setAbsenOut(document.get(AppConstant.ABSENOUT).toString());
                        absen.setSuhuin(document.get(AppConstant.SUHUIN).toString());
                        absen.setSuhuOut(document.get(AppConstant.SUHUOUT).toString());

                        listAbsen.add(absen);
                    }

                    Log.d("DetailFragment", "fetchData: "+listAbsen.size());

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
