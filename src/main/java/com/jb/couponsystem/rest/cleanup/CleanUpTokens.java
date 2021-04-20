package com.jb.couponsystem.rest.cleanup;

import com.jb.couponsystem.rest.ClientSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.Map;

@Service
public class CleanUpTokens {
    private static final long HALF_AN_HOUR = 1_800_000;

    private final Map<String, ClientSession> tokensMap;

    @Autowired
    public CleanUpTokens(@Qualifier("tokens") Map<String, ClientSession> tokensMap) {
        this.tokensMap = tokensMap;
    }

    @Scheduled(fixedRateString = "${minute}")
    private synchronized void deleteExpiredToken() {
        Iterator<ClientSession> iterator = tokensMap.values().iterator();

        while (iterator.hasNext()) {
            ClientSession session = iterator.next();

            long lastAccessedMillis = session.getLastAccessedMillis();
            long currentTimeMillis = System.currentTimeMillis();

            if (currentTimeMillis - lastAccessedMillis > HALF_AN_HOUR) {
                iterator.remove();
            }
        }
    }
}
