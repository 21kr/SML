package com.mrp.sml.ui.viewmodel;

import androidx.lifecycle.ViewModel;
import com.mrp.sml.core.models.ConnectionState;
import com.mrp.sml.core.models.Device;
import com.mrp.sml.data.remote.discovery.DeviceDiscoveryManager;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2 = {"Lcom/mrp/sml/ui/viewmodel/PairingRole;", "", "(Ljava/lang/String;I)V", "SENDER", "RECEIVER", "app_debug"})
public enum PairingRole {
    /*public static final*/ SENDER /* = new SENDER() */,
    /*public static final*/ RECEIVER /* = new RECEIVER() */;
    
    PairingRole() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.mrp.sml.ui.viewmodel.PairingRole> getEntries() {
        return null;
    }
}