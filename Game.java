package baseball;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

class mPanel extends JPanel implements ActionListener {
	
	Random ran;
	JTextArea display, mod, mod2;
	JTextField input;
	TitledBorder tb;
	JButton btn, reset, hint;
	JScrollPane sp;
	
	int[] ansNum = new int[3];
	int[] inpNum = new int[3];
	
	int strike;
	int ball;
	int out;
	
	int life = 10;
	
	int hintNum;
	int hintCount = 3;
	
	Font normalFont = new Font(null, Font.PLAIN, 27);
	Font smallFont = new Font(null, Font.PLAIN, 20);
	
	String[] finish = new String[5];
	
	public mPanel() {
		this.setLayout(null);
		setDefault();
		genNum();
	}
	
	public void setDefault() {
		
		finish[0] = "힌트1";
		finish[1] = "힌트2";
		finish[2] = "힌트3";
		finish[3] = "힌트4";
		finish[4] = "힌트5";
		
		reset = new JButton("리셋");
		reset.setSize(150, 65);
		reset.setLocation(10, 10);
		reset.addActionListener(this);
		add(reset);
		
		mod2 = new JTextArea();
		mod2.setBounds(161,10, 120, 65);
		mod2.setFont(normalFont);
		mod2.setText("Life : " + life);
		add(mod2);
		
		hint = new JButton("힌트: " + hintCount);
		hint.setSize(300, 65);
		hint.setLocation(400, 10);
		hint.setFont(smallFont);
		hint.addActionListener(this);
		add(hint);
		
		
		display = new JTextArea();
		display.setBounds(10, 80, 760, 400);
		display.setBackground(Color.white);
		display.setEditable(false);
		tb = new TitledBorder(new LineBorder(Color.blue), "Text Area");
		display.setBorder(tb);
		display.setFont(smallFont);
		display.setText("게임을 시작합니다\n"
				+ "---------------------------------------------------------\n"
				+ "1. 숫자 111 ~ 999 사이의 숫자 입력\n"
				+ "2. 예) 정답이 123\n"
				+ "현아가 입력을 133 하면 2스트라이크 1볼 이됨\n"
				+ "현아가 입력을 444 하면 3아웃이됨\n"
				+ "현아가 입력을 231 하면 3볼 이됨\n"
				+ "같은 숫자 위치 정답을 맞추면 스트라이크\n"
				+ "같은 숫자 위치는 아니지만 다른 숫자 위치에 같은 숫자를 입력하면 볼\n"
				+ "입력한 숫자가 해당 정답과 관계가 없으면 아웃\n"
				+ "---------------------------------------------------------\n");
		sp = new JScrollPane(display);
		sp.setBounds(10, 80, 760, 400);
		add(sp);
		
		mod = new JTextArea();
		mod.setSize(160, 50);
		mod.setLocation(10, 480);
		mod.setBackground(Color.black);
		mod.setForeground(Color.white);
		mod.setFont(normalFont);
		mod.append("정수: 1 ~ 10");
		add(mod);
		
		input = new JTextField();
		input.setSize(500, 50);
		input.setLocation(170, 480);
		input.setBackground(Color.white);
		
		btn = new JButton("입력");
		btn.setSize(100, 50);
		btn.setLocation(670, 480);
		btn.addActionListener(this);
		add(btn);
		add(input);
	}
	
	public void genNum() {
		ran = new Random();
		int i = 0;
		while(i < 3) {
			int ranNum = ran.nextInt(9) + 1;
			int check = 1;
			int j = 0;
			
			while(j < i) {
				if(ranNum == ansNum[j]) {
					check -= 1;
				}
				j += 1;
			}
			
			if(check == 1) {
				ansNum[i] = ranNum;
				i += 1;
			}
		}
		int ranNum = ran.nextInt(3);
		hintNum = ansNum[ranNum];
		System.out.println(ansNum[0] +""+ ansNum[1] +""+ ansNum[2]);
	}
	
	public void check() {
		strike = 0;
		ball = 0;
		out = 0;
		for(int i = 0; i < ansNum.length; i++) {
			if(inpNum[i] == ansNum[i]) {
				strike += 1;
			}else {
				int cnt = 0;
				for(int j = 0; j < ansNum.length; j++) {
					if(inpNum[i] == ansNum[j]) {
						ball += 1;
						cnt += 1;
						j = ansNum.length;
					}
				}
				if(cnt == 0) {
					out += 1;
				}
			}
		}
		display.append("Strike: " + strike + " Ball: " + ball + " Out: " + out + " 현아 입력: " + inpNum[0] + "" + inpNum[1] + "" + inpNum[2] + "\n");
		if(strike == 3) {
			ran = new Random();
			int ranNum = ran.nextInt(5);
			display.append(finish[ranNum]);
		}
	}
	public void reset() {
		genNum();
		display.setText(null);
		display.setText("새로운 게임\n");
		life = 10;
		hintCount = 3;
		mod2.setText("Life : " + life);
		hint.setText(" 힌트: " + hintCount);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == hint) {
			if(hintCount <= 0) {
				display.append("힌트 모두 썼잖옹 ㅠㅠ\n");
			}else {
				ran = new Random();
				int hintRan = ran.nextInt(3) + 1;
				if(hintRan == 1) {
					display.append("숫자 한개는: " + hintNum + "\n");
					hintCount -= 1;
					hint.setText(" 힌트: " + hintCount);
				}else if(hintRan == 2) {
					life += 1;
					mod2.setText("Life : " + life);
					display.append("life +1\n");
					hintCount -= 1;
					hint.setText(" 힌트: " + hintCount);
				}else if(hintRan == 3) {
					display.append("없음 \n");
					hintCount -= 1;
					hint.setText(" 힌트: " + hintCount);
				}
			}
		}else if(e.getSource() == reset) {
			reset();
		}else if(e.getSource() == btn){
			if(life == 0) {
				mod2.setText("Life : " + life);
				display.setText("까비 \n"
						+ "리셋 버튼을 눌러주세요\n");
			}else {
				try {
					int number = Integer.parseInt(input.getText());
					if(number < 111 || number > 999) {
						display.append("111 ~ 999 사이 정수를 입력해 주세요\n");
					}else {
						inpNum[0] = number / 100;
						inpNum[1] = (number % 100) / 10;
						inpNum[2] = ((number % 100) % 10) / 1;
						check();
						life -= 1;
						mod2.setText("Life : " + life);
					}
				}catch(Exception error) {
					display.append("정수를 입력해 주세요\n");
				}
			}
		}
	}
	
}

public class Game {

	public static void main(String[] args) {
		
		JFrame frame = new JFrame();
		frame.setSize(800,600);
		frame.setTitle("숫자 야구 게임");
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		mPanel p = new mPanel();
		frame.setContentPane(p);
		frame.revalidate();
		
	}

}
