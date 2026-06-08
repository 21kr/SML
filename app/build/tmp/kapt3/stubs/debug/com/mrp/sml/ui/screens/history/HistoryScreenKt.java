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

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000H\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\u001a$\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a:\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\b2\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u0003\u001at\u0010\u000e\u001a\u00020\u00012\b\b\u0002\u0010\u000f\u001a\u00020\u00102\u0014\b\u0002\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0014\b\u0002\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00052\u0014\b\u0002\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00052\u000e\b\u0002\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0007\u001a>\u0010\u0015\u001a\u00020\u00012\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u00172\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001c\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00190\u00172\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\b0\u0017H\u0002\u00a8\u0006\u001a"}, d2 = {"FilterRow", "", "currentFilter", "Lcom/mrp/sml/ui/screens/history/HistoryFilter;", "onFilterChange", "Lkotlin/Function1;", "HistoryItem", "transfer", "Lcom/mrp/sml/domain/model/TransferModel;", "onRetry", "Lkotlin/Function0;", "onOpenFile", "modifier", "Landroidx/compose/ui/Modifier;", "HistoryScreen", "uiState", "Lcom/mrp/sml/ui/viewmodel/HistoryUiState;", "onClearHistory", "onRetryTransfer", "", "onBack", "TransferList", "transfers", "", "groupByDate", "Lcom/mrp/sml/ui/screens/history/DateGroup;", "app_debug"})
public final class HistoryScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void HistoryScreen(@org.jetbrains.annotations.NotNull()
    com.mrp.sml.ui.viewmodel.HistoryUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super com.mrp.sml.ui.screens.history.HistoryFilter, kotlin.Unit> onFilterChange, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onClearHistory, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRetryTransfer, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenFile, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FilterRow(com.mrp.sml.ui.screens.history.HistoryFilter currentFilter, kotlin.jvm.functions.Function1<? super com.mrp.sml.ui.screens.history.HistoryFilter, kotlin.Unit> onFilterChange) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void TransferList(java.util.List<com.mrp.sml.domain.model.TransferModel> transfers, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRetryTransfer, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onOpenFile) {
    }
    
    private static final java.util.List<com.mrp.sml.ui.screens.history.DateGroup> groupByDate(java.util.List<com.mrp.sml.domain.model.TransferModel> transfers) {
        return null;
    }
    
    @androidx.compose.runtime.Composable()
    private static final void HistoryItem(com.mrp.sml.domain.model.TransferModel transfer, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, kotlin.jvm.functions.Function0<kotlin.Unit> onOpenFile, androidx.compose.ui.Modifier modifier) {
    }
}