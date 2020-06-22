package Client.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import Client.Protocol;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class CommercialAreaAnalysis extends JPanel {

	private JTextField txtDdd;
	private JTextField txtDdd_1;
	private JTextField ���θ�;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Map<String, Double> neighboringIndustries = null;

	public CommercialAreaAnalysis(InputStream input, OutputStream output) {
		setBounds(100, 100, 1000, 800);
		setBackground(new Color(233, 235, 232));
		setBounds(100, 100, 1000, 800);
		setLayout(null);

		JLabel ��Ǻм� = new JLabel("�� ��� �м�");
		��Ǻм�.setForeground(new Color(57, 59, 58));
		��Ǻм�.setFont(new Font("HY����L", Font.BOLD, 34));
		��Ǻм�.setBounds(0, 40, 978, 60);
		add(��Ǻм�);

		JLabel �������� = new JLabel("���� ����");
		��������.setFont(new Font("���� ���", Font.PLAIN, 22));
		��������.setHorizontalAlignment(SwingConstants.CENTER);
		��������.setBounds(77, 135, 139, 38);
		add(��������);

		JLabel ����Ư���� = new JLabel("����Ư����");
		����Ư����.setFont(new Font("���� ���", Font.PLAIN, 22));
		����Ư����.setHorizontalAlignment(SwingConstants.CENTER);
		����Ư����.setBounds(233, 135, 139, 38);
		add(����Ư����);

		JComboBox �� = new JComboBox();
		��.setBackground(new Color(233, 235, 232));
		��.setForeground(Color.BLACK);
		��.setFont(new Font("���� ���", Font.PLAIN, 22));
		��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "������", "���ϱ�", "������", "���Ǳ�", "������", "���α�",
				"��õ��", "�����", "������", "���빮��", "���۱�", "������", "���빮��", "���ʱ�", "������", "���ϱ�", "���ı�", "��õ��", "��������", "��걸",
				"����", "���α�", "�߱�", "�߷���" }));
		��.setBounds(401, 135, 139, 38);
		add(��);

		JComboBox �� = new JComboBox();
		��.setBackground(new Color(233, 235, 232));
		��.setFont(new Font("���� ���", Font.PLAIN, 22));
		��.setForeground(Color.BLACK);
		��.setModel(new DefaultComboBoxModel(new String[] { "�� ����" }));
		��.setBounds(557, 135, 127, 38);
		add(��);

		JButton �˻� = new JButton("�˻�");
		�˻�.setBackground(new Color(188, 188, 188));
		�˻�.setFont(new Font("���� ���", Font.PLAIN, 22));
		�˻�.setBounds(534, 243, 148, 38);
		add(�˻�);

		JLabel label = new JLabel("���� ��ġ");
		label.setFont(new Font("���� ���", Font.PLAIN, 22));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(77, 188, 139, 30);
		add(label);

		JLabel lblNewLabel = new JLabel("����");
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(233, 182, 81, 38);
		add(lblNewLabel);

		JLabel label_1 = new JLabel("�浵");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("���� ���", Font.BOLD, 22));
		label_1.setBounds(442, 185, 81, 38);
		add(label_1);

		txtDdd = new JTextField();
		txtDdd.setFont(new Font("���� ���", Font.PLAIN, 16));
		txtDdd.setBounds(316, 185, 127, 38);
		add(txtDdd);
		txtDdd.setColumns(10);

		txtDdd_1 = new JTextField();
		txtDdd_1.setFont(new Font("���� ���", Font.PLAIN, 16));
		txtDdd_1.setColumns(10);
		txtDdd_1.setBounds(524, 185, 127, 38);
		add(txtDdd_1);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(138, 139, 141));
		lblNewLabel_1.setBounds(0, 115, 978, 5);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("��� ����");
		lblNewLabel_2.setFont(new Font("���� ���", Font.PLAIN, 22));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(77, 247, 139, 30);
		add(lblNewLabel_2);

		JComboBox ������� = new JComboBox();
		�������.setBackground(new Color(233, 235, 232));
		�������.setModel(new DefaultComboBoxModel(
				new String[] { "����", "�ѽ�", "�н�", "���", "�߽�", "�Ͻ�/���깰", "�н�ƮǪ��", "Ŀ����/ī��", "��������" }));
		�������.setFont(new Font("���� ���", Font.PLAIN, 22));
		�������.setBounds(243, 243, 280, 38);
		add(�������);

		JLabel lblNewLabel_3 = new JLabel("�� ���� ���");
		lblNewLabel_3.setFont(new Font("���� ���", Font.BOLD, 34));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setBounds(0, 280, 216, 65);
		add(lblNewLabel_3);

		���θ� = new JTextField();
		���θ�.setText("���θ��Է�");
		���θ�.setFont(new Font("���� ���", Font.PLAIN, 22));
		���θ�.setBounds(701, 135, 166, 38);
		add(���θ�);
		���θ�.setColumns(10);

		JLabel ���ܰ��_�������� = new JLabel("");
		���ܰ��_��������.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_��������.setBounds(50, 360, 950, 30);
		add(���ܰ��_��������);

		JLabel ���ܰ��_�������� = new JLabel("");
		���ܰ��_��������.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_��������.setBounds(50, 410, 950, 30);
		add(���ܰ��_��������);

		JLabel ���ܰ��_�����Ĵ����� = new JLabel("");
		���ܰ��_�����Ĵ�����.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_�����Ĵ�����.setBounds(50, 460, 950, 30);
		add(���ܰ��_�����Ĵ�����);

		JLabel ���ܰ��_�����α� = new JLabel("");
		���ܰ��_�����α�.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_�����α�.setBounds(50, 520, 950, 30);
		add(���ܰ��_�����α�);

		JLabel ���ܰ��_�����α� = new JLabel("");
		���ܰ��_�����α�.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_�����α�.setBounds(50, 570, 950, 30);
		add(���ܰ��_�����α�);

		JLabel ���ܰ��_�������� = new JLabel("");
		���ܰ��_��������.setFont(new Font("���� ���", Font.PLAIN, 20));
		���ܰ��_��������.setBounds(50, 630, 950, 30);
		add(���ܰ��_��������);

		JButton ��ġ���� = new JButton("�������� ����");
		��ġ����.setBackground(new Color(188, 188, 188));
		��ġ����.setFont(new Font("���� ���", Font.PLAIN, 22));
		��ġ����.setBounds(668, 188, 182, 38);
		add(��ġ����);

		JButton ��������ó = new JButton("������ ��ó");
		��������ó.setBackground(new Color(188, 188, 188));
		��������ó.setFont(new Font("���� ���", Font.PLAIN, 22));
		��������ó.setBounds(690, 243, 160, 38);
		add(��������ó);

		��������ó.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new source();
			}
		});

		��.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox idx = (JComboBox) e.getSource(); // �޺��ڽ� �˾Ƴ���
				int index = idx.getSelectedIndex();
				if (index == 1) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "������", "��ġ��", "���", "�Ｚ��", "���",
							"������", "�Ż絿", "�б�����", "���ﵿ", "������", "�Ͽ���", "�ڰ", "û�㵿" }));
				} else if (index == 2) {
					��.setModel(new DefaultComboBoxModel(
							new String[] { "�� ����", "���ϵ�", "�����", "�浿", "���̵�", "���ϵ�", "���ϵ�", "������", "�ϻ絿", "õȣ��" }));
				} else if (index == 3) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "�̾Ƶ�", "����", "������", "���̵�" }));
				} else if (index == 4) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "���絿", "��ȭ��", "���׵�", "���ص�", "���߻굿",
							"���̵�", "���", "��ȭ��", "��â��", "���", "���赿", "�ܹ߻굿", "ȭ�" }));
				} else if (index == 5) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "��õ��", "�Ÿ���" }));
				} else if (index == 6) {
					��.setModel(new DefaultComboBoxModel(
							new String[] { "�� ����", "���嵿", "���ǵ�", "���ڵ�", "�ɵ�", "�ھ絿", "�߰", "ȭ�絿" }));
				} else if (index == 7) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "��������", "������", "��ô��", "���ε�", "�õ�",
							"�ŵ�����", "������", "������", "õ�յ�", "�׵�" }));
				} else if (index == 8) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "���굿", "���굿", "���ﵿ" }));
				} else if (index == 9) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "��赿", "���赿", "�߰赿", "�ϰ赿" }));
				} else if (index == 10) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "���е�", "�ֹ���", "â��" }));
				} else if (index == 11) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "��ʸ���", "�ż���", "��ε�", "�̹���", "��ȵ�",
							"����", "���⵿", "û������", "ȸ�⵿", "�ְ浿" }));
				} else if (index == 12) {
					��.setModel(new DefaultComboBoxModel(
							new String[] { "�� ����", "�뷮����", "��浿", "���۵�", "����", "��絿", "��1��", "�󵵵�", "�Ŵ�浿", "�漮��" }));
				} else if (index == 13) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "������", "���굿", "���ε�", "���ﵿ",
							"��ȭ��", "������", "������", "������", "�����", "��ϵ�", "������", "���굿", "�Ű�����", "�ż���", "������", "������", "������",
							"������", "�밭��", "�ߵ�", "â����", "������", "���ߵ�", "������", "������" }));
				} else if (index == 14) {
					��.setModel(new DefaultComboBoxModel(
							new String[] { "�� ����", "�����µ�", "��õ��", "��ŵ�", "������", "�̱ٵ�", "������", "�ϰ��µ�", "�Ͼ�����", "���̵�",
									"����", "��õ��", "��õ��", "âõ��", "õ����", "������2��", "������3��", "�յ�", "������", "ȫ����", "ȫ����" }));
				} else if (index == 15) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "���", "������", "��赿", "���ʵ�", "�ſ���", "���絿",
							"���", "��鵿", "������", "�����" }));
				} else if (index == 16) {
					��.setModel(new DefaultComboBoxModel(
							new String[] { "�� ����", "��ȣ��1��", "��ȣ��2��", "��ȣ��3��", "��ȣ��4��", "������", "���嵿", "��ٵ�", "��սʸ���",
									"������1��", "������2��", "������", "������", "��䵿", "������", "�Ͽսʸ���", "��絿", "ȫ�͵�" }));
				} else if (index == 17) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "���ϵ�", "������1��", "������2��", "������3��",
							"������4��", "������5��", "���ҹ���1��", "���ҹ���2��", "���ҹ���3��", "���ҹ���4��", "���ҹ���5��", "���ҹ���6��", "���ҹ���7��",
							"������1��", "������2��", "������3��", "������4��", "������5��", "������6��", "������7��", "�Ｑ��1��", "�Ｑ��2��", "�Ｑ��3��",
							"�Ｑ��4��", "�Ｑ��5��", "����", "������", "���ϵ�", "���ϵ�1��", "�Ⱦϵ�1��", "�Ⱦϵ�2��", "�Ⱦϵ�3��", "�Ⱦϵ�4��",
							"�Ⱦϵ�5��", "������", "������", "���ϵ�", "�Ͽ��" }));
				} else if (index == 18) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "�ſ���", "��õ��", "������", "���̵�", "������",
							"���̵�", "���ĵ�", "��õ��", "���ݵ�", "��ǵ�", "������", "ǳ����" }));
				} else if (index == 19) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "��", "�ſ���", "������" }));
				} else if (index == 20) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "��굿", "��굿1��", "��굿2��", "��굿3��",
							"��굿4��", "��굿5��", "��굿6��", "�븲��", "������", "������1��", "������2��", "������3��", "������4��", "������5��",
							"������6��", "�ű浿", "����", "����1��", "����2��", "����3��", "����4��", "����5��", "����6��", "��ȭ��", "���ǵ���",
							"��������", "��������1��", "��������2��", "��������3��", "��������4��", "��������5��", "��������6��", "��������7��", "��������8��" }));
				} else if (index == 21) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "������", "������", "������", "���ڵ�",
							"���赿", "������", "��õ��", "���赿", "������", "�Ű赿", "��â��", "�빮��", "��굿1��", "��굿2��", "��굿3��", "��굿4��",
							"��굿5��", "��굿6��", "��ȿ��1��", "��ȿ��2��", "��ȿ��3��", "��ȿ��4��", "���̵�", "���¿���", "�ּ���", "û�ϵ�", "û�ĵ�1��",
							"û�ĵ�2��", "û�ĵ�3��", "�Ѱ���1��", "�Ѱ���2��", "�Ѱ���3��", "�ѳ���", "ȿâ��", "�ľϵ�" }));
				} else if (index == 22) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "���굿", "������", "������", "�ұ���", "������",
							"�Ż絿", "���̵�", "���ϵ�", "���굿", "������" }));
				} else if (index == 23) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "��ȸ��", "������", "��", "�赿", "����", "������",
							"��ö��", "���Ƶ�", "������", "���ϵ�", "���⵿", "������", "������", "������", "���ڵ�", "����", "���ϵ�", "���ֵ�", "���ŵ�",
							"������", "���1��", "���2��", "���3��", "���4��", "����", "���ǵ�", "���͵�", "�ξϵ�", "�簣��", "������", "��û��",
							"������", "������", "�Ұݵ�", "�ۿ���", "������", "���۵�", "���ε�", "�ű���", "�Ź���1��", "�Ź���2��", "�ſ���", "�ȱ���",
							"���ǵ�", "������", "������", "���ε�", "�ͷ浿", "��ϵ�", "������", "������", "��ȭ��", "�ͼ���", "�λ絿", "���ǵ�", "��絿",
							"�絿", "������", "����1��", "����2��", "����3��", "����4��", "����5��", "����6��", "���е�", "â����", "â�ŵ�", "û�",
							"û����", "ü�ε�", "��ŵ�", "���ǵ�", "���ε�", "���ǵ�", "��", "��â��", "�ʿ", "���̵�", "��ȭ��", "ȫ����", "ȫ�ĵ�",
							"ȭ��", "ȿ�ڵ�", "ȿ����", "������" }));
				} else if (index == 24) {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "����1��", "����2��", "���빮��1��", "���빮��2��",
							"���빮��3��", "���빮��4��", "���빮��5��", "���굿1��", "���굿2��", "���굿3��", "��â��", "���е�", "�ٵ�", "������1��",
							"������2��", "��1��", "��2��", "������", "���е�", "������", "��굿", "������1��", "������2��", "��â��", "�긲��", "�ﰢ��",
							"���ҹ���", "�Ұ���", "��ǥ��", "���ϵ�", "��ȭ��", "�Ŵ絿", "�ָ���", "������", "���嵿", "���嵿", "������1��", "������2��",
							"������3��", "������4��", "������5��", "������6��", "������7��", "���ַ�1��", "���ַ�2��", "������1��", "������2��", "������",
							"�屳��", "���浿1��", "���浿2��", "����1��", "����2��", "����", "�ֱ���", "���ڵ�", "�߸���", "�ʵ�", "�湫��1��", "�湫��2��",
							"�湫��3��", "�湫��4��", "�湫��5��", "������1��", "�����1��", "�����2��", "�ʵ�1��", "�ʵ�2��", "�ʵ�3��", "Ȳ�е�",
							"ȸ����1��", "ȸ����2��", "ȸ����3��", "���ε�" }));
				} else if (index == 25) {
					��.setModel(
							new DefaultComboBoxModel(new String[] { "�� ����", "���쵿", "���", "����", "�����", "�ų���", "��ȭ��" }));
				} else {
					��.setModel(new DefaultComboBoxModel(new String[] { "�� ����" }));
				}
			}
		});
		�˻�.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!��.getSelectedItem().toString().equals("�� ����") && !��.getSelectedItem().toString().equals("�� ����")
							&& !���θ�.getText().equals("���θ��Է�") && txtDdd.getText() != null
							&& txtDdd_1.getText() != null && !�������.getSelectedItem().toString().equals("����")) {

						// ��Ǻм� ��û
						Protocol protocol = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_��Ǻм������ȸ);
						String data = ��.getSelectedItem().toString() + "!" + ��.getSelectedItem().toString() + "!"
								+ ���θ�.getText() + "!" + txtDdd.getText() + "!" + txtDdd_1.getText() + "!"
								+ �������.getSelectedItem().toString();
						protocol.setString(data);
						output.write(protocol.getPacket());

						// ��� ��ȯ
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];

						int protocolCode = header[1];

						int bodylength = protocol.byteToInt(header, 2);
						if (bodylength != 0) {

							byte[] body = new byte[bodylength];

							input.read(body);

							protocol.setPacket(header, body);

						}
						System.out.println("����");
						if (protocolType == Protocol.TYPE6_VIEW_RES && protocolCode == Protocol.T6_��Ǻм������ȸ����) {
							System.out.println("����");
							String[] Commercial = protocol.getString().split("%");
							// �α� ���� ����Ʈ
							neighboringIndustries = new HashMap<String, Double>();
							neighboringIndustries.clear();
							String[] neighbor = Commercial[0].split("/");
							String range = neighbor[neighbor.length - 1];
							for (int i = 0; i < Integer.parseInt(range); i += 2) {

								neighboringIndustries.put(neighbor[i], Double.parseDouble(neighbor[i + 1]));
							}
							for (String key : neighboringIndustries.keySet()) {

								Double value = neighboringIndustries.get(key);

							}
							String[] ShortNeighbor = Commercial[1].split("/");
							double distance = Double.parseDouble(ShortNeighbor[0]);
							String neighborhoodStore = ShortNeighbor[1];
							if (distance == 9999.0 || neighborhoodStore == null) {
								���ܰ��_�����Ĵ�����.setText("�����Ͻ� ��ġ�� ���� ���� ������ 0�̹Ƿ� ������ �� �����ϴ�.");
							} else {
								���ܰ��_�����Ĵ�����.setText("���� ��ġ�� ���� ����� �Ĵ�: " + neighborhoodStore + " / " + distance + "m");
							}

							int sectorsStoreCount = Integer.parseInt(Commercial[2]);
							���ܰ��_��������.setText("������ ������ ���� ���� ���� ��: " + sectorsStoreCount + "��");
							long sales = Integer.parseInt(Commercial[3]);
							���ܰ��_��������.setText("������ ������ ���� ���� ����� ����: " + String.format("%,d", sales) + "��");
							String[] FloatingPopulation = Commercial[4].split("/");

							int total = Integer.parseInt(FloatingPopulation[0]);
							String age = FloatingPopulation[1];
							String time = FloatingPopulation[2];

							���ܰ��_�����α�.setText("�� " + String.format("%,d", total) + "��, " + age + "�� ���� ������ " + time
									+ "�� �����α��� ����.");
							String[] ResidentPopulation = Commercial[5].split("/");
							int ResidentTotal = Integer.parseInt(ResidentPopulation[0]);
							String ResidentAge = ResidentPopulation[1];
							���ܰ��_�����α�.setText(
									"�� " + String.format("%,d", ResidentTotal) + "��, " + ResidentAge + "�� ���� ���� �����Ѵ�.");

							String[] BusinessIndex = Commercial[6].split("/");
							double percent = Double.parseDouble(BusinessIndex[0]);
							sales = Long.parseLong(BusinessIndex[1]);
							int businessIndex = Integer.parseInt(BusinessIndex[2]);
							���ܰ��_��������.setText("�α����: " + (int) Math.round(percent) + "��� / ������: " + sales
									+ "��� / ��������: " + String.format("%,d", businessIndex));

							JButton �αٰ���_����Ʈ = new JButton("�αٰ��� ��� ����");
							�αٰ���_����Ʈ.setFont(new Font("���� ���", Font.PLAIN, 22));
							�αٰ���_����Ʈ.setBackground(new Color(188, 188, 188));
							�αٰ���_����Ʈ.setBounds(223, 296, 249, 38);
							add(�αٰ���_����Ʈ);
							�αٰ���_����Ʈ.addActionListener(new ActionListener() {
								@Override
								public void actionPerformed(ActionEvent e) {
									new list(neighboringIndustries);
								}
							});

						} else {
							String[] Commercial = protocol.getString().split("%");
							JOptionPane.showMessageDialog(null, Commercial[0]);
						}
					} else {
						JOptionPane.showMessageDialog(null, "��� ���� �ڽ��� üũ���� �ʾҽ��ϴ�.");
					}
				} catch (Exception message) {
					return;
				}
			}
		});
		��ġ����.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Map3();
			}
		});
	}
}

