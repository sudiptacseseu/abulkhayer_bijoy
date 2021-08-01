package com.android.abulkhayerbijoy.network;

import android.os.Handler;


import com.android.abulkhayerbijoy.model.response.BijoyTransactionResponse;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.Retrofit;


public class NetworkService {

    private NetworkCall networkCall = null;
    private NetworkCall networkCallAuth = null;
    private Handler handler;
    public static int SUCCESS = 1;
    public static int FAILED = 0;

    public NetworkService() {
        Retrofit retrofit = RestClient.getClient();
        networkCall = retrofit.create(NetworkCall.class);
    }

    //region GET METHODS
    public Observable<Response<String>> UserLogin(String contactNo, String password) {
        return networkCall.GetLoginData(contactNo, password, contactNo, password);
    }

    public Observable<Response<String>> GetDSRBasicInfos(String contactNo, String password, int srID, int systemID) {
        return networkCall.GetDSRBasicInfos(contactNo, password, srID, systemID);
    }

    public Observable<Response<String>> GetSKUs(String userID, String password, int systemID, int deliveryGroupID) {
        return networkCall.GetSKUs(userID, password, systemID, deliveryGroupID);
    }

    public Observable<Response<String>> GetSections(String userID, String password, int srID, int systemID) {
        return networkCall.GetSections(userID, password, srID, systemID);
    }

    public Observable<Response<String>> GetOutletInfos(String userID, String password, int srID, int systemID) {
        return networkCall.GetOutletInfos(userID, password, srID, systemID);
    }

    //region Promotion
    public Observable<Response<String>> GetSalesPromotions(String userID, String password, int systemID, String operationDate) {
        return networkCall.GetSalesPromotions(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPSKUChannel(String userID, String password, int systemID, String operationDate) {
        return networkCall.GetSPSKUChannel(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPSlabs(String userID, String password, int systemID, String operationDate) {
        return networkCall.GetSPSlabs(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetSPBonuses(String userID, String password, int systemID, String operationDate) {
        return networkCall.GetSPBonuses(userID, password, systemID, operationDate);
    }

    public Observable<Response<String>> GetPromotionalInfo(String userID, String password, int srID, int systemID) {
        return networkCall.GetPromotionalInfo(userID, password, srID, systemID);
    }
    //endregion


    //endregion

    //region POST METHODS
    public Observable<BijoyTransactionResponse> UploadBijoyInformation(JSONObject uploadData) {
        return networkCall.ChallanUploadByCM(uploadData);
    }

    public Observable<BijoyTransactionResponse> ChallanConfirmation(JSONObject uploadData) {
        return networkCall.ChallanConfirmByCM(uploadData);
    }

    //endregion


}
