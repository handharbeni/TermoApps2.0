package dev.mhandharbeni.termoapps20.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import dev.mhandharbeni.termoapps20.R;
import dev.mhandharbeni.termoapps20.models.Guest;
import dev.mhandharbeni.termoapps20.utils.Utils;

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.ViewHolder> {
    private Context context;
    private List<Guest> listGuest;

    public GuestAdapter(Context context, List<Guest> listGuest) {
        this.context = context;
        this.listGuest = listGuest;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_report_guest, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Guest guest = listGuest.get(position);
        holder.bindData(guest);
    }

    @Override
    public int getItemCount() {
        return listGuest.size();
    }

    @SuppressLint("NonConstantResourceId")
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.guestDate)
        TextView guestDate;

        @BindView(R.id.guestImage)
        ImageView guestImage;

        @BindView(R.id.guestName)
        TextView guestName;

        @BindView(R.id.guestSuhu)
        TextView guestSuhu;

        @BindView(R.id.guestTujuan)
        TextView guestTujuan;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindData(Guest guest){
            guestImage.setImageBitmap(Utils.base64ToBitmap(guest.getImage()));
            guestDate.setText(Utils.getDate(Long.parseLong(guest.getDate())));
            guestName.setText(String.format("Nama: %s", guest.getNama()));
            guestSuhu.setText(String.format("Suhu: %S", guest.getSuhu()));
            guestTujuan.setText(String.format("Tujuan: %s", guest.getTujuan()));
        }
    }
}
