package Client.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.List;
import java.util.Vector;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTable;
import Client.Protocol;

public class RestaurantSearch extends JPanel {
	JTable table = null;
	Vector rlist = null;
	Vector title = null;
	DefaultTableModel model = null;
	Vector result = null;
	public List<String> arr;
	public Object name = null;
	public Object value1 = null;

	public RestaurantSearch(InputStream input, OutputStream output) {
		setBounds(100, 100, 1000, 800);
		// contentPane = new JPanel();
		setBackground(new Color(233, 235, 232));
		setLayout(null);
		rlist = new Vector<>();
		title = new Vector<>();
		title.add("��ȣ��");
		title.add("��Ǿ����ߺз���");
		title.add("���θ��ּ�");
		model = new DefaultTableModel();
		model.setDataVector(result, title);
		table = new JTable(model);
		table.setBackground(Color.white);
		table.setRowHeight(25);
		table.setFont(new Font("���� ���", Font.PLAIN, 18));
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(44, 206, 858, 454);
		add(scrollpane);

		JLabel �������� = new JLabel("���� ����");
		��������.setFont(new Font("���� ���", Font.PLAIN, 22));
		��������.setHorizontalAlignment(SwingConstants.CENTER);
		��������.setBounds(10, 135, 127, 38);
		add(��������);

		JLabel ����Ư���� = new JLabel("����Ư����");
		����Ư����.setFont(new Font("���� ���", Font.PLAIN, 22));
		����Ư����.setHorizontalAlignment(SwingConstants.CENTER);
		����Ư����.setBounds(128, 135, 139, 38);
		add(����Ư����);

		JComboBox �� = new JComboBox();
		��.setBackground(new Color(233, 235, 232));
		��.setForeground(Color.BLACK);
		��.setFont(new Font("���� ���", Font.PLAIN, 22));
		��.setModel(new DefaultComboBoxModel(new String[] { "�� ����", "������", "������", "���ϱ�", "������", "���Ǳ�", "������", "���α�",
				"��õ��", "�����", "������", "���빮��", "���۱�", "������", "���빮��", "���ʱ�", "������", "���ϱ�", "���ı�", "��õ��", "��������", "��걸",
				"����", "���α�", "�߱�", "�߷���" }));
		��.setBounds(260, 135, 139, 38);
		add(��);

		JComboBox �� = new JComboBox();
		��.setFont(new Font("���� ���", Font.PLAIN, 22));
		��.setBackground(new Color(233, 235, 232));
		��.setForeground(Color.BLACK);
		��.setModel(new DefaultComboBoxModel(new String[] { "�� ����" }));
		��.setBounds(416, 135, 127, 38);
		add(��);

		JButton �˻� = new JButton("�˻�");
		�˻�.setBackground(new Color(188, 188, 188));
		�˻�.setFont(new Font("���� ���", Font.PLAIN, 22));
		�˻�.setBounds(704, 134, 92, 40);
		add(�˻�);

		JLabel lblNewLabel = new JLabel("�� ���� ������ ��ȸ�ϱ�");
		lblNewLabel.setForeground(new Color(57, 59, 58));
		lblNewLabel.setFont(new Font("HY����M", Font.BOLD, 34));
		lblNewLabel.setBounds(0, 40, 978, 60);
		add(lblNewLabel);

		JComboBox �����з� = new JComboBox();
		�����з�.setModel(new DefaultComboBoxModel(
				new String[] { "��ü", "�ѽ�", "�н�", "���", "�߽�", "�Ͻ�/���깰", "�н�ƮǪ��", "Ŀ����/ī��", "��������" }));
		�����з�.setFont(new Font("���� ���", Font.PLAIN, 22));
		�����з�.setBackground(new Color(233, 235, 232));
		�����з�.setBounds(560, 135, 127, 38);
		add(�����з�);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(138, 139, 141));
		lblNewLabel_1.setBounds(0, 115, 978, 5);
		add(lblNewLabel_1);

		JButton �������� = new JButton("���� ����");
		��������.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		��������.setFont(new Font("���� ���", Font.PLAIN, 22));
		��������.setBounds(813, 134, 148, 40);
		��������.setBackground(new Color(188, 188, 188));
		add(��������);

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

				String gu = ��.getSelectedItem().toString();
				String dong = ��.getSelectedItem().toString();
				String sectors = �����з�.getSelectedItem().toString();
				if (!gu.equals("�� ����") && !dong.equals("�� ����") && !sectors.equals("��ü")) {
					try {
						// �Ĵ���ȸ ��û
						Protocol protocol = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_�Ĵ�ǥ��ȸ);
						String data = gu + "!" + dong + "!" + sectors;
						protocol.setString(data);
						output.write(protocol.getPacket());

						// �Ĵ� ��ȸ ����
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];
						int protocolCode = header[1];
						rlist.clear();
						if (protocolType == Protocol.TYPE6_VIEW_RES && protocolCode == Protocol.T6_�Ĵ�ǥ��ȸ����) {

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
						String[] Restaurant = protocol.getString().split("%");
						for (int i = 0; i < Restaurant.length; i++) {
							Vector tmp = new Vector<String>();
							String[] temp = Restaurant[i].split("!");
							tmp.add(temp[0]);
							tmp.add(temp[1]);
							tmp.add(temp[2]);
							rlist.add(tmp);
						}
						result = rlist;
						model.setDataVector(result, title);
						Restaurant = null;

					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					JOptionPane.showMessageDialog(null, "��� ���� �ڽ��� üũ���� �ʾҽ��ϴ�.");
				}
			}
		});
		��������.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Map2();
			}
		});

	}

}

class Map2 extends JFrame {
	public Map2() {
		setTitle("�Ĵ� ��ȸ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 900, 800);
		JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		JButton btnClose = new JButton("�ݱ�");
		btnClose.setBounds(420, 710, 88, 25);
		fxPanel.add(btnClose);
		Platform.runLater(new Runnable() {
			public void run() {
				initAndLoadWebView(fxPanel);
			}
		});
		setVisible(true);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
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

		webEngine.load("http://localhost:8080/Commercial/RestaurantSearch.jsp");
	}
}