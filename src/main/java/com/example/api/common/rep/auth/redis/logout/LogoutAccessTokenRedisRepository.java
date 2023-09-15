package com.example.api.common.rep.auth.redis.logout;

import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutAccessTokenRedisRepository extends KeyValueRepository<LogoutAccessTokenRedis, String> {
}