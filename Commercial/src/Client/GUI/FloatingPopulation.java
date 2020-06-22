package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;

import Client.Protocol;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javax.swing.JTextField;

public class FloatingPopulation extends JPanel {
	JTable table = null, table2 = null, table3 = null;
	Vector rlist = null, rlist2 = null, rlist3 = null;
	Vector title = null, title2 = null, title3 = null;
	DefaultTableModel model = null, model2 = null, model3 = null;
	Vector result = null, result2 = null, result3 = null;

	public FloatingPopulation(InputStream input, OutputStream output) {
		rlist = new Vector<>();
		title = new Vector<>();
		title.add("����");
		title.add("����");
		model = new DefaultTableModel();
		model.setDataVector(result, title);
		table = new JTable(model);
		table.setBackground(Color.white);
		table.setRowHeight(25);
		table.setFont(new Font("���� ���", Font.PLAIN, 18));

		rlist2 = new Vector<>();
		title2 = new Vector<>();
		title2.add("10��");
		title2.add("20��");
		title2.add("30��");
		title2.add("40��");
		title2.add("50��");
		model2 = new DefaultTableModel();
		model2.setDataVector(result2, title2);
		table2 = new JTable(model2);
		table2.setBackground(Color.white);
		table2.setRowHeight(25);
		table2.setFont(new Font("���� ���", Font.PLAIN, 18));

		rlist3 = new Vector<>();
		title3 = new Vector<>();
		title3.add("11�� ~ 14��");
		title3.add("14�� ~ 17��");
		title3.add("17�� ~ 21��");
		title3.add("21�� ~ 24��");
		model3 = new DefaultTableModel();
		model3.setDataVector(result3, title3);
		table3 = new JTable(model3);
		table3.setBackground(Color.white);
		table3.setRowHeight(25);
		table3.setFont(new Font("���� ���", Font.PLAIN, 18));

		setBounds(100, 100, 1000, 800);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		setLayout(null);
		JLabel ��Ǻм� = new JLabel("�� �����α� �м�");
		��Ǻм�.setForeground(new Color(57, 59, 58));
		��Ǻм�.setFont(new Font("HY����M", Font.BOLD, 34));
		��Ǻм�.setBounds(0, 40, 978, 60);
		add(��Ǻм�);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(138, 139, 141));
		lblNewLabel_1.setBounds(0, 115, 978, 5);
		add(lblNewLabel_1);

		JLabel ���θ��Է� = new JLabel("\uB3C4\uB85C\uBA85 \uC8FC\uC18C \uC785\uB825");
		���θ��Է�.setFont(new Font("���� ���", Font.PLAIN, 22));
		���θ��Է�.setHorizontalAlignment(SwingConstants.CENTER);
		���θ��Է�.setBounds(10, 135, 180, 38);
		add(���θ��Է�);