class list extends JFrame {

	list(Map<String, Double> neighboringIndustries) {
		setBounds(100, 100, 700, 431);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);

		JLabel label = new JLabel("�� �α� ���� ����Ʈ");
		label.setFont(new Font("���� ���", Font.BOLD, 24));
		label.setBounds(29, 33, 255, 33);
		getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("���� ���", Font.PLAIN, 22));
		lblNewLabel.setBounds(56, 81, 529, 40);
		getContentPane().add(lblNewLabel);

		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("���� ���", Font.PLAIN, 22));
		label_1.setBounds(56, 122, 529, 40);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("���� ���", Font.PLAIN, 22));
		label_2.setBounds(56, 164, 529, 40);
		getContentPane().add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setFont(new Font("���� ���", Font.PLAIN, 22));
		label_3.setBounds(56, 205, 529, 40);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setFont(new Font("���� ���", Font.PLAIN, 22));
		label_4.setBounds(56, 246, 529, 40);
		getContentPane().add(label_4);

		JButton �ݱ� = new JButton("�ݱ�");
		�ݱ�.setBackground(new Color(188, 188, 188));
		�ݱ�.setFont(new Font("���� ���", Font.BOLD, 22));
		�ݱ�.setBounds(456, 39, 129, 29);
		getContentPane().add(�ݱ�);

		// �α� ���� ����Ʈ �� �������� ����
		List<String> keySetListneighboringIndustries = new ArrayList<>(neighboringIndustries.keySet());
		Collections.sort(keySetListneighboringIndustries,
				(o1, o2) -> (neighboringIndustries.get(o1).compareTo(neighboringIndustries.get(o2))));
		// String[][] arr;
		int i = 1;
		for (String key : keySetListneighboringIndustries) {
			if (i == 1) {
				lblNewLabel.setText(key + " / " + Math.round(neighboringIndustries.get(key) * 100) / 100.0 + "m");
			} else if (i == 2) {
				label_1.setText(key + " / " + Math.round(neighboringIndustries.get(key) * 100) / 100.0 + "m");
			} else if (i == 3) {
				label_2.setText(key + " / " + Math.round(neighboringIndustries.get(key) * 100) / 100.0 + "m");
			} else if (i == 4) {
				label_3.setText(key + " / " + Math.round(neighboringIndustries.get(key) * 100) / 100.0 + "m");
			} else {
				label_4.setText(key + " / " + Math.round(neighboringIndustries.get(key) * 100) / 100.0 + "m");
			}
			i++;
		}

		�ݱ�.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}

