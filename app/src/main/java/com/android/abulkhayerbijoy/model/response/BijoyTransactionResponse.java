package com.android.abulkhayerbijoy.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BijoyTransactionResponse {

    @SerializedName("Message")
    @Expose
    public String message;

    @SerializedName("ResponseCode")
    @Expose
    public int responseCode;

    @SerializedName("Data")
    @Expose
    public String data;
}