		JButton ���� = new JButton("\uC9C0\uB3C4 \uBCF4\uAE30");
		����.setBackground(new Color(188, 188, 188));
		����.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Map();
			}
		});
		����.setFont(new Font("���� ���", Font.PLAIN, 22));
		����.setBounds(432, 135, 130, 38);
		add(����);

		JButton ǥ = new JButton("\uD45C\uB85C \uBCF4\uAE30");
		ǥ.setFont(new Font("���� ���", Font.PLAIN, 22));
		ǥ.setBackground(new Color(188, 188, 188));
		ǥ.setBounds(574, 135, 130, 38);
		add(ǥ);

		JTextField textField = new JTextField();
		textField.setFont(new Font("���� ���", Font.PLAIN, 18));
		textField.setBounds(200, 135, 220, 38);
		add(textField);
		
		JLabel lblNewLabel = new JLabel("\uC131\uBCC4");
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 18));
		lblNewLabel.setBounds(50, 220, 70, 30);
		add(lblNewLabel);

		JLabel lblNewLabel_2 = new JLabel("\uB098\uC774\uB300");
		lblNewLabel_2.setFont(new Font("���� ���", Font.PLAIN, 18));
		lblNewLabel_2.setBounds(50, 370, 70, 30);
		add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\uC2DC\uAC04\uB300");
		lblNewLabel_3.setFont(new Font("���� ���", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(50, 520, 70, 30);
		add(lblNewLabel_3);
		
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 250, 220, 50);
		add(scrollPane);
		
		JScrollPane scrollPane2 = new JScrollPane(table2);
		scrollPane2.setBounds(50, 400, 823, 50);
		add(scrollPane2);
		
		JScrollPane scrollPane3 = new JScrollPane(table3);
		scrollPane3.setBounds(50, 550, 823, 50);
		add(scrollPane3);

		ǥ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String address = textField.getText();

				if (!address.equals("")) {
					try {
						// �����α� ��ȸ ��û
						Protocol protocol = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_�����α�ǥ��ȸ);
						protocol.setString(address);
						output.write(protocol.getPacket());

						// �����α� ��ȸ ����
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];
						int protocolCode = header[1];
						if (protocolType == Protocol.TYPE6_VIEW_RES && protocolCode == Protocol.T6_�����α�ǥ��ȸ����) {

							int bodylength = protocol.byteToInt(header, 2);
							if (bodylength != 0) {
								byte[] body = new byte[bodylength];
								input.read(body);
								protocol.setPacket(header, body);
							}

						} else {
							JOptionPane.showMessageDialog(null, "����ó�� �߻�");
							return;
						}

						rlist.clear();
						rlist2.clear();
						rlist3.clear();
						Vector<String> tmp = new Vector<String>();
						Vector<String> tmp2 = new Vector<String>();
						Vector<String> tmp3 = new Vector<String>();
						String[] population = protocol.getString().split("#");
						if(population.length == 0) {
							JOptionPane.showMessageDialog(null, "�Է��� ���θ� ���� ������ �����ϴ�.");
							return;
						}
						String[] gender = population[0].split("!");
						String[] age = population[1].split("!");
						String[] time = population[2].split("!");
						for (int i = 0; i < gender.length; i++) {
							tmp.add(gender[i]);
						}
						for (int i = 0; i < age.length; i++) {
							tmp2.add(age[i]);
						}
						for (int i = 0; i < time.length; i++) {
							tmp3.add(time[i]);
						}
						rlist.add(tmp);
						rlist2.add(tmp2);
						rlist3.add(tmp3);
						result = rlist;
						result2 = rlist2;
						result3 = rlist3;
						model.setDataVector(result, title);
						model2.setDataVector(result2, title2);
						model3.setDataVector(result3, title3);
						population = null;

						// DefaultTableCellHeaderRenderer ���� (��� ������ ����)
						DefaultTableCellRenderer tScheduleCellRenderer = new DefaultTableCellRenderer();
						// DefaultTableCellHeaderRenderer�� ������ ��� ���ķ� ����
						tScheduleCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
						// ������ ���̺��� ColumnModel�� ������
						TableColumnModel tcmSchedule = table.getColumnModel();
						TableColumnModel tcmSchedule2 = table2.getColumnModel();
						TableColumnModel tcmSchedule3 = table3.getColumnModel();
						// �ݺ����� �̿��Ͽ� ���̺��� ��� ���ķ� ����
						for (int i = 0; i < tcmSchedule.getColumnCount(); i++) {
							tcmSchedule.getColumn(i).setCellRenderer(tScheduleCellRenderer);
						}
						for (int i = 0; i < tcmSchedule2.getColumnCount(); i++) {
							tcmSchedule2.getColumn(i).setCellRenderer(tScheduleCellRenderer);
						}
						for (int i = 0; i < tcmSchedule3.getColumnCount(); i++) {
							tcmSchedule3.getColumn(i).setCellRenderer(tScheduleCellRenderer);
						}

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "���θ� ������ �Է��ϼ���");
				}
			}
		});
	}
}

class Map extends JFrame {
	public Map() {
		setTitle("�Ĵ� ��ȸ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 900, 800);
		JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		JButton asdf = new JButton("�ݱ�");
		asdf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		asdf.setBounds(420, 710, 88, 25);
		fxPanel.add(asdf);

		Platform.runLater(new Runnable() {
			public void run() {
				initAndLoadWebView(fxPanel);
			}
		});
		setVisible(true);
	}

	private static void initAndLoadWebView(final JFXPanel fxPanel) {
		Group group = new Group();
		Scene scene = new Scene(group);
		fxPanel.setScene(scene);

		WebView webView = new WebView();

		group.getChildren().add(webView);
		webView.setMinSize(1000, 700);
		webView.setMaxSize(1000, 700);

		WebEngine webEngine = webView.getEngine();

		webEngine.load("http://localhost:8080/Commercial/FloatingPopulation.jsp");
	}
}