package com.example.api.common.rep.auth.redis.user;

import org.springframework.data.keyvalue.repository.KeyValueRepository;


public interface UserRedisRepository extends KeyValueRepository<UserRedisEntity, String> {
}
