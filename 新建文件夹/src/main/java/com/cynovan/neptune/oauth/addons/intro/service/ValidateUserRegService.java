package com.cynovan.neptune.oauth.addons.intro.service;

import com.cynovan.neptune.oauth.base.arch.base.controller.BaseService;
import com.cynovan.neptune.oauth.base.utils.RandomUtils;
import com.cynovan.neptune.oauth.base.utils.RedisUtils;
import com.cynovan.neptune.oauth.base.utils.StringLib;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * Created by azure on 23/11/2016.
 */
@Service
public class ValidateUserRegService extends BaseService {

    public boolean checkCode(String account, String code) {
        String cacheKey = "REG_CODE_CHECKER_" + account;
        String oriCode = RedisUtils.get(cacheKey);

        if (StringLib.isEmpty(oriCode)) {
            return false;
        }

        if (StringLib.equals(code, oriCode)) {
            RedisUtils.delete(cacheKey);
            return true;
        }
        return false;
    }

    public static String genernateCode(String cacheKey) {
        String code = RandomUtils.uuid4num();
        RedisUtils.set("REG_CODE_CHECKER_" + cacheKey, code, 10l, TimeUnit.MINUTES);
        return code;
    }
}
