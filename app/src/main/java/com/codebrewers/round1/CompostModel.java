package com.codebrewers.round1;

public class CompostModel {
    String amt,sign;

    public CompostModel(String amt, String sign) {
        this.amt = amt;
        this.sign = sign;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
