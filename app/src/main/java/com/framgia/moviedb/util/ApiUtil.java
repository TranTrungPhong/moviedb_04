package com.framgia.moviedb.util;

import com.framgia.moviedb.service.ApiCore;

import java.util.HashMap;

/**
 * Created by tuannt on 23/01/2017.
 * Project: moviedb_04
 * Package: com.framgia.moviedb.util
 */
public class ApiUtil {
    public static HashMap<String, String> param() {
        HashMap<String, String> params = new HashMap<>();
        params.put(ApiCore.Param.API_KEY, ApiCore.Value.API_KEY);
        return params;
    }
}
