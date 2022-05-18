package com.company.mx.framework.dbdriver.impl;

import com.company.mx.framework.notifications.IAdapter;
import com.company.mx.framework.notifications.Manager;
import com.company.mx.framework.vo.AdminVO;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.UpdateOptions;
import com.company.mx.framework.utils.CustomException;
import com.company.mx.framework.utils.Generator;
import com.company.mx.framework.utils.PropertiesLoader;
import com.company.mx.framework.vo.ProductVO;
import org.bson.BsonDocument;
import org.bson.BsonInt32;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

public class Mongo implements com.company.mx.framework.dbdriver.IAdapter {

    final private String dbName = "testdb";
    final private String adminCollection = "admin";
    final private String productCollection = "product";
    final private String productStatsCollection = "product_stats";

    private static final Logger LOG = Logger.getLogger(Mongo.class.getName());
    private final String notificationProvider = PropertiesLoader.getProperty("notifications.provider");
    protected com.mongodb.client.MongoDatabase mongoClient = null;

    public Mongo(){
        mongoClient = initDb(dbName).mongoClient;
    }

    public Mongo initDb(String databaseName){
        LOG.log(Level.INFO, "Mongo: initDb");

        if (mongoClient == null){
            String url = PropertiesLoader.getProperty("mongo.uri");
            LOG.log(Level.INFO, "url=> "+url);

            com.mongodb.client.MongoClient mongoClientx = com.mongodb.client.MongoClients.create(url);
            this.mongoClient = mongoClientx.getDatabase(databaseName);
        }

        return this;
    }

    @Override
    public void upsertAdmin(AdminVO a) throws CustomException {
        LOG.log(Level.INFO, "upsertAdmin");
        try {
            Bson query = eq("account", a.getAccount());

            Document docQuery = mongoClient.getCollection(adminCollection).find(query).first();

            Document d = new Document();
            d.put("name", a.getName());
            d.put("account", a.getAccount());
            d.put("password", a.getPassword());
            d.put("status", a.getStatus());
            d.put("phone", a.getPhone());
            d.put("email", a.getEmail());

            if (docQuery == null) {
                d.put("uuid", a.getUuid());

                mongoClient.getCollection(adminCollection).insertOne(d);
            }
            else {
                d.put("uuid", docQuery.getString("uuid"));
                LOG.log(Level.INFO, "docQuery=> "+docQuery.toString());
                mongoClient.getCollection(adminCollection).replaceOne(query, d);
            }
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-upsertAdmin", e.getMessage()+"");
        }
    }

    @Override
    public void upsertProduct(ProductVO p) throws CustomException {
        LOG.log(Level.INFO, "upsertProduct");
        try {
            Bson query = eq("sku", p.getSku());

            Document docQuery = mongoClient.getCollection(productCollection).find(query).first();

            Document d = new Document();
            d.put("sku", p.getSku());
            d.put("category", p.getCategory());
            d.put("name", p.getName());
            d.put("price", p.getPrice());
            d.put("brand", p.getBrand());
            d.put("status", p.getStatus());

            if (docQuery == null) {
                mongoClient.getCollection(productCollection).insertOne(d);
            }
            else {
                LOG.log(Level.INFO, "docQuery=> "+docQuery.toString());
                mongoClient.getCollection(productCollection).replaceOne(query, d);

                ArrayList<AdminVO> list = queryAdmin(p.getInvoker());

                for (AdminVO admin:
                     list) {
                    IAdapter notificationAdapter = (IAdapter) Manager.Instance().create(notificationProvider);
                    notificationAdapter.sendSMS(admin.getPhone(),"Hola "+admin.getName()+", sku["+p.getSku()+"] fue modificado");
                }

            }
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-upsertProduct", e.getMessage()+"");
        }
    }

    @Override
    public JSONArray queryProduct(ProductVO p) throws CustomException {
        JSONArray array = new JSONArray();

        LOG.log(Level.INFO, "queryProduct");
        try {
            Bson query = and(eq("name", p.getName()), eq("status", "enabled"));

            LOG.log(Level.INFO, "query=> "+query.toString());

            MongoCursor cursor = mongoClient.getCollection(productCollection).find(query).cursor();

            while(cursor.hasNext()){
                Document d = (Document)cursor.next();
                d.remove("_id");
                LOG.log(Level.INFO, d.toString());

                array.put(d);
            }

            return array;
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-queryProduct", e.getMessage()+"");
        }
    }

    @Override
    public boolean queryAdmin(AdminVO a) throws CustomException {
        LOG.log(Level.INFO, "queryAdmin");
        try {
            Bson query = and(eq("account", a.getAccount()), eq("password", a.getPassword()));

            LOG.log(Level.INFO, "query=> "+query.toString());

            Document d = mongoClient.getCollection(adminCollection).find(query).first();

            return d != null;
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-queryAdmin", e.getMessage()+"");
        }
    }

    @Override
    public ArrayList<AdminVO> queryAdmin(String uuid) throws CustomException {
        LOG.log(Level.INFO, "queryAdmin");
        ArrayList<AdminVO> list = new ArrayList<>();
        try {
            Bson query = and(ne("uuid", uuid), eq("status", "enabled"));

            LOG.log(Level.INFO, "query=> "+query.toString());

            MongoCursor cursor  = mongoClient.getCollection(adminCollection).find(query).cursor();

            while(cursor.hasNext()){
                Document d = (Document)cursor.next();
                d.remove("_id");
                LOG.log(Level.INFO, d.toString());

                AdminVO admin = new AdminVO();
                admin.setUuid(d.getString("uuid"));
                admin.setAccount(d.getString("account"));
                admin.setPhone(d.getString("phone"));
                admin.setEmail(d.getString("email"));
                admin.setStatus(d.getString("status"));
                admin.setName(d.getString("name"));

                list.add(admin);
            }

            return list;
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-queryAdmin", e.getMessage()+"");
        }
    }

    @Override
    public void increment(String name) throws CustomException{
        LOG.log(Level.INFO, "increment");
        try {
            String uuid = Generator.Id();
            SimpleDateFormat sdf = new SimpleDateFormat();
            sdf.applyPattern("yyyyMMdd");
            Date day = sdf.parse(sdf.format(new Date()));

            LOG.log(Level.INFO, "day=> "+day.toString());

            BsonDocument bsonDocumentNSamples = new BsonDocument();
            bsonDocumentNSamples.append("$lt", new BsonInt32(Integer.parseInt(PropertiesLoader.getProperty("docs.limit"))));

            LOG.log(Level.INFO, "bson=> "+bsonDocumentNSamples.toString());

            Bson query = and(eq("name", name), eq("timestamp", day), eq("nrows", bsonDocumentNSamples));

            LOG.log(Level.INFO, "query=> "+query);

            Map<String, Object> map = new TreeMap<>();
            map.put("key", "value");

            mongoClient.getCollection(productStatsCollection).updateOne(query,
                    combine(
                            push("rows", map),
                            min("first", day),
                            max("last", day),
                            inc("nrows", 1),
                            set("uuid", uuid),
                            set("timestamp", day)
                    ),
                    new UpdateOptions().upsert(true));
        }
        catch(Exception e){
            LOG.log(Level.INFO, "exception=> "+e.getMessage());
            throw new CustomException("mongo-increment", e.getMessage()+"");
        }
    }
}
