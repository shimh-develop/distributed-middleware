package com.shimh.curator.leader;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.CloseableUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: shimh
 * @create: 2018年10月
 **/
public class LeaderSelectorExample {


    private static final int        CLIENT_QTY = 10;

    private static final String     PATH = "/examples/leader";

    private static final String server = "localhost:2181";

    public static void main(String[] args) throws Exception
    {
        // all of the useful sample code is in ExampleClient.java

        System.out.println("Create " + CLIENT_QTY + " clients, have each negotiate for leadership and then wait a random number of seconds before letting another leader election occur.");
        System.out.println("Notice that leader election is fair: all clients will become leader and will do so the same number of times.");

        List<CuratorFramework> clients = new ArrayList<>();
        List<ExampleClient>     examples = new ArrayList<>();
        // TestingServer           server = new TestingServer();
        try
        {
            for ( int i = 0; i < CLIENT_QTY; ++i )
            {
                CuratorFramework    client = CuratorFrameworkFactory.newClient(server, new ExponentialBackoffRetry(1000, 3));
                clients.add(client);

                ExampleClient       example = new ExampleClient(client, PATH, "Client #" + i);
                examples.add(example);

                client.start();
                example.start();
            }

            System.out.println("Press enter/return to quit\n");
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        }
        finally
        {
            System.out.println("Shutting down...");

            for ( ExampleClient exampleClient : examples )
            {
                CloseableUtils.closeQuietly(exampleClient);
            }
            for ( CuratorFramework client : clients )
            {
                CloseableUtils.closeQuietly(client);
            }

            // CloseableUtils.closeQuietly(server);
        }
    }
}
