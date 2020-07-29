package com.project.mobile;

import java.util.ArrayList;
import java.util.List;

public class QueryUtils {
    private QueryUtils() {}

    private static class Singleton {
        private static final QueryUtils instance = new QueryUtils();
    }

    public static QueryUtils getInstance() {
        return Singleton.instance;
    }

}
