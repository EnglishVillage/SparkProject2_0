package ArtConcurrentBook.chapter03;

/**
 * 名称：王坤造
 * 时间：2017/5/7.
 * 名称：
 * 备注：
 */
class MonitorExample {
	int a = 0;

	public synchronized void writer() { //1
		a++; //2
	} //3

	public synchronized void reader() { //4
		int i = a; //5
		//бнбн
	} //6
}