class source extends JFrame {

	source() {
		setBounds(100, 100, 620, 412);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		setVisible(true);

		JLabel lblNewLabel = new JLabel("�� ������ ��ó");
		lblNewLabel.setFont(new Font("���� ���", Font.BOLD, 26));
		lblNewLabel.setBounds(37, 47, 198, 36);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("�� ���� ���� - �ش� ��ġ�� ī�� ���� ���� ������");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(0, 145, 598, 42);
		getContentPane().add(lblNewLabel_1);

		JLabel label = new JLabel("���� ���������� ����");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("���� ���", Font.PLAIN, 20));
		label.setBounds(0, 98, 598, 42);
		getContentPane().add(label);

		JLabel lblKipris = new JLabel("Kipris");
		lblKipris.setHorizontalAlignment(SwingConstants.CENTER);
		lblKipris.setFont(new Font("���� ���", Font.PLAIN, 20));
		lblKipris.setBounds(0, 191, 598, 42);
		getContentPane().add(lblKipris);

		JLabel label_1 = new JLabel("�� �һ���� ������ �������յ� �� ������ ��� �ý��� �� ���");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("���� ���", Font.PLAIN, 20));
		label_1.setBounds(0, 235, 598, 42);
		getContentPane().add(label_1);

		JButton �ݱ� = new JButton("�ݱ�");
		�ݱ�.setBackground(new Color(188, 188, 188));
		�ݱ�.setBounds(243, 312, 129, 29);
		getContentPane().add(�ݱ�);
		�ݱ�.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}

class Map3 extends JFrame {
	public Map3() {
		setTitle("��ġ ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 900, 800);
		JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		JButton btnClose = new JButton("�ݱ�");
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnClose.setBounds(420, 710, 88, 25);
		fxPanel.add(btnClose);

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

		webEngine.load("http://localhost:8080/Commercial/Analysis.jsp");
	}
}