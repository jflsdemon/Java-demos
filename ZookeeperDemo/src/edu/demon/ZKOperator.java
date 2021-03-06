package edu.demon;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

public class ZKOperator {
	// create static instance for zookeeper class.
	private static ZooKeeper zk;

	// create static instance for ZooKeeperConnection class.
	private static ZookeeperConnection conn;

	// Method to create znode in zookeeper ensemble
	public static void create(String path, byte[] data) throws KeeperException, InterruptedException {
		zk.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}

	public static Stat exists(String path) throws KeeperException, InterruptedException {
		return zk.exists(path, true);
	}

	public static Stat update(String path, byte[] data) throws KeeperException, InterruptedException {
		return zk.setData(path, data, zk.exists(path, true).getVersion());
	}
	
	public static void delete(String path) throws InterruptedException, KeeperException {
		zk.delete(path, zk.exists(path, false).getVersion());
	}
	public static void checkCreate() {
		String path = "/MyFirstZnode";
		byte[] data = "My first zookeeper app".getBytes(); // Declare data
		try {
			conn = new ZookeeperConnection();
			zk = conn.connect("localhost");
			create(path, data);
			zk.close();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkExist() {

		try {
			String path = "/MyFirstZnode";
			conn = new ZookeeperConnection();
			zk = conn.connect("localhost");
			Stat stat;
			stat = exists(path);
			if (stat != null) {
				System.out.println("Node exists and the node version is " + stat.getVersion());
			} else {
				System.out.println("Node does not exists");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void checkGetData() {
		// znode path
		String path = "/MyFirstZnode2"; // Assign path to znode
		// data in byte array
		byte[] data = "My first zookeeper app".getBytes(); // Declare data

		final CountDownLatch connectedSignal = new CountDownLatch(1);

		try {
			conn = new ZookeeperConnection();
			zk = conn.connect("localhost");

			Stat stat = exists("/MyFirstZnode");
			if (stat != null) {
				System.out.println("Node exists and the node version is " + stat.getVersion());
				byte[] b = zk.getData("/MyFirstZnode", new Watcher() {

					@Override
					public void process(WatchedEvent event) {
						if (event.getType() == EventType.None) {
							switch (event.getState()) {
							case Expired:
								connectedSignal.countDown();
								break;
							default:
								break;
							}
						} else {
							String path = "/MyFirstZnode";
							byte[] bn;
							try {
								bn = zk.getData(path, false, null);
								String data = new String(bn, "UTF-8");
								System.out.println("data: " + data);
								connectedSignal.countDown();
							} catch (UnsupportedEncodingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (KeeperException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						}

					}

				}, stat);
				String getData = new String(b, "UTF-8");
				System.out.println("getdata: " + getData);
				connectedSignal.await();
			} else {
				System.out.println("Node does not exists");
			}
			conn.close();
		} catch (Exception e) {
			System.out.println(e.getMessage()); // Catch error message
		}
	}
	
	public static void checkUpdate() {
		String path = "/MyFirstZnode";
		byte[] data = "Success update".getBytes();
		conn = new ZookeeperConnection();
		try {
			zk = conn.connect("localhost");
			Stat stat = update(path, data);
			System.out.println(stat.toString());
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		}
	}
	public static void checkGetChildren() {
		String path = "/MyFirstZnode";
		conn = new ZookeeperConnection();
		try {
			zk = conn.connect("localhost");
			Stat stat = exists(path);
			if (stat != null) {
				List<String> children = zk.getChildren(path, false);
				for (Iterator<String> iterator = children.iterator(); iterator.hasNext();) {
					String string = iterator.next();
					System.out.println(string);
				}
			} else {
				System.out.println("Node does not exists");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	public static void checkDelete() {
		String path = "/MyFirstZnode1";
		conn = new ZookeeperConnection();
		try {
			zk = conn.connect("localhost");
			delete(path);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		} catch (KeeperException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		checkDelete();
	}
}
