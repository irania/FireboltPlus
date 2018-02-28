package com.Tirax.plasma.Errors;



/**
 * Created by Lenovo on 7/8/2016.
 */
public class Error124 extends Error {
    @Override
    public void beforeError() {

    }

    @Override
    public void afterError() {

    }

    @Override
    public boolean isError() {
        //TODO active it
        /*
        if(DataProvider.getRegister(DataProvider.TEMPMONO_REG)>30 || DataProvider.getRegister(DataProvider.TEMPBI_REG)>30)
            return true;*/
        return false;

    }
}
