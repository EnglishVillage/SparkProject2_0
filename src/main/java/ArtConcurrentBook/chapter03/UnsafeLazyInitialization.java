package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class UnsafeLazyInitialization {
	private static Instance instance;

	public static Instance getInstance() {
		if (instance == null) //1：A线程执行
			instance = new Instance(); //2：B线程执行
		return instance;
	}

	static class Instance {
	}
}
