package view.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Administrator
 */
public class Music implements Runnable {

	// TODO ����1�������ķ�Χ��0��2.0����Ҫ����Ϊʲô��Ҳ��֪��233
	double volume = 2.0;
	// TODO ����2�����ڿ��������ĸ���ֵ��Χ
	FloatControl floatControl = null;
	
	String filePath = null; // �ļ�·��
	boolean loop = true; // �Ƿ�ѭ��
	AudioInputStream audioInputStream = null;// ��Ƶ������, ��FileInputStream����
	SourceDataLine sourceDataLine = null;

	/**
	 * ���캯������ʼ�����ֵ�·��
	 * @param path
	 */
	public Music(String command) {
		String resultFile = readFile("SystemSet");
		int mv = Integer.parseInt(resultFile.split("%")[0]);
		int sv = Integer.parseInt(resultFile.split("%")[1]);
		switch(command){
		case "select":
			this.filePath="sound/select.wav";
			this.setLoop(false);
			this.volume=sv;
			break;
		case "match":
			this.filePath="";
			break;
		case "fall":
			this.filePath="";
			break;
		case "backgr":
			this.filePath="sound/backgrmusic.mp3";
			this.volume=mv;
			this.setLoop(true);
			break;
		default:
			break;
			
		}
		new Thread(this).start();
//		new Thread(this).start();
		
	}

	/**
	 * �����Ƿ�ѭ������
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * implements Runnable ����Ҫʵ�ֵķ���<br>
	 * ����������õ�ʱ�򣬻��Զ������������ʼ��
	 */
	@Override
	public void run() {
		do { // ����ѭ��yeah����Ϊ�ƺ�û��api���ҵ��Դ���loop�����������û����233
			try {// �������try��catch�����ǳ���������Щ������
			    // ��������˴���ͻ�ִ��catch�ķ������жϳ���
				init();
				play();
			} catch (Exception e) {
				e.printStackTrace();//��������������Ϣ�Ĵ��롭��������ô����233
			}
		} while (loop);
	}
	
