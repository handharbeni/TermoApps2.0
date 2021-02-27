package dev.mhandharbeni.termoapps20.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dev.mhandharbeni.termoapps20.report_pegawai.DetailFragment;

public class AbsenAdapter extends FragmentStateAdapter {
    Activity activity;
    List<String> listDate;

    public AbsenAdapter(@NonNull @NotNull Fragment fragment, Activity activity, List<String> listDate) {
        super(fragment);
        this.activity = activity;
        this.listDate = listDate;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        String dateMillis = listDate.get(position);
        return DetailFragment.getInstance(activity, dateMillis);
    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }

    public void updateData(List<String> listDate){
        this.listDate.clear();
        this.listDate = listDate;
        notifyDataSetChanged();
    }
}
