package com.example.weather_api.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import redis.clients.jedis.Jedis;

import java.io.IOException;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.port}")
    private int redisPort;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String clientIp = request.getRemoteAddr(); // identify client by IP
        String key = "rate_limit:" + clientIp;

        try (Jedis jedis = new Jedis(redisHost, redisPort)) {

            String requestCount = jedis.get(key);

            if (requestCount == null) {
                // first request, set count to 1 and set expiration for 1 minute
                jedis.setex(key, 60, "1");
            } else {
                int currentCount = Integer.parseInt(requestCount);

                int MAX_REQUESTS_PER_MINUTE = 5;
                if (currentCount < MAX_REQUESTS_PER_MINUTE) {
                    // increment the request count
                    jedis.incr(key);
                } else {
                    // limit exceeded, send 429 Too Many Requests response
                    response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                    response.getWriter().write("Rate limit exceeded. Try again later.");
                    return;
                }
            }
        }

        filterChain.doFilter(request, response); // continue the request
    }
}