	/**
	 * ��ʼ�����ֵ�������ݣ���ʵ���뼸�����ǹ̶���<br>
	 * ����throws Exception ���ǰ������Ĵ��󶪵���һ�㡾��ϸ��run��try{}catch{}<br>
	 * �ڶ�ȡ�ļ���ʱ����Ҫ��һthrows
	 */
	public void init() throws Exception {
		// 1�����ļ�ת��Ϊ��Ƶ������
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
		// 2����ȡ����ļ�����Ƶ��ʽ
		AudioFormat audioFormat = audioInputStream.getFormat();
		// mp3ת��
		// �����ж�audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED
		// ����ΪMP3��ʽ��Ϊ�����ʽ�����������������ƣ�PCM������Ƶ���ݣ�����PCM_SIGNED��PCM_SIGNED������
		if (filePath.endsWith(".mp3")
				& audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			// �½�һ���涨����Ƶ��ʽ
			audioFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,// �����ʽ
					audioFormat.getSampleRate(), // ԭ���Ĳ��β�����
					16,// 16λ˫�ֽڲɼ�
					audioFormat.getChannels(),// ԭ����������
			//  ���������ÿ֡�е��ֽ�����ÿ����ÿ֡�ֽ���2�ֽڣ��ټ���˫������������������2��*�ֽ�����2��
					audioFormat.getChannels() * 2,
					audioFormat.getSampleRate(),  // ÿ���֡��
					false);// ������������
			// ����ǰ����Ƶ��ת����ָ���ĸ�ʽ
			audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
		}
		// 3��������Ƶ�е���Ϣ
		Info info = new Info(SourceDataLine.class, audioFormat);
		// 4���ƺ��ǽ�����Ƶ�С��Ѿ���ʼ���ײ��ʵ�����Ҳ�����orz
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
		sourceDataLine.open(audioFormat);// ����Ƶ������������Ƶ��
		sourceDataLine.start();
		// TODO ��ʼ��FloatControl�� FloatControl.Type.MASTER_GAIN��ĳһ�����������Ŀؼ�
		floatControl = (FloatControl) sourceDataLine.
				getControl(FloatControl.Type.MASTER_GAIN);
		// TODO ����ΪĬ�ϵ�������С
		setVolume(volume, true);
	}
	
	/**
	 * ������Ƶ�ļ�
	 */
	public void play() {
		try {
			int nByte = 0;
			final int SIZE = 1024 * 64;
			byte[] buffer = new byte[SIZE];
			while (nByte != -1) {
				// ����Ƶ����д���Ƶ�������ð����Ѿ�������233
				sourceDataLine.write(buffer, 0, nByte);
				nByte = audioInputStream.read(buffer, 0, SIZE);
			}
			// ������ֹͣ
			sourceDataLine.stop();
			// ��ջ���������ʵӦ��ûʲô��Ҫ
			sourceDataLine.flush();
			sourceDataLine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// TODO �ǵ�ֻҪ����TODO�Ϳ������ұ߿������������ˡ���
	/**
	 * ���������Ĵ�С
	 * 
	 * @param newVolume
	 * <br />
	 *                ���ֱ�������ѹֵ, ��ΧΪ0.0 ~ 2.0<br />
	 *                �������Ϊ�ٷֱ�, ��ΧΪ0.0 ~ 100.0<br />
	 * @param isPercent
	 * <br />
	 *                �������Ϊ�ٷֱ�, ��˴�Ϊtrue
	 * @see FloatControl.Type.MASTER_GAIN
	 */
	public void setVolume(Double newVolume, boolean isPercent) {
		if (isPercent) {
			// ���ٷֱ�ת����0.0~2.0
			volume = newVolume / 50.0;
		}
		// �����и��ֱ��ļ��㹫ʽ dB = 20lg(E/Er) E-ʵ�ʲ�����ѹֵ��Er-�ο���ѹֵ
		// �������newVolume�����ʾ��ѹֵ��ΪE/Er
		// ��ϸ��FloatControl.Type.MASTER_GAIN��API�ĵ�����̫������orz
		float dB = (float) (Math.log(volume == 0.0 ? 0.0001 : volume)
				/ Math.log(10.0) * 20.0);
		floatControl.setValue(dB);
		this.volume=volume*50.0;
	}
	
	// ����
	public static void main(String[] args) {
//		PS��eclipse��ctrl+/����ֱ������ע����
		final Music musicPlay = new Music("select");
//		VolChangeAudioSample.setLoop(false);

//		��Ҫ����дһ��JSlider��Ȼ���϶��͵���VolChangeAudioSample.setVolume����
		JFrame jFrame = new JFrame();
		jFrame.setSize(100, 60);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�������˳�
		jFrame.setResizable(false);// ���ɸı��С
		jFrame.setLocationRelativeTo(null);// ������С���Ҫ��setSize֮��

		//slider�ҾͲ�д�����ˡ������϶�����
		// 					��Сֵ0���ֵ100��Ĭ��Ϊ80
		final JSlider slider1 = new JSlider(JSlider.HORIZONTAL,0,100,80);
		// �Ӽ�����������ϲ�������������ڲ���233�������������ž�����233
		slider1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				musicPlay.setVolume((double)slider1.getValue(), true);
			}
		});
		jFrame.add(slider1);
		jFrame.setVisible(true);
		
//		�����������½�һ���̣߳���ʵ���ǣ�
//		Thread aThread = new Thread(musicPlay);
//		aThread.start();
		new Thread(musicPlay).start();
//		ע��������start()��������ʵ�ֶ��̣߳�Ȼ��ϵͳ�Զ������run()����
//		���д����new Thread(musicPlay).run();������ͨ�ĺ�������
		
	}
	public String readFile(String fileName) {
		File file = new File(fileName);
		String result = "";
		try {
			FileReader fr = new FileReader(file);
			BufferedReader bufr = new BufferedReader(fr);
			result = bufr.readLine();
			bufr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}