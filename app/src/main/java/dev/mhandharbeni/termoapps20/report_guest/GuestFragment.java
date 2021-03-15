package dev.mhandharbeni.termoapps20.report_guest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.mhandharbeni.termoapps20.R;
import dev.mhandharbeni.termoapps20.adapters.AbsenAdapter;
import dev.mhandharbeni.termoapps20.adapters.GuestLogAdapter;
import dev.mhandharbeni.termoapps20.databases.FirestoreModule;
import dev.mhandharbeni.termoapps20.utils.Messages;
import dev.mhandharbeni.termoapps20.utils.Utils;
import dev.mhandharbeni.termoapps20.utils_network.AppConstant;

@SuppressLint("NonConstantResourceId")
public class GuestFragment extends Fragment implements FirestoreModule.FirestoreModuleCallback {
    Activity activity;

    @BindView(R.id.viewPagerAbsen)
    ViewPager2 viewPagerAbsen;

    @BindView(R.id.tabAbsen)
    TabLayout tabAbsen;

    GuestLogAdapter guestLogAdapter;
    List<String> listDateMillis = new ArrayList<>();

    ProgressDialog mProgressDialog;

    FirestoreModule firestoreModule;

    public static GuestFragment getInstance(Activity activity){
        return new GuestFragment(activity);
    }

    public GuestFragment(Activity activity) {
        this.activity = activity;
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.absen_fragment, container, false);
        ButterKnife.bind(this, view);

        mProgressDialog = ProgressDialog.show(activity, "GUEST REPORT", "LOAD REPORT THIS WEEK");

        firestoreModule = FirestoreModule.getInstance(this);
        fetchData();
        return view;
    }


    void init(){
        guestLogAdapter = new GuestLogAdapter(this, activity, listDateMillis);
        viewPagerAbsen.setAdapter(guestLogAdapter);
        viewPagerAbsen.setCurrentItem(listDateMillis.size()-1, true);

        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabAbsen, viewPagerAbsen,
                (tab, position) -> {
                    String sDate = Utils.getDate(Long.valueOf(listDateMillis.get(position)));
                    tab.setText(sDate);
                }
        );
        tabLayoutMediator.attach();
    }

    void fetchData(){
        firestoreModule.getListParent(AppConstant.PARENT)
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot queryDocumentSnapshot : queryDocumentSnapshots) {
                        listDateMillis.add(queryDocumentSnapshot.getId());
                    }

//                    absenAdapter.updateData(listDateMillis);

                    init();
                    if (mProgressDialog != null){
                        mProgressDialog.dismiss();
                        mProgressDialog = null;
                    }
                })
                .addOnFailureListener(e -> {
                    Messages.showAlertMessage(activity, "LIST REPORT", "FAILED TO FETCH DATA");
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
