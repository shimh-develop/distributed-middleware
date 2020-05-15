package com.shimh.curator.framework;

import com.shimh.curator.framework.CreateClientExamples;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorOp;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;

import java.util.Collection;

/**
 * @author: shimh
 * @create: 2018年10月
 **/
public class TransactionExamples {

    public static Collection<CuratorTransactionResult> transaction(CuratorFramework client) throws Exception
    {
        // this example shows how to use ZooKeeper's transactions

        CuratorOp createOp = client.transactionOp().create().forPath("/path", "some data".getBytes());
        CuratorOp setDataOp = client.transactionOp().setData().forPath("/shimh", "other data".getBytes());
        CuratorOp deleteOp = client.transactionOp().delete().forPath("/shimh2");

        Collection<CuratorTransactionResult>    results = client.transaction().forOperations(createOp, setDataOp, deleteOp);

        for ( CuratorTransactionResult result : results )
        {
            System.out.println(result.getForPath() + " - " + result.getType());
        }

        return results;
    }

    public static void main(String[] args) throws Exception{
        String server = "localhost:2181";
        CuratorFramework client = CreateClientExamples.createSimple(server);
        client.start();


        transaction(client);



    }
}
