package at.tuwien.telemedizin.dermadoc.server.services;

import at.tuwien.telemedizin.dermadoc.entities.Patient;
import at.tuwien.telemedizin.dermadoc.entities.Physician;
import at.tuwien.telemedizin.dermadoc.entities.User;
import at.tuwien.telemedizin.dermadoc.server.exceptions.InvalidUserTypeException;
import at.tuwien.telemedizin.dermadoc.server.security.SecurityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by daniel on 26.11.2015.
 */
@Service
public class TokenService {

    private TokenService() {
//        store(generateNewToken(MockData.users.get(0), "test"));
//        store(generateNewToken(MockData.users.get(1), "test2"));
    }

    private static final Logger logger = LoggerFactory.getLogger(TokenService.class);
    public static final int HALF_AN_HOUR_IN_MILLISECONDS = 30 * 60 * 1000;
    private HashMap<String, Authentication> tokens = new HashMap<>();

    @Scheduled(fixedRate = HALF_AN_HOUR_IN_MILLISECONDS)
    public void evictExpiredTokens() {
        logger.info("Evicting expired tokens");
        // TODO delete expired tokens
    }

    public SecurityToken generateNewToken(User user) {
        String tokenId = UUID.randomUUID().toString();
        return generateNewToken(user, tokenId);
    }

    public SecurityToken generateNewToken(User user, String tokenId) {
        List<GrantedAuthority> authorities;

        if (user instanceof Patient) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_PATIENT");
        }
        else if (user instanceof Physician) {
            authorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_PHYSICIAN");
        }
        else {
            throw new InvalidUserTypeException(user.getClass());
        }

        SecurityToken token = new SecurityToken(tokenId, user, null, authorities);
        return token;
    }

    public void store(SecurityToken token) {
        tokens.put(token.getToken(), token);
    }

    public boolean contains(String token) {
        return tokens.containsKey(token);
    }

    public Authentication retrieve(String token) {
        return tokens.get(token);
    }
}
