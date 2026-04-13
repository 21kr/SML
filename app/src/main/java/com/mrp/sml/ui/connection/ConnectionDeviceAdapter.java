package com.mrp.sml.ui.connection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.mrp.sml.R;
import com.mrp.sml.domain.repository.DiscoveredDevice;
import java.util.ArrayList;
import java.util.List;

public class ConnectionDeviceAdapter extends RecyclerView.Adapter<ConnectionDeviceAdapter.DeviceViewHolder> {

    public interface DeviceClickListener {
        void onDeviceClicked(DiscoveredDevice device);
    }

    private final List<DiscoveredDevice> devices = new ArrayList<>();
    private final DeviceClickListener clickListener;

    public ConnectionDeviceAdapter(DeviceClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void submitList(List<DiscoveredDevice> newDevices) {
        devices.clear();
        if (newDevices != null) {
            devices.addAll(newDevices);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_discovered_device, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        DiscoveredDevice device = devices.get(position);
        holder.bind(device, clickListener);
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    static class DeviceViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleText;
        private final TextView subtitleText;

        DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.deviceNameText);
            subtitleText = itemView.findViewById(R.id.deviceIdText);
        }

        void bind(DiscoveredDevice device, DeviceClickListener clickListener) {
            titleText.setText(device.getName());
            subtitleText.setText(device.getId());
            itemView.setOnClickListener(view -> clickListener.onDeviceClicked(device));
        }
    }
}
