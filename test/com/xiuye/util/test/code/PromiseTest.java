package com.xiuye.util.test.code;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import com.xiuye.sharp.Promise;
import com.xiuye.util.code.XCode;
import com.xiuye.util.log.XLog;

public class PromiseTest {

	@Test
	public void test() {
		// default true
//		Promise.implyErrorHandler(false);
		new Promise<>(99999).then(d -> {
			XLog.lg(d);
			XLog.lg("void call(I in)");

		}).then(d -> {
			XLog.lg(d);
			XLog.lg("R call(I in)");

			return 999;
		}).then(() -> {
			XLog.lg("R call()");
			return "ABC";
		}).then(() -> {
			XLog.lg("void call()");
		}).then(() -> {
			XLog.lg("End");
		}).then(() -> {
			throw new RuntimeException("My Exception intentional");
		}).except(e -> {
			XLog.lg(e);
			return e;
		}).except(e -> {
			XLog.lg(e);
		}).except(e -> {
			XLog.lg(e);
		}).except(() -> {
			throw new RuntimeException("Nothing");
		})./* then(()->{}). */except(e -> {
			XLog.lg(e);
			return 100;
		}).except(d -> {
			XLog.log(d);
		}).except(() -> {

		}).except(() -> {

		});
	}

	@Test
	public void testIfElse() {

		Promise.resolve().begin().ef(false).then(() -> {
			XLog.lg("123");
		}).eeseEf(true).then(() -> {
			XLog.lg("then");
		}).eeseEf(true).then(() -> {
			XLog.lg("then2");
		}).eese(() -> {
			XLog.lg("ABC");
		}).ef(true).then(() -> {
			XLog.lg("2 then");
		}).eese(() -> {
			XLog.lg("EESE");
		}).end().then(d -> {
			XLog.lg(d);
		});

		XLog.lg(trueflag() | falseflag());
		XLog.lg(trueflag() || falseflag());

	}

	private boolean trueflag() {
		XLog.lg("trueflag run");
		return true;
	}

	private boolean falseflag() {
		XLog.lg("falseflag run");
		return false;
	}

	@Test
	public void testMatchAs() {
		Promise.of().begin().match(123).as(99).then(() -> {
			XLog.lg("matched 1");
		}).as(100).as(123).then(() -> {
			XLog.lg("matched 2");
		}).defaut(() -> {
			XLog.lg("default");
		}).end();

		Promise.of().begin().match(11).as(56789).as(11).then(() -> {
			XLog.lg("56789");
		}).defaut(() -> {
			XLog.lg("default");
		}).then(() -> {
		}).end().then(() -> {
			XLog.lg("then");
		});
		XLog.lg(null instanceof String);
		String s = null;
		XLog.lg(s instanceof String);

	}

	@Test
	public void testAllSwitchStatement() {

		Promise.beginS().ef(false).then(() -> {
			XLog.lg(false);
		}).eeseEf(true).then(() -> {
			XLog.lg(127, true);
		}).eese(() -> {
			XLog.lg("else");
		}).ef(true).then(() -> {
			XLog.lg(131, true);
		}).eese(() -> {
			XLog.lg("eese");
		}).match(5).as(7).then(() -> {
			XLog.lg(7);
		}).as(5).then(() -> {
			XLog.lg(137, 5);
		}).defaut(() -> {
			XLog.lg(999);
		}).ef(true).then(() -> {
			XLog.lg(139, true);
		}).end();

		Promise.beginS().match(123).as(123).as(99).then(() -> {
			XLog.lg(149, 123);
		}).end();

		Promise.beginS().match(99).as(123).then(() -> {
			XLog.lg(153, 123);
		}).as(99).as(100).then(() -> {
			XLog.lg(157, 123);
		}).ef(true).then(() -> {
			XLog.lg(159, true);
		}).ef(true).then(() -> {
			XLog.lg(160, true);
		}).match(true).defaut(() -> {
			XLog.lg(161, true);
		}).match("ABC").as("ABC").then(() -> {
			XLog.lg("ABC");
		}).match(1).as(1).as(2).then(() -> {
			XLog.lg(1);
		}).ef(false).then(() -> {
			XLog.lg(false);
		}).eeseEf(true).then(() -> {
			XLog.lg(true);
		}).ef(false).then(() -> {
			XLog.lg(false);
		}).eese(() -> {
			XLog.lg("else");
		}).ef(false).then(() -> {
			XLog.lg("doing");
		})
//		.ef(false)
				.match(11).as(88).as(11).then(() -> {
					XLog.lg(193);
				}).as(11).then(() -> {
					XLog.lg(197);
				}).defaut(() -> {
					XLog.lg("default");
				}).ef(true).then(() -> {
					XLog.line(true);
				}).ef(true).then(() -> {
					XLog.ln(true);
				}).end();
	}

