package com.android.abulkhayerbijoy.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class MainActivityViewModel extends AndroidViewModel {

    private NetworkCallRepository nRepository;
    private DatabaseCallRepository dbRepository;

    public MainActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dbRepository = new DatabaseCallRepository(application);
    }

    public Completable clearAllTables() {
        return dbRepository.clearAllTables();
    }

    public Observable<List<OutletDetail>> getAllOutlets() {
        return dbRepository.getAllOutlets();
    }

    public Observable<Double> getSalesInformation() {
        return dbRepository.getSalesInformation();
    }

    public Observable<SRBasic> getSRInfo() {
        return dbRepository.getSRInfo();
    }

    public int getAllOutletsCount() {
        return dbRepository.getAllOutletsCount();
    }
}