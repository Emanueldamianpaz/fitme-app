package ar.edu.davinci.infraestructure.security.session;

import ar.edu.davinci.infraestructure.exception.FitmeException;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

public class UserSessionGuavaCacheUtil {

    private static LoadingCache<String, UserSession> cache;

    static {
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(36000, TimeUnit.SECONDS)
                .build(
                        new CacheLoader<String, UserSession>() {
                            @Override
                            public UserSession load(String id) throws FitmeException {
                                return getUserSessionById(id);
                            }
                        }
                );
    }

    public static LoadingCache<String, UserSession> getLoadingCache() {
        return cache;
    }

    private static UserSession getUserSessionById(String id) {
        return cache.getUnchecked(id);
    }
}