	@Test
	public void testThread() throws InterruptedException {
//		Promise.threadS(()->{
//			XLog.ln("thread S");
//		}).then(t->{
//			t.start();
//		}).thread(d->{
//			XLog.ln(d);
//		}).then(d->{
//			d.start();
//		});

		Promise.of().task(() -> {
			XLog.ln(123);
		}).task(d -> {
			XLog.ln(d);
		}).task(d -> {
			XLog.ln(d);
			return 100;
		}).task(d -> {
			XLog.ln(d, d.get(), d.getError());
		}).task(() -> 88888).line().ln();
		Promise.lineS("ABC", 123);
		Promise.lnS("ABC", 123);
		Thread.sleep(3000);
//		Thread.class.wait();

		Promise.taskS(() -> {
			throw new RuntimeException("OKOKOKOKOKOKOK");
		}).except(e -> {
			XLog.ln(e);
			e.printStackTrace();
		}).then(d -> {
			XLog.ln(d, d.get(), d.getError());
		});
		Promise.lnS(Promise.taskS(() -> {
			return "KKKKKKKKKKKK";
		}).get().get());

	}

	@Test
	public void testLogOut() throws InterruptedException, ExecutionException, TimeoutException {
		Promise.logS(123, 666, "ABC");
		Promise.lgS(123, 666, "ABC");
		Promise.lnS(123, 666, "ABC");
		Promise.lineS(123, 666, "ABC").line().log().toJson().line().toFormatterJson().line().log();
		XLog.ln(123, "ABC");
		XLog.line(123, "ABC");
		String[] ss = { "A", "B", "C" };
		Promise.of(ss).line();
		Promise.of(ss).toJson().line().toJson().line();
		Promise.of(ss).toFormatterJson().line().toFormatterJson().line();
		Promise.formatterJsonKitS().then(d -> {
			return d.fromJson((String) null, Object.class);
		}).line();
		Promise.of(ss).toJson().line().toObject(String[].class).then(d -> {
			for (String s : d) {
				XLog.ln(s);
			}
			return null;
		}).toObject(String[].class).line().and(true).or(true).line().ln();

		Promise.of(new A("ABC", "DEF")).toFormatterJson().line().toObject(Map.class).line().toFormatterJson().line()
				.toObject(A.class).line().toObject(Map.class).except(e -> {
					e.printStackTrace();
				}).line();

//		FutureTask<String> futureTask = new FutureTask<>(()->{
//			return "ABC";
//		});
//		XLog.ln(futureTask.get(1, TimeUnit.SECONDS));
// 		Thread.sleep(3000);
	}

	private static class A {
		String a;
		String b;

		public A(String a, String b) {
			super();
			this.a = a;
			this.b = b;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public String getB() {
			return b;
		}

		public void setB(String b) {
			this.b = b;
		}

		@Override
		public String toString() {
			return "A [a=" + a + ", b=" + b + "]";
		}

	}

	@Test
	public void testReject() {
//		Promise.reject(new RuntimeException("ABC")).then(d->{
//			XLog.ln(d);
//			return d;
//		}).line();

		Promise.reject(new RuntimeException("ABC")).except(e -> {
			XLog.ln(e);
			return e;
		}).line();

		new Promise<>(123);
		new Promise<>(new RuntimeException("ABC")).except(() -> {
		});

	}

