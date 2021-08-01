package com.android.abulkhayerbijoy.model;

public class CashDeposit {

    private double previousDue;
    private double challanValue;
    private double total;
    private double returnProductPrice;
    private double totalDue;
    private double todaysCashCollection;
    private double netDue;

    public double getPreviousDue() {
        return previousDue;
    }

    public void setPreviousDue(double previousDue) {
        this.previousDue = previousDue;
    }

    public double getChallanValue() {
        return challanValue;
    }

    public void setChallanValue(double challanValue) {
        this.challanValue = challanValue;
    }

    public double getTotal() {
        return previousDue + challanValue;
    }

    public double getReturnProductPrice() {
        return returnProductPrice;
    }

    public void setReturnProductPrice(double returnProductPrice) {
        this.returnProductPrice = returnProductPrice;
    }

    public double getTotalDue() {
        return getTotal() - this.returnProductPrice;
    }

    public double getTodaysCashCollection() {
        return todaysCashCollection;
    }

    public void setTodaysCashCollection(double todaysCashCollection) {
        this.todaysCashCollection = todaysCashCollection;
    }

    public double getNetDue() {
        return getTotalDue() - todaysCashCollection;
    }
}
