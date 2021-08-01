package com.android.abulkhayerbijoy.viewmodel;

import android.app.Application;
import android.os.Handler;

import androidx.lifecycle.AndroidViewModel;


import com.android.abulkhayerbijoy.model.SRBasic;
import com.android.abulkhayerbijoy.network.NetworkService;
import com.android.abulkhayerbijoy.repository.DatabaseCallRepository;
import com.android.abulkhayerbijoy.repository.NetworkCallRepository;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import retrofit2.Response;

public class LoginActivityViewModel extends AndroidViewModel {

    private NetworkCallRepository nRepository;
    private DatabaseCallRepository dbCallRepository;

    public LoginActivityViewModel(Application application) {
        super(application);
        nRepository = new NetworkCallRepository(application);
        dbCallRepository = new DatabaseCallRepository(application);
    }

    public Observable<Response<String>> UserLogin(String userID, String password) {
        return nRepository.UserLogin(userID, password);
    }

    public Observable<Response<String>> GetDSRBasicInfos(String userID, String password, int srID, int systemID) {
        return nRepository.GetDSRBasicInfos(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetSKUs(String userID, String password, int systemID, int deliveryGroupID) {
        return nRepository.GetSKUs(userID, password, systemID, deliveryGroupID);
    }

    public Observable<Response<String>> GetSections(String userID, String password, int srID, int systemID) {
        return nRepository.GetSections(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetData(String userID, String password, int srID, int systemID) {
        return nRepository.GetSections(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetOutletInfos(String userID, String password, int srID, int systemID) {
        return nRepository.GetOutletInfos(userID, password, srID, systemID);
    }

    public Maybe<SRBasic> getSRBasic() {
        return dbCallRepository.getSRBasic();
    }

    //region Promotion

    public Observable<Response<String>> GetSalesPromotions(String userID, String password, int systemID, String operationDate) {
        return nRepository.GetSalesPromotions(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPSKUChannel(String userID, String password, int systemID, String operationDate) {
        return nRepository.GetSPSKUChannel(userID, password, systemID, operationDate);
    }


    public Observable<Response<String>> GetSPSlabs(String userID, String password, int systemID, String operationDate) {
        return nRepository.GetSPSlabs(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPBonuses(String userID, String password, int systemID, String operationDate) {
        return nRepository.GetSPBonuses(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetPromotionalInfo(String userID, String password, int srID, int systemID) {
        return nRepository.GetPromotionalInfo(userID, password, srID, systemID);
    }


    /*public void GetGiftSKU(int sectionID, int salesPointID, String uniqueCode, Handler handler) {
        nRepository.GetGiftSKU(sectionID, salesPointID, uniqueCode, handler);
    }*/

    /*public void GetRestrictedTPData(int sectionID, int srID, int systemID, String uniqueCode, Handler handler) {
        nRepository.GetRestrictedTPData(sectionID, srID, systemID, uniqueCode, handler);
    }*/

    //endregion

}