	@Test
	public void testBeansPool() {
		Promise.beanS("abc", String.class).getBean().end().ln();
		Promise.beanS("abc", "ABC").register().getBean().end().ln();
		Promise.beanS("abc").getBean().end().ln();
		Promise.beanS("def", String.class, "DEF").register().getBean().end().ln();
		Promise.beanS("def", String.class).getBean().end().ln();
		Promise.beanS("abc").end().ln().bean("abc").getBean().end().ln();

		Promise.beanS(String.class).getBean().end().ln();
		Promise.beanS(String.class, "CCCCC", true).register().getBean().end().ln();
	}

	@Test
	public void testBeansPool2() {
		Promise.beanS(String.class).getBean().end().ln();
		Promise.beanS("a", "ABC").register().getBean().end().ln().then(d -> {
			XLog.ln(d.toCharArray());
			return d.toCharArray();
		}).bean(String.class, "DEF").register().getBean().end().ln().bean(String.class).getBean().end().ln().bean("a")
				.getBean().end().ln();
//		XCode.runS(()->{
//			for(int i=0;i<10000;i++) {
//				Promise.beanS(String.class,"abc"+i,true)
//				.register()
//				.getBean()
//				.end()
//				.ln();
//				Promise.beanS("abc"+i,"abc"+i)
//				.register()
//				.getBean()
//				.end()
//				.ln();
//			}
//		});

		XCode.runS(() -> {
			for (int i = 0; i < 10000; i++) {
				int j = i;

				Promise.taskS(() -> {
					Promise.beanS(String.class, "abc" + j, true).register().getBean().end().ln();
				});
				Promise.taskS(() -> {
					Promise.beanS("abc" + j, "abc" + j).register().getBean().end().ln();
				});

			}
		});

		Promise.beanS(String.class).getBean().end().ln();
	}

	@Test
	public void testNetworkServer() {
//		Promise.udpS(8888).then(d->{
//			byte []data = new byte[1024];
//			DatagramPacket dp = new DatagramPacket(data, data.length);
//			try {
//				d.receive(dp);
//				XLog.lg(dp,new String(data));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		});

//		Promise.tcpS(8888).then(d->{
//			try {
//				return d.accept();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}).exist(d->{
//			try {
//				InputStream is = d.getInputStream();
////				int i=-1;
////				while((i=is.read())!=-1) {
////					XLog.print(i);
////				}
//				byte []data = new byte[1024];
//				is.read(data);
//				XLog.lg(new String(data));
//				d.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		});

	}

	@Test
	public void testNetworkClient() {

//		Promise.tcpS("localhost",8888).then(d->{
//			OutputStream os;
//			try {
//				os = d.getOutputStream();
//				os.write("汉字".getBytes());
//				d.close();
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}
//		});

//		Promise.udpS().then(d->{
//			String s = "汉字，这是汉字！嘿嘿!";
//			try {
//				DatagramPacket dp = new DatagramPacket(
//						s.getBytes(), 
//						s.getBytes().length,
//						InetAddress.getByName("localhost"),
//						8888
//						);
//				d.send(dp);
//				XLog.lg("sent!");
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			
//		});
	}

	private ZooKeeper zk = null;

//	@Test
	public void testZooKeeper() throws IOException, InterruptedException, KeeperException {
		CountDownLatch connectedSemaphore = new CountDownLatch(1);
		Stat stat = new Stat();
		String path = "/demo_five";
		zk = new ZooKeeper("127.0.0.1:2181", 5000, e -> {
			if (KeeperState.SyncConnected == e.getState()) {
				if (EventType.None == e.getType() && null == e.getPath()) {
					connectedSemaphore.countDown();
				} else if (e.getType() == EventType.NodeDataChanged) {
					try {
						XLog.ln(zk);
						XLog.ln("配置已修改，新值为：" + new String(zk.getData(e.getPath(), true, stat)));
					} catch (KeeperException | InterruptedException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
//		zk.create(path, "终于".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		connectedSemaphore.await();
		XLog.ln(new String(zk.getData(path, true, stat)));
		zk.setData(path, "第三方科技按还款法还是开发开放了".getBytes(), -1);
		
		Thread.sleep(10000);

	}
}
