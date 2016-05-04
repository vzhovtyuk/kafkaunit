package net.myrts.kafka;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.IOException;
import java.util.Properties;

public class ZooKeeperLocal {

    private ZooKeeperServerMainShutdown zooKeeperServer;
    private Runnable runner;
    private Thread runnerThread;

    public ZooKeeperLocal(Properties zkProperties) throws IOException {
        QuorumPeerConfig quorumConfiguration = new QuorumPeerConfig();
        try {
            quorumConfiguration.parseProperties(zkProperties);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        zooKeeperServer = new ZooKeeperServerMainShutdown();
        final ServerConfig configuration = new ServerConfig();
        configuration.readFrom(quorumConfiguration);

        runner = new Runnable() {
            @Override
            public void run() {
                try {
                    zooKeeperServer.runFromConfig(configuration);
                } catch (IOException e) {
                    System.out.println("ZooKeeper Failed");
                    e.printStackTrace(System.err);
                }
            }
        };
        runnerThread = new Thread(runner);
        runnerThread.start();
    }


    public void stop() {
        zooKeeperServer.shutdown();
    }

    class ZooKeeperServerMainShutdown extends ZooKeeperServerMain {
        @Override
        public void shutdown() {
            super.shutdown();
        }
    }
}