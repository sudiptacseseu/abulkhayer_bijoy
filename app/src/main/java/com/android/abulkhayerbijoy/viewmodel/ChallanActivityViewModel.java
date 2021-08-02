package com.android.abulkhayerbijoy.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.SKU;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class ChallanActivityViewModel extends AndroidViewModel {

    private DatabaseCallRepository dRepository;
    private NetworkCallRepository nRepository;

    public ChallanActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dRepository = new DatabaseCallRepository(application);
    }

    public Observable<SRBasic> getSRInfo() {
        return dRepository.getSRInfo();
    }

    public void doChallanItemsConfirmation(ArrayList<Challan> challanItems, SRBasic srBasic) {
        nRepository.challanItemsConfirmation(challanItems, srBasic);
    }

    public Observable<List<SKU>> GetSkuItems() {
        return dRepository.getSkuItems();
    }

    public Observable<List<Challan>> getTotalChallanValue() {
        return dRepository.getTotalChallanValue();
    }
}