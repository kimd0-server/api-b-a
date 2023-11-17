package com.example.api.auth.rep.redis.user;

import org.springframework.data.keyvalue.repository.KeyValueRepository;


public interface UserRedisRepository extends KeyValueRepository<UserRedisEntity, String> {
}
