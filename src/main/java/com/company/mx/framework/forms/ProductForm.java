package com.company.mx.framework.forms;

import com.company.mx.framework.utils.CustomException;
import com.company.mx.framework.vo.ProductVO;
import org.json.JSONObject;

public class ProductForm {

    public ProductVO validateProduct(JSONObject payload, String invoker) throws CustomException {
        if (!payload.has("data") || !(payload instanceof JSONObject))
            throw new CustomException("001", "request_has_not_data_object");

        JSONObject data = payload.getJSONObject("data");

        if (! data.has("sku") || !(data.getString("sku") instanceof String))
            throw new CustomException("002", "sku_format_error");

        if (! data.has("name") || !(data.getString("name") instanceof String))
            throw new CustomException("002", "name_format_error");

        if (! data.has("category") || !(data.getString("category") instanceof String))
            throw new CustomException("002", "category_format_error");

        if (!data.has("brand") || !((data.getString("brand")) instanceof String))
            throw new CustomException("002", "brand_format_error");

        if (!data.has("price") || !((data.get("price")) instanceof Double))
            throw new CustomException("002", "price_format_error");

        if (!data.has("status") || !((data.getString("status")) instanceof String))
            throw new CustomException("002", "status_format_error");

        if (invoker.trim().equals(""))
            throw new CustomException("002", "invoker_format_error");

        ProductVO p = new ProductVO();
        p.setSku(data.getString("sku"));
        p.setName(data.getString("name"));
        p.setCategory(data.getString("category"));
        p.setBrand(data.getString("brand"));
        p.setPrice(data.getDouble("price"));
        p.setStatus(data.getString("status"));
        p.setInvoker(invoker);

        return p;
    }

    public ProductVO validateQueryProduct(JSONObject payload) throws CustomException {
        if (!payload.has("data") || !(payload instanceof JSONObject))
            throw new CustomException("001", "request_has_not_data_object");

        JSONObject data = payload.getJSONObject("data");

        if (! data.has("name") || !(data.getString("name") instanceof String))
            throw new CustomException("002", "name_format_error");

        ProductVO p = new ProductVO();
        p.setName(data.getString("name"));

        return p;
    }
}
