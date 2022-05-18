package com.company.mx.framework.dbdriver;

import com.company.mx.framework.vo.AdminVO;
import com.company.mx.framework.utils.CustomException;
import com.company.mx.framework.vo.ProductVO;
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
