package com.opdar.appversion.base;


import com.opdar.mote.web.interceptor.IEditor;

public class ResultEditor implements IEditor {
    @Override
    public Object editor(Object o) {
        Result result = new Result();
        result.setCode(0);
        result.setData(o);
        return result;
    }
}
