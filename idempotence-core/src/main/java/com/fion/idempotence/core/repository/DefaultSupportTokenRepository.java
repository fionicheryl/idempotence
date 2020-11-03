package com.fion.idempotence.core.repository;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 默认支持的token存储
 *
 * @author yangfei100
 * @date 2020-10-29 15:42
 */
@Component
public class DefaultSupportTokenRepository implements TokenRepositoryAdapter {

    /**
     * 默认存储大小
     */
    private static int DEFAULT_REPOSITORY_CAPACITY = 1024;

    /**
     * 本地存储仓库
     * @see Node
     */
    private ConcurrentHashMap<String, Node> repository;

    /**
     * 按照过期时间大小排序的优先队列，方便清除过期节点
     * @see Node
     */
    private PriorityBlockingQueue<Node> queue;

    /**
     * 可重入锁，保证操作原子性
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 使用默认大小构造
     */
    public DefaultSupportTokenRepository() {
        repository = new ConcurrentHashMap<>(DEFAULT_REPOSITORY_CAPACITY);
        queue = new PriorityBlockingQueue<>(DEFAULT_REPOSITORY_CAPACITY);
    }

    /**
     * 设置token
     *
     * @param token token
     * @param ttl   生存时间
     */
    @Override
    public Object set(String token, long ttl) {
        return false;
    }

    /**
     * 获取token
     *
     * @param token token
     * @return
     */
    @Override
    public Object get(String token) {
        return null;
    }

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    @Override
    public Object remove(String token) {
        return null;
    }

    /**
     * 清洗过期token
     */
    private void washExpire() {
        long current = System.currentTimeMillis();
        while (!queue.isEmpty()) {
            Node node = queue.peek();
            // 没有数据是已过期的，直接返回
            if (node.expire > current) {
                return;
            }
            // 删除已过期的token
            repository.remove(node.token);
            queue.poll();
        }
    }

    /**
     * token存储节点
     */
    private class Node implements Comparable<Node> {

        /**
         * token，对应于 {@link repository} 的key
         */
        protected String token;

        /**
         * 过期时间
         */
        protected long expire;

        public Node(String token, long expire) {
            this.token = token;
            this.expire = expire;
        }

        @Override
        public int compareTo(Node other) {
            long diff = this.expire - other.expire;
            if (diff > 0) {
                return 1;
            } else if (diff < 0) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
