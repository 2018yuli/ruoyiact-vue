package com.ruoyi.common.core.redis;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

/**  
 * @Description	基于Redis分布式ID生成实现
 * @author		butterfly
 * @date	    2017年8月3日 下午2:35:16  
 */
@Service
public class RedisIdGenerator {
	
	/**
	 * 
	 */
	@SuppressWarnings("rawtypes")
	@Autowired
    private RedisTemplate redisTemplate;
	
	
	private enum BizType {
		BIZ, 	// 业务
		ACT,	// 工作流
		OTHER	// 其他
	}
	
	/**
	 * @Description 生成分布式ID
	 * 
	 * @author butterfly
	 * @return
	 */
	public Long generatorBizId() {
		// 转成数字类型，可排序
		return generatorId(BizType.BIZ.name());
	}
	/**
	 * 工作流专用
	 * @return
	 */
	public Long generatorActId() {
		// 转成数字类型，可排序
		return generatorId(BizType.ACT.name());
	}
	
	/**
	 * @Description 生成分布式ID
	 * 
	 * @author butterfly
	 * @return
	 */
	private Long generatorId(String biz) {
		// 转成数字类型，可排序
		return incrOrderId(biz, getOrderPrefix(new Date()));
	}
	
	/**
	 * @Description 
	 * 
	 * @author butterfly
	 * @param date
	 * @return
	 */
	private String getOrderPrefix(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int year = c.get(Calendar.YEAR);
		int day = c.get(Calendar.DAY_OF_YEAR); // 今天是第多少天
		int hour =  c.get(Calendar.HOUR_OF_DAY);
		String dayFmt = String.format("%1$03d", day); // 0补位操作 必须满足三位
		String hourFmt = String.format("%1$02d", hour);  // 0补位操作 必须满足2位
		StringBuffer prefix = new StringBuffer();
		prefix.append((year - 2000)).append(dayFmt).append(hourFmt);
		return prefix.toString();
	}
	
	/**
	 * @Description 支持一个小时100w个订单号的生成
	 * 
	 * @author butterfly
	 * @param prefix
	 * @return
	 */
	private Long incrOrderId(String biz, String prefix) {
		String orderId = null;
		String key = "opms:#{biz}:id:".replace("#{biz}", biz).concat(prefix); // 00001
		try {

			RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
			// 25 小时后自动失效
			entityIdCounter.expire(25, TimeUnit.HOURS);
			Long index = entityIdCounter.getAndIncrement();
			orderId = prefix.concat(String.format("%1$05d", index)); // 补位操作 保证满足5位
		} catch(Exception ex) {
			System.out.println("分布式订单号生成失败异常。。。。。");
		}
		if (StringUtils.isEmpty(orderId)) return null;
		return Long.parseLong(orderId);
	} 
	
}
