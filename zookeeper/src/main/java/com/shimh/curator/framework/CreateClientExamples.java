package com.shimh.curator.framework;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class CreateClientExamples {

    public static CuratorFramework createSimple(String connectionString)
    {
        // these are reasonable arguments for the ExponentialBackoffRetry. The first
        // retry will wait 1 second - the second will wait up to 2 seconds - the
        // third will wait up to 4 seconds.
        /**
         * baseSleepTimeMs：初始的重试等待时间
         * maxRetries：最多重试次数
         */
        ExponentialBackoffRetry retryPolicy = new ExponentialBackoffRetry(1000, 3);

        // The simplest way to get a CuratorFramework instance. This will use default values.
        // The only required arguments are the connection string and the retry policy
        return CuratorFrameworkFactory.newClient(connectionString, retryPolicy);
    }

    public static CuratorFramework  createWithOptions(String connectionString, RetryPolicy retryPolicy, int connectionTimeoutMs, int sessionTimeoutMs)
    {
        // using the CuratorFrameworkFactory.builder() gives fine grained control
        // over creation options. See the CuratorFrameworkFactory.Builder javadoc
        // details
        /**
         * connectionTimeoutMs 连接超时时间
         * sessionTimeoutMs 会话超时时间
         *
         */
        return CuratorFrameworkFactory.builder()
                .connectString(connectionString)
                .retryPolicy(retryPolicy)
                .connectionTimeoutMs(connectionTimeoutMs)
                .sessionTimeoutMs(sessionTimeoutMs)
                // etc. etc.
                .build();
    }

    public static void main(String[] args) {
        String server = "localhost:2181";
        CuratorFramework client = createSimple(server);
        client.start();
        System.out.println(client.getState());

    }




}
