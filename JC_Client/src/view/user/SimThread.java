package view.user;
/**
 * <code><b>SimThread</b></code> 
 * 
 * @author ������
 * 
 */
class SimThread extends Thread {// �߳��� 
	private int current;// �������ĵ�ǰֵ
	private int target;// �����������ֵ

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

	public void run() {// �߳���
		try {
			while (current < target && !interrupted()) {// ����������ĵ�ǰֵС��Ŀ��ֵ�����߳�û�б��ж�
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

