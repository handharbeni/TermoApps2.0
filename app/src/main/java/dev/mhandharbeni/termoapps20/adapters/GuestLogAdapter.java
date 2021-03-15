package dev.mhandharbeni.termoapps20.adapters;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import dev.mhandharbeni.termoapps20.report_guest.GuestDetailFragment;

public class GuestLogAdapter extends FragmentStateAdapter {
    Activity activity;
    List<String> listDate;

    public GuestLogAdapter(@NonNull @NotNull Fragment fragment, Activity activity, List<String> listDate) {
        super(fragment);
        this.activity = activity;
        this.listDate = listDate;
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        String dateMillis = listDate.get(position);
        return GuestDetailFragment.getInstance(activity, dateMillis);
    }

    @Override
    public int getItemCount() {
        return listDate.size();
    }
}
