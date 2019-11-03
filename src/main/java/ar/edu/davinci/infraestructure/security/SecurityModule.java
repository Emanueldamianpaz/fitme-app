package ar.edu.davinci.infraestructure.security;

import ar.edu.davinci.infraestructure.security.session.UserSession;
import ar.edu.davinci.infraestructure.security.session.UserSessionFactory;
import ar.edu.davinci.infraestructure.security.session.UserSessionGuavaCacheUtil;
import com.auth0.jwk.JwkException;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.JwkProviderBuilder;
import com.auth0.jwt.interfaces.RSAKeyProvider;
import com.github.racc.tscg.TypesafeConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.common.cache.LoadingCache;
import ar.edu.davinci.exception.FitmeException;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public class SecurityModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserSessionFactory.class);
    }

    @Provides
    LoadingCache<String, UserSession> provideUserLoadingCache() {
        return UserSessionGuavaCacheUtil.getLoadingCache();
    }

    @Provides
    RSAKeyProvider provideRSAKeyProvider(@TypesafeConfig("auth0.jwksUri") String jwksUri) {

        JwkProvider jwkProvider = new JwkProviderBuilder(jwksUri).build();

        return new RSAKeyProvider() {
            @Override
            public RSAPublicKey getPublicKeyById(String kid) {

                try {
                    return (RSAPublicKey) jwkProvider.get(kid).getPublicKey();

                } catch (JwkException e) {
                    throw new FitmeException(e);
                }

            }

            @Override
            public RSAPrivateKey getPrivateKey() {
                return null;
            }

            @Override
            public String getPrivateKeyId() {
                return null;
            }
        };
    }
}
