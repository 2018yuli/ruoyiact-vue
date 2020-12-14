package com.ruoyi.activiti.config;

import org.activiti.engine.impl.cfg.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ruoyi.common.core.redis.RedisIdGenerator;

/**
 * @Auther: Ace Lee
 * @Date: 2019/3/11 16:05
 */
@Component
public class MyIdGenerator implements IdGenerator
{
	@Autowired
	private RedisIdGenerator redisIdGenerator;
	
	
	
    /**
     * 生成带时间的 ID 号
     */
    @Override
    public String getNextId()
    {
        String uuid = redisIdGenerator.generatorActId() + "";
        return uuid;
    }
}