package com.mrp.sml.ui.screens.history;

import androidx.compose.foundation.layout.Arrangement;
import androidx.compose.material.icons.Icons;
import androidx.compose.material3.CardDefaults;
import androidx.compose.material3.ExperimentalMaterial3Api;
import androidx.compose.material3.FilterChipDefaults;
import androidx.compose.runtime.Composable;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.mrp.sml.core.utils.FileUtils;
import com.mrp.sml.domain.model.TransferModel;
import com.mrp.sml.ui.viewmodel.HistoryUiState;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0006\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005j\u0002\b\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/mrp/sml/ui/screens/history/HistoryFilter;", "", "(Ljava/lang/String;I)V", "ALL", "SENT", "RECEIVED", "FAILED", "app_debug"})
public enum HistoryFilter {
    /*public static final*/ ALL /* = new ALL() */,
    /*public static final*/ SENT /* = new SENT() */,
    /*public static final*/ RECEIVED /* = new RECEIVED() */,
    /*public static final*/ FAILED /* = new FAILED() */;
    
    HistoryFilter() {
    }
    
    @org.jetbrains.annotations.NotNull()
    public static kotlin.enums.EnumEntries<com.mrp.sml.ui.screens.history.HistoryFilter> getEntries() {
        return null;
    }
}