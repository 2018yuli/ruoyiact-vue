package com.ruoyi.common.utils.concurrent;

import java.util.HashMap;

/**
 * 由于synchronized只能锁对象的地址，所有像Long为1000的用户id是锁不住的
 * 此类来解决这个问题
 * synchronized (LockUtils.getLock("test" + userId)) {}
 * 因为是全局的入参当然是"接口名+id"最好了
 */
public class LockUtils {
    private static HashMap<String, String> mMapId = new HashMap<String, String>();
    private static HashMap<String, String> mMapIdCache = new HashMap<String, String>();
    
    /**
     * 缓存切换的开始时间，等待{@link #mCacheDeleteTime}时间后将清空切换数据
     */
    private static long mCacheCreatTime;
    /**
     * 最大缓存数(当超出这一数值时，会自动清空)，缓存切换等待时间
     */
    private static final int mMaxCache = 1000;
    private static final int mCacheDeleteTime = 10000;
 
    public static synchronized String getLock(String oldId) {
        String returnSt;
        if (mMapId.size() < mMaxCache) {//数据比较少，普通的返回锁
            if (!mMapId.containsKey(oldId)) {
                mMapId.put(oldId, oldId);
            }
            returnSt = mMapId.get(oldId);
        } else {//累加的残留数据太多，切换至缓存
            //缓存开始时间
            long nowMills = System.currentTimeMillis();
            if (mMapIdCache.size() == 0) {
                mCacheCreatTime = nowMills;
            }
 
            if (!mMapIdCache.containsKey(oldId)) {
                mMapIdCache.put(oldId, mMapId.getOrDefault(oldId, oldId));
            }
            returnSt = mMapIdCache.get(oldId);
 
            //等待mCacheChangeTime时间后清除原始数据
            if (nowMills - mCacheCreatTime > mCacheDeleteTime) {
                mMapId.clear();
                //原始和缓存对调即可实现切换
                HashMap<String, String> change = mMapId;
                mMapId = mMapIdCache;
                mMapIdCache = change;
            }
        }
        return returnSt;
    }
    
    /**
     * 利用缓存 10s 被动清理的特性
     * 解决工作流定时任务与手工执行时的并发问题
     * 1. 工作流定时任务执行时，将 tskID 存入 mMapId 缓存
     * 2. 工作流执行完毕后，调用本方法，将 tskID 从 mMapId 中移除
     * 3. 手工任务执行时，判断是否已经被加锁
     * @param tskId
     * @return
     */
    public static synchronized boolean releaseWorkflowJobLock(String tskId) {
    	// 
		if (mMapId.containsKey(tskId)) {
			mMapId.remove(tskId);
			return true;
		}
		if (mMapIdCache.containsKey(tskId)) {
			mMapIdCache.remove(tskId);
			return true;
		}
    	return false;
    }
    public static synchronized boolean isLockedByWorkflowJob(String tskId) {
        return mMapId.containsKey(tskId) || mMapIdCache.containsKey(tskId);
    }
}