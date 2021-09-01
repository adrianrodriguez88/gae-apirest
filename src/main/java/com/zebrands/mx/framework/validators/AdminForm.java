package com.zebrands.mx.framework.validators;

import com.zebrands.mx.framework.utils.CustomException;
import com.zebrands.mx.framework.utils.Generator;
import com.zebrands.mx.framework.domain.AdminVO;
import org.json.JSONObject;

public class AdminForm {

    public AdminVO validateAdmin(JSONObject payload) throws CustomException {
        if (!payload.has("data") || !(payload instanceof JSONObject))
            throw new CustomException("001", "request_has_not_data_object");

        JSONObject data = payload.getJSONObject("data");

        if (! data.has("name") || !(data.getString("name") instanceof java.lang.String))
            throw new CustomException("002", "name_format_error");

        if (!data.has("account") || !((data.getString("account")) instanceof java.lang.String))
            throw new CustomException("002", "account_format_error");

        if (!data.has("password") || !((data.getString("password")) instanceof java.lang.String))
            throw new CustomException("002", "password_format_error");

        if (!data.has("phone") || !((data.getString("phone")) instanceof java.lang.String))
            throw new CustomException("002", "phone_format_error");

        if (!data.has("email") || !((data.getString("email")) instanceof java.lang.String))
            throw new CustomException("002", "email_format_error");

        if (!data.has("status") || !((data.getString("status")) instanceof java.lang.String))
            throw new CustomException("002", "status_format_error");

        AdminVO admin = new AdminVO();
        admin.setUuid(Generator.Id());
        admin.setName(data.getString("name"));
        admin.setAccount(data.getString("account"));
        admin.setPassword(data.getString("password"));
        admin.setStatus(data.getString("status"));
        admin.setPhone(data.getString("phone"));
        admin.setEmail(data.getString("email"));

        return admin;
    }

    public AdminVO validateQueryAdmin(JSONObject payload) throws CustomException {
        if (!payload.has("data") || !(payload instanceof JSONObject))
            throw new CustomException("001", "request_has_not_data_object");

        JSONObject data = payload.getJSONObject("data");

        if (! data.has("account") || !(data.getString("account") instanceof java.lang.String))
            throw new CustomException("002", "account_format_error");

        if (! data.has("password") || !(data.getString("password") instanceof java.lang.String))
            throw new CustomException("002", "password_format_error");

        AdminVO admin = new AdminVO();
        admin.setAccount(data.getString("account"));
        admin.setPassword(data.getString("password"));

        return admin;
    }
}
