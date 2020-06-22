package Client;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;
import Client.GUI.*;

public class MainDisplay extends JFrame {

	public MainDisplay(Socket socket, InputStream input, OutputStream output) {
		// frame ����
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("����� ��ľ� ��Ǻм�");
		CommercialAreaAnalysis commercialAreaAnalysis = new CommercialAreaAnalysis(input, output);
		FloatingPopulation floatingPopulation = new FloatingPopulation(input, output);
		RestaurantSearch restaurantSearch = new RestaurantSearch(input, output);

		JTabbedPane Tab_Admin = new JTabbedPane(JTabbedPane.TOP);
		add(Tab_Admin);
		Tab_Admin.addTab("���� �Ĵ� ��ȸ", restaurantSearch);
		Tab_Admin.addTab("�����α� ��ȸ", floatingPopulation);
		Tab_Admin.addTab("��� �м�", commercialAreaAnalysis);
		setSize(1000, 800);
		setVisible(true);
	}
}