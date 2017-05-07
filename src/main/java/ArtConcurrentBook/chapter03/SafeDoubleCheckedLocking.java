package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class SafeDoubleCheckedLocking {
	private volatile static Instance instance;

	public static Instance getInstance() {
		if (instance == null) {
			synchronized (SafeDoubleCheckedLocking.class) {
				if (instance == null)
					instance = new Instance();//instance为volatile，现在没问题了
			}
		}
		return instance;
	}

	static class Instance {
	}
}
