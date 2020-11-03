package com.fion.idempotence.core.repository;

import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * 单机token存储存储（分布式环境下禁用）
 *
 * @author fion yang
 * @date 2020-11-03 10:30
 */
@Component
public class SingleSupportTokenRepositoryAdapter implements TokenRepositoryAdapter{

    /**
     * token存储
     */
    private ConcurrentHashMap<String, Node> repository = new ConcurrentHashMap<>(1024);

    /**
     * 优先队列，按照过期时间排序，便于清洗
     */
    private PriorityBlockingQueue<Node> queue = new PriorityBlockingQueue<>(1024);

    /**
     * 清洗过期节点线程池
     */
    private static ScheduledExecutorService handleExpiredPool = new ScheduledThreadPoolExecutor(10);

    /**
     * 构造方法，开启清除过期token任务
     */
    public SingleSupportTokenRepositoryAdapter() {
        handleExpiredPool.scheduleWithFixedDelay(washExpiredTask, 3, 3, TimeUnit.SECONDS);
    }


    /**
     * 设置token
     *
     * @param token token
     * @param ttl   生存时间
     */
    @Override
    public Object set(String token, long ttl) {
        long expire = System.currentTimeMillis() + ttl;
        Node node = new Node(token, expire);
        Node old = repository.put(token, node);
        queue.add(node);
        if (null != old) {
            queue.remove(old);
            return old.value;
        }
        return null;
    }

    /**
     * 获取token
     *
     * @param token token
     * @return
     */
    @Override
    public Object get(String token) {
        long now = System.currentTimeMillis();
        Node node = repository.get(token);
        if (null == node) {
            return null;
        }
        if (node.expire < now) {
            // 可能已过期，但还未来得及清理，此处直接返回null，不做清理工作
            return null;
        }
        return node.value;
    }

    /**
     * 删除token
     *
     * @param token
     * @return
     */
    @Override
    public Object remove(String token) {
        Node node = repository.remove(token);
        if (null != node) {
            queue.remove(node);
            return node.value;
        }
        return null;
    }

    /**
     * 清洗过期token任务
     */
    private Runnable washExpiredTask = () -> {
        long now = System.currentTimeMillis();
        while (true) {
            Node node = queue.peek();
            // 没有需要从缓存中删除的
            if (null == node || node.expire > now) {
                return;
            }
            // 删除过期的token
            repository.remove(node.token);
            queue.poll();
        }
    };

    /**
     * token存储节点
     */
    private static class Node implements Comparable<Node> {

        /**
         * token，对应于 {@link repository} 的key
         */
        protected String token;

        /**
         * 值，构造函数中 UUID 生成
         */
        protected String value;

        /**
         * 过期时间
         */
        protected long expire;

        public Node(String token, long expire) {
            this.token = token;
            this.value = UUID.randomUUID().toString();
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

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }
            if (!(obj instanceof Node)) {
                return false;
            }
            Node node = (Node) obj;
            if (Objects.equals(this.token, node.token)
                    && Objects.equals(this.value, node.value)
                    && Objects.equals(this.expire, node.expire)) {
                return true;
            }
            return false;
        }
    }
}
