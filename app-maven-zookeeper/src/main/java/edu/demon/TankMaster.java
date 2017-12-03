package edu.demon;

import java.util.Random;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;

public class TankMaster {
	static String status = "ACTIVE";
	public static void main(String[] args) throws Exception{
		String CONN = "192.168.100.109:2181";
		ZooKeeper zk = new ZooKeeper(CONN, 50000, null);
		String IP = "192.0.0.1";
		String port = new Random().nextInt(500) + "";
		System.out.println(port);
		if (zk.exists("/tankmaster", new MasterWatcher()) != null) {
			status = "STANDBY";
		} else {
			zk.create("/tankmaster", (IP+":"+port).getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
			status = "ACTIVE";
			System.out.println("启动就是ACTIVE");
		}
		Thread.sleep(Integer.MAX_VALUE);
	}
	public static class MasterWatcher implements Watcher {

		public void process(WatchedEvent event) {
			if (event.getType() == EventType.NodeDeleted) {
				status = "ACTIVE";
				System.out.println("切换为ACTIVE");
			}
		}
	}
}
