package ArtConcurrentBook.chapter03;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
class ReentrantLockExample {
	int a = 0;
	ReentrantLock lock = new ReentrantLock();

	public void writer() {
		lock.lock(); //获取锁
		try {
			a++;
		} finally {
			lock.unlock(); //释放锁
		}
	}

	public void reader() {
		lock.lock(); //获取锁
		try {
			int i = a;
			//……
		} finally {
			lock.unlock(); //释放锁
		}
	}
}
