package com.cynovan.neptune.oauth.base.utils;

import com.cynovan.neptune.oauth.addons.intro.jdo.QCompany;
import com.cynovan.neptune.oauth.base.config.auth.dto.UserToken;
import com.cynovan.neptune.oauth.base.database.jdo.QUserInfo;
import org.bson.Document;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Created by Aric on 2016/11/25.
 */
public class UserUtils {

    public static UserToken getUserToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof String) {
            return null;
        }

        UserToken userToken = (UserToken) principal;
        return userToken;
    }

    public static Document getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            return null;
        }
        if (principal instanceof String) {
            return null;
        }

        String userID = "";
        if (principal instanceof UserToken) {
            UserToken userToken = (UserToken) authentication.getPrincipal();
            userID = userToken.getId();
        }
        if (StringLib.isEmpty(userID)) {
            return null;
        }

        return DBUtilsNoCompany.findByID(QUserInfo.collectionName, userID);
    }

    public static String getCompanyId() {
        Document company = UserUtils.getUserCompany();
        return DocumentLib.getID(company);
    }

    public static Document getUserCompany() {
        Document userInfo = getUser();
        if (userInfo != null) {
            String companyId = DocumentLib.getString(userInfo, QUserInfo.company_id);
            if (userInfo != null && StringLib.isNotEmpty(companyId)) {
                Document company = DBUtilsCompany.findByID(QCompany.collectionName, companyId);
                return company;
            }
        }
        return new Document();
    }
}


