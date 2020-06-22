package Client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JPasswordField passwordField;
	private JButton btnLogin;
	
	public Login(Socket socket, InputStream input, OutputStream output)throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(730, 330, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("로그인");
		setVisible(true);

		JLabel label = new JLabel("서울시 상권분석 시스템");
		label.setFont(new Font("HY엽서L", Font.BOLD, 20));
		label.setBounds(50, 35, 350, 60);
		contentPane.add(label);

		JLabel ID_label = new JLabel("ID");
		ID_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		ID_label.setBounds(60, 100, 48, 35);
		contentPane.add(ID_label);

		JLabel Pwd_label = new JLabel("Pwd");
		Pwd_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Pwd_label.setBounds(60, 150, 48, 35);
		contentPane.add(Pwd_label);

		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				passwordField.requestFocus();
			}
		});
		textField.setBounds(120, 100, 180, 35);
		contentPane.add(textField);

		passwordField = new JPasswordField();
		passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		passwordField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnLogin.doClick();
			}
		});
		passwordField.setBounds(120, 150, 180, 35);
		contentPane.add(passwordField);

		btnLogin = new JButton("login");
		btnLogin.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String pwd = passwordField.getText();
				if (id.equals(""))
					JOptionPane.showMessageDialog(null, "ID를 입력하세요.");
				else if (pwd.equals(""))
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
				else {
					try {
						Protocol protocol = new Protocol(Protocol.TYPE3_LOGIN_REQ);
						String data = id + "/" + pwd;
						protocol.setString(data);
						output.write(protocol.getPacket());
						
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];
						int protocolCode = header[1];
						
						if(protocolType == Protocol.TYPE4_LOGIN_RES && protocolCode == Protocol.T4_로그인승인) {
							JOptionPane.showMessageDialog(null, "로그인 성공.");
							MainDisplay mainDisplay = new MainDisplay(socket, input, output);
						}
						else {
							JOptionPane.showMessageDialog(null, "아이디 혹은 비밀번호가 틀렸습니다.");
							return;
						}
					}
					catch(Exception e1) {
						e1.getMessage();
					}
					
				}
			}
		});
		btnLogin.setBounds(312, 100, 116, 35);
		contentPane.add(btnLogin);

		JButton btnSignUp = new JButton("\uD68C\uC6D0\uAC00\uC785");
		btnSignUp.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		btnSignUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					new SignUp(socket, input, output);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSignUp.setBounds(312, 150, 116, 35);
		contentPane.add(btnSignUp);
	}
}

class SignUp extends JFrame {
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;

	SignUp(Socket socket, InputStream input, OutputStream output)throws IOException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(730, 350, 450, 300);
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("회원가입");
		setResizable(false);
		setVisible(true);

		JLabel label = new JLabel("회원가입");
		label.setFont(new Font("HY엽서L", Font.BOLD, 20));
		label.setBounds(50, 35, 350, 60);
		contentPane.add(label);

		JLabel ID_label = new JLabel("ID");
		ID_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		ID_label.setBounds(60, 100, 48, 35);
		contentPane.add(ID_label);

		JLabel Pwd_label = new JLabel("Pwd");
		Pwd_label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		Pwd_label.setBounds(60, 150, 48, 35);
		contentPane.add(Pwd_label);

		JTextField textField = new JTextField();
		textField.setBounds(120, 100, 180, 35);
		contentPane.add(textField);

		JPasswordField passwordField = new JPasswordField();
		passwordField.setBounds(120, 150, 180, 35);
		contentPane.add(passwordField);

		JButton btnRegister = new JButton("\uAC00\uC785");
		btnRegister.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnRegister.setBounds(312, 100, 88, 85);
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String id = textField.getText();
				String pwd = passwordField.getText();
				if (id.equals(""))
					JOptionPane.showMessageDialog(null, "ID를 입력하세요.");
				else if (pwd.equals(""))
					JOptionPane.showMessageDialog(null, "비밀번호를 입력하세요.");
				else {
					try {
						Protocol protocol = new Protocol(Protocol.TYPE1_SIGNUP_REQ);
						String data = id +"/" + pwd;
						protocol.setString(data);
						output.write(protocol.getPacket());
						
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];
						int protocolCode = header[1];
						
						if(protocolType == Protocol.TYPE2_SIGNUP_RES && protocolCode == Protocol.T2_회원가입승인) {
							JOptionPane.showMessageDialog(null, "회원 가입 성공.");
							return;
						}
						else {
							JOptionPane.showMessageDialog(null, "중복된 아이디가 존재합니다.");
							return;
						}
					} catch (Exception e1) {
						System.out.println(e1);
					}
				}
			}
		});
		contentPane.add(btnRegister);

		JButton btnClose = new JButton("닫기");
		btnClose.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(170, 210, 90, 30);
		contentPane.add(btnClose);
	}
}