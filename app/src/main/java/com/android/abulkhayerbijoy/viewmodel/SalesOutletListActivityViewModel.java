package com.android.abulkhayerbijoy.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;


import com.android.abulkhayerbijoy.model.OutletDetail;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import java.util.List;

import io.reactivex.Observable;

public class SalesOutletListActivityViewModel extends AndroidViewModel {

    private NetworkCallRepository nRepository;
    private DatabaseCallRepository dbRepository;

    public SalesOutletListActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dbRepository = new DatabaseCallRepository(application);
    }

    public Observable<List<OutletDetail>> getDeliveryManOrders(int status) {
        return dbRepository.getDeliveryManOrders(status);
    }

//    public Observable<List<OutletDetail>> getOutletByStatus(int status) {
//        return dbRepository.getOutletByStatus(status);
//    }

    public Observable<List<OutletDetail>> getAllOutlets() {
        return dbRepository.getAllOutlets();
    }

    public int getOutletCountByStatus(int status) {
        return dbRepository.getOutletCountByStatus(status);
    }
}