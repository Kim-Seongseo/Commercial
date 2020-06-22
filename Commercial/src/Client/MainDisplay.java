package Client;

import java.awt.BorderLayout;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.*;
import Client.GUI.*;

public class MainDisplay extends JFrame {

	public MainDisplay(Socket socket, InputStream input, OutputStream output) {
		// frame 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("서울시 요식업 상권분석");
		CommercialAreaAnalysis commercialAreaAnalysis = new CommercialAreaAnalysis(input, output);
		FloatingPopulation floatingPopulation = new FloatingPopulation(input, output);
		RestaurantSearch restaurantSearch = new RestaurantSearch(input, output);

		JTabbedPane Tab_Admin = new JTabbedPane(JTabbedPane.TOP);
		add(Tab_Admin);
		Tab_Admin.addTab("동네 식당 조회", restaurantSearch);
		Tab_Admin.addTab("유동인구 조회", floatingPopulation);
		Tab_Admin.addTab("상권 분석", commercialAreaAnalysis);
		setSize(1000, 800);
		setVisible(true);
	}
}