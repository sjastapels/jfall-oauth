package com.sstapels.jfall.ccdemo;

class ResponseBody {
    Integer status;
    String error;
    Double balance;
    String currency;

    public ResponseBody(){
        this.status = 500;
        this.error = "Internal Server Error";
    }

    public ResponseBody(Integer status, String error){
        this.status = status;
        this.error = error;
    }

    public ResponseBody(Integer status, String currency, Double balance){
        this.status = status;
        this.currency = currency;
        this.balance = balance;
    }

    /**
     * @return the balance
     */
    public Double getbalance() {
        return balance;
    }

    /**
     * @return the currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @return the error
     */
    public String getError() {
        return error;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }
}