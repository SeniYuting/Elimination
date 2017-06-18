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

	// TODO 新增1：声音的范围在0～2.0【不要问我为什么我也不知道233
	double volume = 2.0;
	// TODO 新增2：用于控制声音的浮点值范围
	FloatControl floatControl = null;
	
	String filePath = null; // 文件路径
	boolean loop = true; // 是否循环
	AudioInputStream audioInputStream = null;// 音频输入流, 与FileInputStream类似
	SourceDataLine sourceDataLine = null;

	/**
	 * 构造函数，初始化音乐的路径
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
	 * 设置是否循环播放
	 * @param loop
	 */
	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	/**
	 * implements Runnable 后需要实现的方法<br>
	 * 在主程序调用的时候，会自动从这个方法开始。
	 */
	@Override
	public void run() {
		do { // 做个循环yeah【因为似乎没在api里找到自带的loop方法【大概是没看到233
			try {// 至于这个try和catch，就是尝试着做那些方法，
			    // 如果遇到了错误就会执行catch的方法并中断程序
				init();
				play();
			} catch (Exception e) {
				e.printStackTrace();//这个是输出错误信息的代码……不用怎么在意233
			}
		} while (loop);
	}
	
	/**
	 * 初始化音乐的相关数据，其实代码几乎都是固定的<br>
	 * 至于throws Exception 就是把遇到的错误丢到上一层【详细见run的try{}catch{}<br>
	 * 在读取文件的时候需要这一throws
	 */
	public void init() throws Exception {
		// 1、将文件转换为音频输入流
		audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
		// 2、获取这个文件的音频格式
		AudioFormat audioFormat = audioInputStream.getFormat();
		// mp3转码
		// 这里判断audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED
		// 是因为MP3格式作为有损格式它丢掉了脉冲编码调制（PCM）的音频数据，所以PCM_SIGNED或PCM_SIGNED都可以
		if (filePath.endsWith(".mp3")
				& audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
			// 新建一个规定的音频格式
			audioFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED,// 编码格式
					audioFormat.getSampleRate(), // 原来的波形采样率
					16,// 16位双字节采集
					audioFormat.getChannels(),// 原来的声道数
			//  下面这个是每帧中的字节数，每声道每帧字节数2字节，再加上双声道所以用声道数（2）*字节数（2）
					audioFormat.getChannels() * 2,
					audioFormat.getSampleRate(),  // 每秒的帧数
					false);// ………………
			// 将当前的音频流转换到指定的格式
			audioInputStream = AudioSystem.getAudioInputStream(audioFormat, audioInputStream);
		}
		// 3、设置音频行的信息
		Info info = new Info(SourceDataLine.class, audioFormat);
		// 4、似乎是建立音频行【已经开始到底层的实现了我不行了orz
		sourceDataLine = (SourceDataLine) AudioSystem.getLine(info);
		sourceDataLine.open(audioFormat);// 将音频数据流读入音频行
		sourceDataLine.start();
		// TODO 初始化FloatControl， FloatControl.Type.MASTER_GAIN是某一行上总音量的控件
		floatControl = (FloatControl) sourceDataLine.
				getControl(FloatControl.Type.MASTER_GAIN);
		// TODO 设置为默认的音量大小
		setVolume(volume, true);
	}
	
	/**
	 * 播放音频文件
	 */
	public void play() {
		try {
			int nByte = 0;
			final int SIZE = 1024 * 64;
			byte[] buffer = new byte[SIZE];
			while (nByte != -1) {
				// 将音频数据写入混频器……好吧我已经不行了233
				sourceDataLine.write(buffer, 0, nByte);
				nByte = audioInputStream.read(buffer, 0, SIZE);
			}
			// 结束后停止
			sourceDataLine.stop();
			// 清空缓冲区【其实应该没什么必要
			sourceDataLine.flush();
			sourceDataLine.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// TODO 是的只要加上TODO就可以在右边看到蓝白条纹了【不
	/**
	 * 设置声音的大小
	 * 
	 * @param newVolume
	 * <br />
	 *                如果直接输入电压值, 范围为0.0 ~ 2.0<br />
	 *                如果输入为百分比, 范围为0.0 ~ 100.0<br />
	 * @param isPercent
	 * <br />
	 *                如果输入为百分比, 则此处为true
	 * @see FloatControl.Type.MASTER_GAIN
	 */
	public void setVolume(Double newVolume, boolean isPercent) {
		if (isPercent) {
			// 将百分比转化到0.0~2.0
			volume = newVolume / 50.0;
		}
		// 这里有个分贝的计算公式 dB = 20lg(E/Er) E-实际测量电压值，Er-参考电压值
		// 所输入的newVolume如果表示电压值则为E/Er
		// 详细见FloatControl.Type.MASTER_GAIN的API文档……太多字了orz
		float dB = (float) (Math.log(volume == 0.0 ? 0.0001 : volume)
				/ Math.log(10.0) * 20.0);
		floatControl.setValue(dB);
		this.volume=volume*50.0;
	}
	
	// 测试
	public static void main(String[] args) {
//		PS：eclipse下ctrl+/可以直接增减注释嗯
		final Music musicPlay = new Music("select");
//		VolChangeAudioSample.setLoop(false);

//		主要就是写一个JSlider，然后拖动就调用VolChangeAudioSample.setVolume……
		JFrame jFrame = new JFrame();
		jFrame.setSize(100, 60);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击红叉退出
		jFrame.setResizable(false);// 不可改变大小
		jFrame.setLocationRelativeTo(null);// 界面居中【需要在setSize之后

		//slider我就不写具体了……能拖动就行
		// 					最小值0最大值100，默认为80
		final JSlider slider1 = new JSlider(JSlider.HORIZONTAL,0,100,80);
		// 加监听……【我喜欢用这种匿名内部类233【不解释了用着就是了233
		slider1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				musicPlay.setVolume((double)slider1.getValue(), true);
			}
		});
		jFrame.add(slider1);
		jFrame.setVisible(true);
		
//		下面这句就是新建一个线程，其实就是：
//		Thread aThread = new Thread(musicPlay);
//		aThread.start();
		new Thread(musicPlay).start();
//		注意这里用start()方法才能实现多线程，然后系统自动会调用run()方法
//		如果写的是new Thread(musicPlay).run();则是普通的函数调用
		
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