package com.lpb.esb.service.cache.model.redis;

import com.google.gson.Gson;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Created by tudv1 on 2021-07-14
 */
@RedisHash("token")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@With
public class UserIpRangeEntity {
    @Id
    private String username;
    private String fromIp;
    private String toIp;

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
