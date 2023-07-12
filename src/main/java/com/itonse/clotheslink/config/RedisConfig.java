package com.itonse.clotheslink.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

@RequiredArgsConstructor
@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    // 캐시 구성 빈 생성
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration conf = RedisCacheConfiguration.defaultCacheConfig()
                // Serialization(직렬화): 데이터, 오브젝트 값 -> 바이트 형태로 변환하여 저장
                .serializeKeysWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new StringRedisSerializer()))     // 키는 String 형식으로 serialize
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair.fromSerializer(new GenericJackson2JsonRedisSerializer()))        // 값은 Json 형식으로 serialize
                .entryTtl(Duration.ofSeconds(600L));     // 캐시 유효시간
        // 캐시매니저 초기화
        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)      // 아래에 정의한 RedisConnectionFactory 를 사용하여 레디스와의 연결 설정을 적용
                .cacheDefaults(conf)
                .build();
    }

    // 레디스 연결 관리 빈 생성
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();

        conf.setHostName(this.host);
        conf.setPort(this.port);
        // Lettuce 라이브러리를 사용하여 RedisConnectionFactory 를 생성하고 반환
        return new LettuceConnectionFactory(conf);
    }
}
