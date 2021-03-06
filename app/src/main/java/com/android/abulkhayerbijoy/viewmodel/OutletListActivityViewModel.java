package com.android.abulkhayerbijoy.viewmodel;


import android.app.Application;
import androidx.lifecycle.AndroidViewModel;


import com.android.abulkhayerbijoy.model.Outlet;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import java.util.List;

import io.reactivex.Observable;

public class OutletListActivityViewModel extends AndroidViewModel {

    private NetworkCallRepository nRepository;
    private DatabaseCallRepository dbRepository;

    public OutletListActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dbRepository = new DatabaseCallRepository(application);
    }

    public Observable<List<Outlet>> getDeliveryManOrders(int status) {
        return dbRepository.getDeliveryManOrders(status);
    }

//    public Observable<List<OutletDetail>> getOutletByStatus(int status) {
//        return dbRepository.getOutletByStatus(status);
//    }

    public Observable<List<Outlet>> getAllOutlets() {
        return dbRepository.getAllOutlets();
    }

    public int getOutletCountByStatus(int status) {
        return dbRepository.getOutletCountByStatus(status);
    }
}