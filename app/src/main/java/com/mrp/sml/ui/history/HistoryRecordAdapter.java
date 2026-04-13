package com.mrp.sml.ui.history;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mrp.sml.R;
import java.util.ArrayList;
import java.util.List;

public class HistoryRecordAdapter extends RecyclerView.Adapter<HistoryRecordAdapter.HistoryViewHolder> {

    public static class HistoryRow {
        public final String title;
        public final String subtitle;

        public HistoryRow(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }
    }

    private final List<HistoryRow> rows = new ArrayList<>();

    public void submitRows(List<HistoryRow> newRows) {
        rows.clear();
        if (newRows != null) {
            rows.addAll(newRows);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_history_record, parent, false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        HistoryRow row = rows.get(position);
        holder.titleText.setText(row.title);
        holder.subtitleText.setText(row.subtitle);
    }

    @Override
    public int getItemCount() {
        return rows.size();
    }

    static class HistoryViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView subtitleText;

        HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.historyTitleText);
            subtitleText = itemView.findViewById(R.id.historySubtitleText);
        }
    }
}
