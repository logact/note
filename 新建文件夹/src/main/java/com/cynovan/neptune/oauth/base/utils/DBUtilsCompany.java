package com.cynovan.neptune.oauth.base.utils;

import com.cynovan.neptune.oauth.addons.intro.jdo.QCompany;
import com.cynovan.neptune.oauth.addons.intro.jdo.QDeviceAnalyzeData;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.apache.commons.collections.CollectionUtils;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

public class DBUtilsCompany {

    public static MongoCollection<Document> getCollection(String collectionName) {
        return DBUtilsNoCompany.getCollection(collectionName);
    }

    public static void createIndex(String collectionName, Document index) {
        createIndex(collectionName, index, false);
    }

    public static void createIndex(String collectionName, Document index, boolean background) {
        DBUtilsNoCompany.createIndex(collectionName, index, background);
    }

    public static MongoDatabase getDatabase() {
        return DBUtilsNoCompany.getDatabase();
    }

    public static long count(String collectionName) {
        return DBUtilsNoCompany.count(collectionName);
    }

    public static long count(String collectionName, Bson filter) {
        filter = processCompanyId(collectionName, filter);
        return DBUtilsNoCompany.count(collectionName, filter);
    }

    public static List<Document> aggregate(String collectionName, List<Document> pipeline) {
        return DBUtilsNoCompany.aggregate(collectionName, pipeline);
    }

    public static Document findByID(String collectionName, String id) {
        return find(collectionName, Filters.eq("_id", new ObjectId(id)));
    }

    public static Document find(String collectionName, Bson filter) {
        return find(collectionName, filter, null);
    }

    public static Document find(String collectionName, Bson filter, Bson projection) {
        return find(collectionName, filter, projection, null);
    }

    public static Document find(String collectionName, Bson filter, Bson projection, Bson sort) {
        return find(collectionName, filter, projection, sort, 0);
    }

    public static Document find(String collectionName, Bson filter, Bson projection, Bson sort, int skip) {
        List<Document> list = list(collectionName, filter, projection, sort, 1, skip);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        }
        return null;
    }

    public static void insertMany(String collectionName, List<Document> list) {
        DBUtilsNoCompany.insertMany(collectionName, list);
    }

    public static void insert(String collectionName, Document document) {
        DBUtilsNoCompany.insert(collectionName, document);
    }

    public static void save(String collectionName, Document document) {
        processCompanyId(collectionName, document);
        DBUtilsNoCompany.save(collectionName, document);
    }

    public static UpdateResult updateMany(String collectionName, Bson filter, Bson update) {
        return DBUtilsNoCompany.updateMany(collectionName, filter, update, false);
    }

    public static UpdateResult updateMany(String collectionName, Bson filter, Bson update, boolean upsert) {
        return DBUtilsNoCompany.updateMany(collectionName, filter, update, false);
    }

    public static UpdateResult updateOne(String collectionName, Bson filter, Bson update) {
        return updateOne(collectionName, filter, update, false);
    }

    public static UpdateResult updateOne(String collectionName, Bson filter, Bson update, boolean upsert) {
        filter = processCompanyId(collectionName, filter);
        return DBUtilsNoCompany.updateOne(collectionName, filter, update, upsert);
    }

    public static DeleteResult deleteOne(String collectionName, Bson filter) {
        filter = processCompanyId(collectionName, filter);
        return DBUtilsNoCompany.deleteOne(collectionName, filter);
    }

    public static DeleteResult deleteMany(String collectionName, Bson filter) {
        filter = processCompanyId(collectionName, filter);
        return DBUtilsNoCompany.deleteMany(collectionName, filter);
    }

    public static List<Document> list(String collectionName) {
        return list(collectionName, null);
    }

    public static List<Document> list(String collectionName, Bson filter) {
        return list(collectionName, filter, null);
    }

    public static List<Document> list(String collectionName, Bson filter, Bson projection) {
        return list(collectionName, filter, projection, null);
    }

    public static List<Document> list(String collectionName, Bson filter, Bson projection, Bson sort) {
        return list(collectionName, filter, projection, sort, 0);
    }

    public static List<Document> list(String collectionName, Bson filter, Bson projection, Bson sort, int limit) {
        return list(collectionName, filter, projection, sort, limit, 0);
    }

    public static List<Document> list(String collectionName, Bson filter, Bson projection, Bson sort, int limit, int skip) {
        filter = processCompanyId(collectionName, filter);
        return DBUtilsNoCompany.list(collectionName, filter, projection, sort, limit, skip);
    }

    private static Bson processCompanyId(String collectionName, Bson bson) {
        if (!StringLib.equalsAny(collectionName, QCompany.collectionName, QDeviceAnalyzeData.collectionName)) {
            bson = EntityLib.appendCompanyID(bson);
        }
        return bson;
    }

    public static Document runCommand(Bson command) {
        return getDatabase().runCommand(command);
    }
}
