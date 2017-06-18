package view.user;
/**
 * <code><b>SimThread</b></code> 
 * 
 * @author 曹雨婷
 * 
 */
class SimThread extends Thread {// 线程类 
	private int current;// 进度栏的当前值
	private int target;// 进度栏的最大值

	public SimThread(int t) {
		current = 0;
		target = t;
	}

	public int getTarget() {
		return target;
	}

	public int getCurrent() {
		return current;
	}

	public void run() {// 线程体
		try {
			while (current < target && !interrupted()) {// 如果进度栏的当前值小于目标值并且线程没有被中断
				sleep(1);
				current++;
				if (current == 40) {
					sleep(50);
				} else if (current == 70) {
					sleep(20);
				}
			}
		} catch (InterruptedException e) {
		}
	}

}

