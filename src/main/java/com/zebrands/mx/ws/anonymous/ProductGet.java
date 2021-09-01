package com.zebrands.mx.ws.anonymous;

import com.zebrands.mx.framework.adapter.IAdapter;
import com.zebrands.mx.framework.adapter.Manager;
import com.zebrands.mx.framework.utils.CustomException;
import com.zebrands.mx.framework.utils.PropertiesLoader;
import com.zebrands.mx.framework.domain.ProductVO;
import com.zebrands.mx.framework.validators.ProductForm;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductGet extends HttpServlet {

    private static final Logger LOG = Logger.getLogger(com.zebrands.mx.ws.admin.ProductUpsert.class.getName());
    private final String provider = PropertiesLoader.getProperty("db.provider");

    @Override
    public void init() throws ServletException{
        super.init();
    }

    public byte[] readHTTPChannel(HttpServletRequest request) throws ServletException, IOException {
        InputStream stream = request.getInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        byte[] buffer = new byte[2048];
        int bytesRead;

        while (-1 != (bytesRead = stream.read(buffer, 0, buffer.length))){
            baos.write(buffer, 0, bytesRead);
        }

        return baos.toByteArray();
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res){
        javax.servlet.http.HttpServletRequest request = req;
        javax.servlet.http.HttpServletResponse response = res;

        JSONObject responseDataPayload = new JSONObject();

        try {
            JSONObject payload = new JSONObject(new String(readHTTPChannel(request), "utf-8"));
            LOG.log(Level.INFO, payload.toString());

            ProductVO p = new ProductForm().validateQueryProduct(payload);

            IAdapter adapter = (IAdapter) Manager.Instance().create(provider);
            JSONArray array = adapter.queryProduct(p);

            adapter.increment(p.getName());

            responseDataPayload.put("products", array);
            response.setStatus(200);
        }
        catch(CustomException ce){
            LOG.log(Level.INFO, ce.getMessage());
            responseDataPayload.put("status", "error");
            responseDataPayload.put("code", ce.getCode());
            responseDataPayload.put("message", ce.getMessage());
            response.setStatus(400);
        }
        catch(Exception e){
            LOG.log(Level.INFO, e.getMessage());
            responseDataPayload.put("status", "exception");
            responseDataPayload.put("code", "000");
            responseDataPayload.put("message", "unhandled_exception");
            response.setStatus(500);
        }
        finally{
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");

            try {
                PrintWriter out = response.getWriter();
                response.setContentType("application/json; charset=utf-8");
                out.print(responseDataPayload);
                response.flushBuffer();
            }
            catch(Exception e){}
            return;
        }
    }

}

