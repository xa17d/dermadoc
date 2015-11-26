package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.server.security.SecurityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by daniel on 26.11.2015.
 */
@Service
public class TokenService {

    private TokenService() {
        tokens.put("test", new SecurityToken("test", new Patient(), "blub", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_PATIENT"))); // TODO remove
        tokens.put("test2", new SecurityToken("test2", new Physician(), "blub", AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_PHYSICIAN"))); // TODO remove
    }

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private HashMap<String, Authentication> tokens = new HashMap<>();

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        // TODO delete expired tokens
    }

    public String generateNewToken() {
        return UUID.randomUUID().toString();
    }

    public void store(String token, Authentication authentication) {
        tokens.put(token, authentication);
    }

    public boolean contains(String token) {
        return tokens.containsKey(token);
    }

    public Authentication retrieve(String token) {
        return tokens.get(token);
    }
}
