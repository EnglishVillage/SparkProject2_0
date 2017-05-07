package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
class VolatileFeaturesExample {
	volatile long vl = 0L; //使用volatile声明64位的long型变量

	public void set(long l) {
		vl = l; //单个volatile变量的写
	}

	public void getAndIncrement() {
		vl++; //复合（多个）volatile变量的读/写
	}

	public long get() {
		return vl; //单个volatile变量的读
	}
}
