package com.zebrands.mx.framework.adapter;

import com.zebrands.mx.framework.utils.CustomException;
import com.zebrands.mx.framework.domain.AdminVO;
import com.zebrands.mx.framework.domain.ProductVO;
import org.json.JSONArray;

import java.util.ArrayList;

public interface IAdapter {
    public void upsertProduct(ProductVO p) throws CustomException;
    public JSONArray queryProduct(ProductVO p) throws CustomException;
    public void upsertAdmin(AdminVO a) throws CustomException;
    public boolean queryAdmin(AdminVO a) throws CustomException;
    public ArrayList<AdminVO> queryAdmin(String uuid) throws CustomException;
    public void increment(String sku) throws CustomException;
}
