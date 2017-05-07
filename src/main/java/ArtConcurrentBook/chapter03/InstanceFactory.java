package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
public class InstanceFactory {
	private static class InstanceHolder {
		public static Instance instance = new Instance();
	}

	public static Instance getInstance() {
		return InstanceHolder.instance; //这里将导致InstanceHolder类被初始化
	}

	static class Instance {
	}
}