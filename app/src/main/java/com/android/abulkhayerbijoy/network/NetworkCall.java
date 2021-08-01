package com.android.abulkhayerbijoy.network;

import com.android.abulkhayerbijoy.model.SalesOrderPromotion;
import com.android.abulkhayerbijoy.model.UserInfo;
import com.android.abulkhayerbijoy.model.response.BijoyTransactionResponse;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface NetworkCall {

    //region GET
    @GET("OnlineSalesServiceController/GetHHTUsers")
    Observable<Response<String>> GetLoginData(@Query("userID") String userID, @Query("password") String password, @Query("mobile") String mobile, @Query("Userpassword") String Userpassword);

    @GET("OnlineSalesServiceController/GetCMBasicInfos")
    Observable<Response<String>> GetDSRBasicInfos(@Query("userID") String userID, @Query("password") String password, @Query("srID") int srID, @Query("systemID") int systemID);

    @GET("OnlineSalesServiceController/GetSKUsForCMAsync")
    Observable<Response<String>> GetSKUs(@Query("userID") String userID, @Query("password") String password, @Query("systemID") int systemID, @Query("deliveryGroupID") int deliveryGroupID);

    @GET("OnlineSalesServiceController/GetSection")
    Observable<Response<String>> GetSections(@Query("userID") String userID, @Query("password") String password, @Query("srID") int srID, @Query("systemID") int systemID);

    @GET("OnlineSalesServiceController/GetOutletInfos")
    Observable<Response<String>> GetOutletInfos(@Query("userID") String userID, @Query("password") String password, @Query("srID") int srID, @Query("systemID") int systemID);

    //region Promotion
    @GET("OnlineSalesServiceController/GetSalesPromotions")
    Observable<Response<String>> GetSalesPromotions(@Query("userID") String userID, @Query("password") String password, @Query("systemID") int systemID, @Query("operationDate") String operationDate);

    @GET("OnlineSalesServiceController/GetSPSKUChannel")
    Observable<Response<String>> GetSPSKUChannel(@Query("userID") String userID, @Query("password") String password, @Query("systemID") int systemID, @Query("operationDate") String operationDate);

    @GET("OnlineSalesServiceController/GetSPSlabs")
    Observable<Response<String>> GetSPSlabs(@Query("userID") String userID, @Query("password") String password, @Query("systemID") int systemID, @Query("operationDate") String operationDate);

    @GET("OnlineSalesServiceController/GetSPBonuses")
    Observable<Response<String>> GetSPBonuses(@Query("userID") String userID, @Query("password") String password, @Query("systemID") int systemID, @Query("operationDate") String operationDate);

    @GET("OnlineSalesServiceController/GetPromotionalInfo")
    Observable<Response<String>> GetPromotionalInfo(@Query("userID") String userID,@Query("password")  String password,@Query("srID") int srID,@Query("systemID") int systemID);

    //endregion


    //region POST
    //for Final Upload
    @Headers("Content-Type: application/json")
    @POST("OnlineSalesServiceController/ChallanConfirmByCM")
    Observable<BijoyTransactionResponse> ChallanUploadByCM(@Body JSONObject orderData);

    //for Challan Confirmation
    @Headers("Content-Type: application/json")
    @POST("OnlineSalesServiceController/ChallanUploadByCM")
    Observable<BijoyTransactionResponse> ChallanConfirmByCM(@Body JSONObject orderData);

    //endregion


}
