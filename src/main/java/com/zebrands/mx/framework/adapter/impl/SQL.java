package com.zebrands.mx.framework.adapter.impl;

import com.zebrands.mx.framework.adapter.IAdapter;
import com.zebrands.mx.framework.domain.AdminVO;
import com.zebrands.mx.framework.domain.ProductVO;
import com.zebrands.mx.framework.utils.CustomException;
import org.json.JSONArray;

import java.util.ArrayList;

public class SQL implements IAdapter {
    @Override
    public void upsertProduct(ProductVO p) throws CustomException {

    }

    @Override
    public JSONArray queryProduct(ProductVO p) throws CustomException {
        return null;
    }

    @Override
    public void upsertAdmin(AdminVO a) throws CustomException {

    }

    @Override
    public boolean queryAdmin(AdminVO a) throws CustomException {
        return false;
    }

    @Override
    public ArrayList<AdminVO> queryAdmin(String uuid) throws CustomException {
        return null;
    }

    @Override
    public void increment(String sku) throws CustomException {

    }
}
