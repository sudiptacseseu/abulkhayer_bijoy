package com.android.abulkhayerbijoy.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.android.abulkhayerbijoy.model.CashDeposit;
import com.android.abulkhayerbijoy.model.Challan;
import com.android.abulkhayerbijoy.model.SKU;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import java.util.List;

import io.reactivex.Observable;

public class DepositActivityViewModel extends AndroidViewModel {

    private DatabaseCallRepository dRepository;
    private NetworkCallRepository nRepository;

    public DepositActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dRepository = new DatabaseCallRepository(application);
    }

    public Observable<SRBasic> getSRInfo() {
        return dRepository.getSRInfo();
    }

    public Observable<List<SKU>> GetReturnSkuItems() {
        return dRepository.getReturnSkuItems();
    }
    public Observable<List<Challan>> GetChallanItems() {
        return dRepository.getChallanStockItems();
    }

    public Observable<CashDeposit> getCashDepositInformation() {
        return dRepository.getCashDepositInformation();
    }


    public Observable<List<Challan>> getTotalChallanValue() {
        return dRepository.getTotalChallanValue();
    }

    public void doUploadBijoyInformation() {
        nRepository.UploadBijoyInformation();
    }
}