package com.sp.jobportal.config.cache;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class RedisCacheConfig {

    @Value("${cache.jobs.ttl-minutes:5}")
    private String jobsCacheTtlMinutes;

    @Value("${cache.jobs.max-size}")
    private String jobsCacheMaxSize;

    @Value("${cache.companies.ttl-minutes:15}")
    private String compaiesCacheTtlMinutes;

    @Value("${cache.companies.max-size}")
    private String companiesCacheMaxSize;

    @Value("${cache.roles.ttl-days:5}")
    private String rolesCacheTtlMinutes;

    @Value("${cache.roles.max-size}")
    private String rolesCacheMaxSize;

    @Autowired
    private Environment env;

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {

        String jobsCacheTtlMinutes = env.getProperty("cache.jobs.ttl-minutes");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        PolymorphicTypeValidator ptv = LaissezFaireSubTypeValidator.instance;
        objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL);

        RedisSerializer<Object> jsonSerializer = new TypedJsonRedisSerializer(objectMapper);

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(10))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        RedisCacheConfiguration companiesConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(Long.valueOf(compaiesCacheTtlMinutes)))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        RedisCacheConfiguration jobsConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(Long.valueOf(jobsCacheTtlMinutes)))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        RedisCacheConfiguration messagesConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(Long.valueOf(rolesCacheTtlMinutes)))
                .disableCachingNullValues()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jsonSerializer));

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
        cacheConfigurations.put("companies", companiesConfig);
        cacheConfigurations.put("jobs", jobsConfig);
        cacheConfigurations.put("messages", messagesConfig);

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    // Custom serializer: forces root value to always be treated as Object,
    // so type info is written the SAME way it's expected on read.
    static class TypedJsonRedisSerializer implements RedisSerializer<Object> {

        private final ObjectMapper objectMapper;

        TypedJsonRedisSerializer(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public byte[] serialize(Object value) throws SerializationException {
            if (value == null) return new byte[0];
            try {
                return objectMapper.writerFor(Object.class).writeValueAsBytes(value);
            } catch (Exception e) {
                throw new SerializationException("Could not write JSON: " + e.getMessage(), e);
            }
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            if (bytes == null || bytes.length == 0) return null;
            try {
                return objectMapper.readValue(bytes, Object.class);
            } catch (Exception e) {
                throw new SerializationException("Could not read JSON: " + e.getMessage(), e);
            }
        }
    }
}