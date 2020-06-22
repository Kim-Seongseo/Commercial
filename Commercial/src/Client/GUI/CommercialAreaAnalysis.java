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
	private JTextField 도로명;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs = null;
	Map<String, Double> neighboringIndustries = null;

	public CommercialAreaAnalysis(InputStream input, OutputStream output) {
		setBounds(100, 100, 1000, 800);
		setBackground(new Color(233, 235, 232));
		setBounds(100, 100, 1000, 800);
		setLayout(null);

		JLabel 상권분석 = new JLabel("▶ 상권 분석");
		상권분석.setForeground(new Color(57, 59, 58));
		상권분석.setFont(new Font("HY엽서L", Font.BOLD, 34));
		상권분석.setBounds(0, 40, 978, 60);
		add(상권분석);

		JLabel 지역선택 = new JLabel("지역 선택");
		지역선택.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		지역선택.setHorizontalAlignment(SwingConstants.CENTER);
		지역선택.setBounds(77, 135, 139, 38);
		add(지역선택);

		JLabel 서울특별시 = new JLabel("서울특별시");
		서울특별시.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		서울특별시.setHorizontalAlignment(SwingConstants.CENTER);
		서울특별시.setBounds(233, 135, 139, 38);
		add(서울특별시);

		JComboBox 구 = new JComboBox();
		구.setBackground(new Color(233, 235, 232));
		구.setForeground(Color.BLACK);
		구.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		구.setModel(new DefaultComboBoxModel(new String[] { "구 선택", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구",
				"금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구",
				"은평구", "종로구", "중구", "중량구" }));
		구.setBounds(401, 135, 139, 38);
		add(구);

		JComboBox 동 = new JComboBox();
		동.setBackground(new Color(233, 235, 232));
		동.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		동.setForeground(Color.BLACK);
		동.setModel(new DefaultComboBoxModel(new String[] { "동 선택" }));
		동.setBounds(557, 135, 127, 38);
		add(동);

		JButton 검색 = new JButton("검색");
		검색.setBackground(new Color(188, 188, 188));
		검색.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		검색.setBounds(534, 243, 148, 38);
		add(검색);

		JLabel label = new JLabel("예상 위치");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(77, 188, 139, 30);
		add(label);

		JLabel lblNewLabel = new JLabel("위도");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(233, 182, 81, 38);
		add(lblNewLabel);

		JLabel label_1 = new JLabel("경도");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label_1.setBounds(442, 185, 81, 38);
		add(label_1);

		txtDdd = new JTextField();
		txtDdd.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		txtDdd.setBounds(316, 185, 127, 38);
		add(txtDdd);
		txtDdd.setColumns(10);

		txtDdd_1 = new JTextField();
		txtDdd_1.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
		txtDdd_1.setColumns(10);
		txtDdd_1.setBounds(524, 185, 127, 38);
		add(txtDdd_1);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(138, 139, 141));
		lblNewLabel_1.setBounds(0, 115, 978, 5);
		add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("희망 업종");
		lblNewLabel_2.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2.setBounds(77, 247, 139, 30);
		add(lblNewLabel_2);

		JComboBox 희망업종 = new JComboBox();
		희망업종.setBackground(new Color(233, 235, 232));
		희망업종.setModel(new DefaultComboBoxModel(
				new String[] { "선택", "한식", "분식", "양식", "중식", "일식/수산물", "패스트푸드", "커피점/카페", "유흥주점" }));
		희망업종.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		희망업종.setBounds(243, 243, 280, 38);
		add(희망업종);

		JLabel lblNewLabel_3 = new JLabel("▶ 진단 결과");
		lblNewLabel_3.setFont(new Font("맑은 고딕", Font.BOLD, 34));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_3.setBounds(0, 280, 216, 65);
		add(lblNewLabel_3);

		도로명 = new JTextField();
		도로명.setText("도로명입력");
		도로명.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		도로명.setBounds(701, 135, 166, 38);
		add(도로명);
		도로명.setColumns(10);

		JLabel 진단결과_업종개수 = new JLabel("");
		진단결과_업종개수.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_업종개수.setBounds(50, 360, 950, 30);
		add(진단결과_업종개수);

		JLabel 진단결과_업종매출 = new JLabel("");
		진단결과_업종매출.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_업종매출.setBounds(50, 410, 950, 30);
		add(진단결과_업종매출);

		JLabel 진단결과_근접식당정보 = new JLabel("");
		진단결과_근접식당정보.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_근접식당정보.setBounds(50, 460, 950, 30);
		add(진단결과_근접식당정보);

		JLabel 진단결과_유동인구 = new JLabel("");
		진단결과_유동인구.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_유동인구.setBounds(50, 520, 950, 30);
		add(진단결과_유동인구);

		JLabel 진단결과_상주인구 = new JLabel("");
		진단결과_상주인구.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_상주인구.setBounds(50, 570, 950, 30);
		add(진단결과_상주인구);

		JLabel 진단결과_영업지수 = new JLabel("");
		진단결과_영업지수.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		진단결과_영업지수.setBounds(50, 630, 950, 30);
		add(진단결과_영업지수);

		JButton 위치선택 = new JButton("지도에서 선택");
		위치선택.setBackground(new Color(188, 188, 188));
		위치선택.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		위치선택.setBounds(668, 188, 182, 38);
		add(위치선택);

		JButton 데이터출처 = new JButton("데이터 출처");
		데이터출처.setBackground(new Color(188, 188, 188));
		데이터출처.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		데이터출처.setBounds(690, 243, 160, 38);
		add(데이터출처);

		데이터출처.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new source();
			}
		});

		구.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				JComboBox idx = (JComboBox) e.getSource(); // 콤보박스 알아내기
				int index = idx.getSelectedIndex();
				if (index == 1) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "개포동", "논현동", "대치동", "도곡동", "삼성동", "세곡동",
							"수서동", "신사동", "압구정동", "역삼동", "율현동", "일원동", "자곡동", "청담동" }));
				} else if (index == 2) {
					동.setModel(new DefaultComboBoxModel(
							new String[] { "동 선택", "강일동", "고덕동", "길동", "둔촌동", "명일동", "상일동", "성내동", "암사동", "천호동" }));
				} else if (index == 3) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "미아동", "번동", "수유동", "우이동" }));
				} else if (index == 4) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "가양동", "개화동", "공항동", "과해동", "내발산동",
							"등촌동", "마곡동", "방화동", "염창동", "오곡동", "오쇠동", "외발산동", "화곡동" }));
				} else if (index == 5) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "남현동", "봉천동", "신림동" }));
				} else if (index == 6) {
					동.setModel(new DefaultComboBoxModel(
							new String[] { "동 선택", "광장동", "구의동", "군자동", "능동", "자양동", "중곡동", "화양동" }));
				} else if (index == 7) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "가리봉동", "개봉동", "고척동", "구로동", "궁동",
							"신도림동", "오류동", "은수동", "천왕동", "항동" }));
				} else if (index == 8) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "가산동", "독산동", "시흥동" }));
				} else if (index == 9) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "공릉동", "상계동", "월계동", "중계동", "하계동" }));
				} else if (index == 10) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "도봉동", "방학동", "쌍문동", "창동" }));
				} else if (index == 11) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "답십리동", "신설동", "용두동", "이문동", "장안동",
							"정농동", "제기동", "청량리동", "회기동", "휘경동" }));
				} else if (index == 12) {
					동.setModel(new DefaultComboBoxModel(
							new String[] { "동 선택", "노량진동", "대방동", "동작동", "본동", "사당동", "상도1동", "상도동", "신대방동", "흑석동" }));
				} else if (index == 13) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "공덕동", "구수동", "노고산동", "당인동", "대흥동",
							"도화동", "동교동", "마포동", "망원동", "상수동", "상암동", "서교동", "성산동", "신공덕동", "신수동", "신정동", "아현동", "연남동",
							"염리동", "용강동", "중동", "창전동", "토정동", "하중동", "합정동", "현석동" }));
				} else if (index == 14) {
					동.setModel(new DefaultComboBoxModel(
							new String[] { "동 선택", "남가좌동", "냉천동", "대신동", "대현동", "미근동", "봉원동", "북가좌동", "북아현동", "신촌동",
									"연희동", "영천동", "옥천동", "창천동", "천연동", "충정로2가", "충정로3가", "합동", "현저동", "홍은동", "홍제동" }));
				} else if (index == 15) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "내곡동", "반포동", "방배동", "서초동", "신원동", "양재동",
							"염곡동", "우면동", "원지동", "잠원동" }));
				} else if (index == 16) {
					동.setModel(new DefaultComboBoxModel(
							new String[] { "동 선택", "금호동1가", "금호동2가", "금호동3가", "금호동4가", "도선동", "마장동", "사근동", "상왕십리동",
									"성수동1가", "성수동2가", "송정동", "옥수동", "용답동", "응봉동", "하왕십리동", "행당동", "홍익동" }));
				} else if (index == 17) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "길음동", "돈암동", "동선동1가", "동선동2가", "동선동3가",
							"동선동4가", "동선동5가", "동소문동1가", "동소문동2가", "동소문동3가", "동소문동4가", "동소문동5가", "동소문동6가", "동소문동7가",
							"보문동1가", "보문동2가", "보문동3가", "보문동4가", "보문동5가", "보문동6가", "보문동7가", "삼선동1가", "삼선동2가", "삼선동3가",
							"삼선동4가", "삼선동5가", "상월곡동", "석관동", "성북동", "성북동1가", "안암동1가", "안암동2가", "안암동3가", "안암동4가",
							"안암동5가", "장위동", "정릉동", "종암동", "하월곡동" }));
				} else if (index == 18) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "가락동", "거여동", "마천동", "문정동", "방이동", "삼전동",
							"석촌동", "송파동", "신천동", "오금동", "잠실동", "장지동", "풍납동" }));
				} else if (index == 19) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "목동", "신월동", "신정동" }));
				} else if (index == 20) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "당산동", "당산동1가", "당산동2가", "당산동3가",
							"당산동4가", "당산동5가", "당산동6가", "대림동", "도림동", "문래동1가", "문래동2가", "문래동3가", "문래동4가", "문래동5가",
							"문래동6가", "신길동", "양평동", "양평동1가", "양평동2가", "양평동3가", "양평동4가", "양평동5가", "양평동6가", "양화동", "여의도동",
							"영등포동", "영등포동1가", "영등포동2가", "영등포동3가", "영등포동4가", "영등포동5가", "영등포동6가", "영등포동7가", "영등포동8가" }));
				} else if (index == 21) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "갈월동", "남영동", "도원동", "동빙고동", "동자동",
							"문배동", "보광동", "산천동", "서계동", "서빙고동", "신계동", "신창동", "용문동", "용산동1가", "용산동2가", "용산동3가", "용산동4가",
							"용산동5가", "용산동6가", "원효로1가", "원효로2가", "원효로3가", "원효로4가", "이촌동", "이태원동", "주성동", "청암동", "청파동1가",
							"청파동2가", "청파동3가", "한강로1가", "한강로2가", "한강로3가", "한남동", "효창동", "후암동" }));
				} else if (index == 22) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "갈현동", "구산동", "늑번동", "대조동", "불광동", "수색동",
							"신사동", "역촌동", "응암동", "증산동", "진관동" }));
				} else if (index == 23) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "가회동", "견지동", "경운동", "계동", "공평동", "관수동",
							"관철동", "관훈동", "교남동", "교북동", "구기동", "궁정동", "낙원동", "내수동", "내자동", "누상동", "누하동", "당주동", "도렴동",
							"동숭동", "명륜1가", "명륜2가", "명륜3가", "명륜4가", "묘동", "무악동", "봉익동", "부암동", "사간동", "사직동", "삼청동",
							"서린동", "세종로", "소격동", "송월동", "송현동", "수송동", "숭인동", "신교동", "신문로1가", "신문로2가", "신영동", "안국동",
							"연건동", "연지동", "예지동", "옥인동", "와룡동", "운니동", "원남동", "원서동", "이화동", "익선동", "인사동", "인의동", "장사동",
							"재동", "적선동", "종로1가", "종로2가", "종로3가", "종로4가", "종로5가", "종로6가", "중학동", "창성동", "창신동", "청운동",
							"청진동", "체부동", "충신동", "통의동", "통인동", "팔판동", "평동", "평창동", "필운동", "행촌동", "혜화동", "홍지동", "홍파동",
							"화동", "효자동", "효제동", "훈정동" }));
				} else if (index == 24) {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택", "광희동1가", "광희동2가", "남대문로1가", "남대문로2가",
							"남대문로3가", "남대문로4가", "남대문로5가", "남산동1가", "남산동2가", "남산동3가", "남창동", "남학동", "다동", "만리동1가",
							"만리동2가", "명동1가", "명동2가", "무교동", "무학동", "묵정동", "방산동", "봉래동1가", "봉래동2가", "북창동", "산림동", "삼각동",
							"서소문동", "소공동", "수표동", "수하동", "순화동", "신당동", "쌍림동", "예관동", "예장동", "오장동", "을지로1가", "을지로2가",
							"을지로3가", "을지로4가", "을지로5가", "을지로6가", "을지로7가", "의주로1가", "의주로2가", "인현동1가", "인현동2가", "입정동",
							"장교동", "장충동1가", "장충동2가", "저동1가", "저동2가", "정동", "주교동", "주자동", "중림동", "초동", "충무로1가", "충무로2가",
							"충무로3가", "충무로4가", "충무로5가", "충정로1가", "태평로1가", "태평로2가", "필동1가", "필동2가", "필동3가", "황학동",
							"회현동1가", "회현동2가", "회현동3가", "흥인동" }));
				} else if (index == 25) {
					동.setModel(
							new DefaultComboBoxModel(new String[] { "동 선택", "망우동", "면목동", "묵동", "상봉동", "신내동", "중화동" }));
				} else {
					동.setModel(new DefaultComboBoxModel(new String[] { "동 선택" }));
				}
			}
		});
		검색.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				try {
					if (!구.getSelectedItem().toString().equals("구 선택") && !동.getSelectedItem().toString().equals("동 선택")
							&& !도로명.getText().equals("도로명입력") && txtDdd.getText() != null
							&& txtDdd_1.getText() != null && !희망업종.getSelectedItem().toString().equals("선택")) {

						// 상권분석 요청
						Protocol protocol = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_상권분석결과조회);
						String data = 구.getSelectedItem().toString() + "!" + 동.getSelectedItem().toString() + "!"
								+ 도로명.getText() + "!" + txtDdd.getText() + "!" + txtDdd_1.getText() + "!"
								+ 희망업종.getSelectedItem().toString();
						protocol.setString(data);
						output.write(protocol.getPacket());

						// 결과 반환
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
						System.out.println("받음");
						if (protocolType == Protocol.TYPE6_VIEW_RES && protocolCode == Protocol.T6_상권분석결과조회승인) {
							System.out.println("들어옴");
							String[] Commercial = protocol.getString().split("%");
							// 인근 업종 리스트
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
								진단결과_근접식당정보.setText("선택하신 위치의 동일 업종 개수가 0이므로 측정할 수 없습니다.");
							} else {
								진단결과_근접식당정보.setText("현재 위치와 가장 가까운 식당: " + neighborhoodStore + " / " + distance + "m");
							}

							int sectorsStoreCount = Integer.parseInt(Commercial[2]);
							진단결과_업종개수.setText("선택한 지역의 동일 업종 가게 수: " + sectorsStoreCount + "개");
							long sales = Integer.parseInt(Commercial[3]);
							진단결과_업종매출.setText("선택한 지역의 동일 업종 월평균 매출: " + String.format("%,d", sales) + "원");
							String[] FloatingPopulation = Commercial[4].split("/");

							int total = Integer.parseInt(FloatingPopulation[0]);
							String age = FloatingPopulation[1];
							String time = FloatingPopulation[2];

							진단결과_유동인구.setText("총 " + String.format("%,d", total) + "명, " + age + "가 가장 많으며 " + time
									+ "에 유동인구가 많다.");
							String[] ResidentPopulation = Commercial[5].split("/");
							int ResidentTotal = Integer.parseInt(ResidentPopulation[0]);
							String ResidentAge = ResidentPopulation[1];
							진단결과_상주인구.setText(
									"총 " + String.format("%,d", ResidentTotal) + "명, " + ResidentAge + "가 가장 많이 상주한다.");

							String[] BusinessIndex = Commercial[6].split("/");
							double percent = Double.parseDouble(BusinessIndex[0]);
							sales = Long.parseLong(BusinessIndex[1]);
							int businessIndex = Integer.parseInt(BusinessIndex[2]);
							진단결과_영업지수.setText("인구등급: " + (int) Math.round(percent) + "등급 / 매출등급: " + sales
									+ "등급 / 영업지수: " + String.format("%,d", businessIndex));

							JButton 인근가게_리스트 = new JButton("인근가게 목록 보기");
							인근가게_리스트.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
							인근가게_리스트.setBackground(new Color(188, 188, 188));
							인근가게_리스트.setBounds(223, 296, 249, 38);
							add(인근가게_리스트);
							인근가게_리스트.addActionListener(new ActionListener() {
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
						JOptionPane.showMessageDialog(null, "모든 선택 박스를 체크하지 않았습니다.");
					}
				} catch (Exception message) {
					return;
				}
			}
		});
		위치선택.addActionListener(new ActionListener() {
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

		JLabel label = new JLabel("▶ 인근 업종 리스트");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setBounds(29, 33, 255, 33);
		getContentPane().add(label);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		lblNewLabel.setBounds(56, 81, 529, 40);
		getContentPane().add(lblNewLabel);

		JLabel label_1 = new JLabel("");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		label_1.setBounds(56, 122, 529, 40);
		getContentPane().add(label_1);

		JLabel label_2 = new JLabel("");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		label_2.setBounds(56, 164, 529, 40);
		getContentPane().add(label_2);

		JLabel label_3 = new JLabel("");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		label_3.setBounds(56, 205, 529, 40);
		getContentPane().add(label_3);

		JLabel label_4 = new JLabel("");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		label_4.setBounds(56, 246, 529, 40);
		getContentPane().add(label_4);

		JButton 닫기 = new JButton("닫기");
		닫기.setBackground(new Color(188, 188, 188));
		닫기.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		닫기.setBounds(456, 39, 129, 29);
		getContentPane().add(닫기);

		// 인근 업종 리스트 별 내림차순 정렬
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

		닫기.addActionListener(new ActionListener() {
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

		JLabel lblNewLabel = new JLabel("▶ 데이터 출처");
		lblNewLabel.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		lblNewLabel.setBounds(37, 47, 198, 36);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("※ 추정 매출 - 해당 위치의 카드 결제 매출 데이터");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(0, 145, 598, 42);
		getContentPane().add(lblNewLabel_1);

		JLabel label = new JLabel("서울 열린데이터 광장");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label.setBounds(0, 98, 598, 42);
		getContentPane().add(label);

		JLabel lblKipris = new JLabel("Kipris");
		lblKipris.setHorizontalAlignment(SwingConstants.CENTER);
		lblKipris.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		lblKipris.setBounds(0, 191, 598, 42);
		getContentPane().add(lblKipris);

		JLabel label_1 = new JLabel("※ 소상공인 영업점 입지적합도 및 매출등급 계산 시스템 및 방법");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label_1.setBounds(0, 235, 598, 42);
		getContentPane().add(label_1);

		JButton 닫기 = new JButton("닫기");
		닫기.setBackground(new Color(188, 188, 188));
		닫기.setBounds(243, 312, 129, 29);
		getContentPane().add(닫기);
		닫기.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
}

class Map3 extends JFrame {
	public Map3() {
		setTitle("위치 선택");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 900, 800);
		JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		JButton btnClose = new JButton("닫기");
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