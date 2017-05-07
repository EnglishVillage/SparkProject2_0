package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class SafeLazyInitialization {
	private static Instance instance;

	public synchronized static Instance getInstance() {
		if (instance == null)
			instance = new Instance();
		return instance;
	}

	static class Instance {
	}
}
