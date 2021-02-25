package dev.mhandharbeni.termoapps20.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.mhandharbeni.termoapps20.R;
import dev.mhandharbeni.termoapps20.models.Absen;
import dev.mhandharbeni.termoapps20.utils.Utils;

public class PegawaiAdapter extends RecyclerView.Adapter<PegawaiAdapter.ViewHolder>{
    private Context context;
    private List<Absen> listAbsen = new ArrayList<>();

    public PegawaiAdapter(Context context, List<Absen> listAbsen) {
        this.context = context;
        this.listAbsen = listAbsen;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.item_report_absen, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Absen absen = listAbsen.get(position);
        holder.bindData(absen);
    }

    @Override
    public int getItemCount() {
        return listAbsen.size();
    }

    public void updateData(List<Absen> listAbsen){
        this.listAbsen.clear();
        this.listAbsen = listAbsen;
        notifyDataSetChanged();
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.itemNama)
        TextView itemNama;

        @BindView(R.id.itemNik)
        TextView itemNik;

        @BindView(R.id.itemAbsenIn)
        TextView itemAbsenIn;

        @BindView(R.id.itemAbsenOut)
        TextView itemAbsenOut;

        @BindView(R.id.itemSuhuIn)
        TextView itemSuhuIn;

        @BindView(R.id.itemSuhuOut)
        TextView itemSuhuOut;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Absen absen){
            itemNama.setText(absen.getName());
            itemNik.setText(absen.getNik());
            itemAbsenIn.setText(String.format("Absen In: %s", Utils.getDate(Long.parseLong(absen.getAbsenIn()),"H:m:s")));
            itemAbsenOut.setText(String.format("Absen Out: %s", Utils.getDate(Long.parseLong(absen.getAbsenOut()),"H:m:s")));
            itemSuhuIn.setText(String.format("Suhu In: %s", absen.getSuhuin()));
            itemSuhuOut.setText(String.format("Suhu Out: %s", absen.getSuhuOut()));
        }
    }
}
