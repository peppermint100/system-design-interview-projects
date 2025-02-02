package com.example.newsfeedservice.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.RedisPassword
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig {

    @Primary
    @Bean("postRedisConnectionFactory")
    fun postRedisConnectionFactory(
        @Value("\${spring.data.redis.post.host}") host: String,
        @Value("\${spring.data.redis.post.port}") port: Int,
        @Value("\${spring.data.redis.post.password}") password: String,
        @Value("\${spring.data.redis.post.database}") database: Int,
    ): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = host
        config.port = port
        config.password = RedisPassword.of(password)
        config.database = database
        return LettuceConnectionFactory(config).apply {
            afterPropertiesSet()
        }
    }

    @Primary
    @Bean("postRedisTemplate")
    fun postRedisTemplate(
        @Qualifier("postRedisConnectionFactory") postRedisConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        return RedisTemplate<String, String>().apply {
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = StringRedisSerializer()
            setConnectionFactory(postRedisConnectionFactory)
            afterPropertiesSet()  // 초기화 추가
        }
    }

    @Bean("newsFeedRedisConnectionFactory")
    fun newsFeedRedisConnectionFactory(
        @Value("\${spring.data.redis.newsfeed.host}") host: String,
        @Value("\${spring.data.redis.newsfeed.port}") port: Int,
        @Value("\${spring.data.redis.newsfeed.password}") password: String,
        @Value("\${spring.data.redis.newsfeed.database}") database: Int,
    ): RedisConnectionFactory {
        val config = RedisStandaloneConfiguration()
        config.hostName = host
        config.port = port
        config.password = RedisPassword.of(password)
        config.database = database
        return LettuceConnectionFactory(config).apply {
            afterPropertiesSet()
        }
    }

    @Bean("newsFeedRedisTemplate")
    fun newsFeedRedisTemplate(
        @Qualifier("newsFeedRedisConnectionFactory") newsFeedConnectionFactory: RedisConnectionFactory
    ): RedisTemplate<String, String> {
        return RedisTemplate<String, String>().apply {
            keySerializer = StringRedisSerializer()
            valueSerializer = StringRedisSerializer()
            hashKeySerializer = StringRedisSerializer()
            hashValueSerializer = StringRedisSerializer()
            setConnectionFactory(newsFeedConnectionFactory)
            afterPropertiesSet()  // 초기화 추가
        }
    }
}