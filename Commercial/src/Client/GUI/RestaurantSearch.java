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
		title.add("상호명");
		title.add("상권업종중분류명");
		title.add("도로명주소");
		model = new DefaultTableModel();
		model.setDataVector(result, title);
		table = new JTable(model);
		table.setBackground(Color.white);
		table.setRowHeight(25);
		table.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		JScrollPane scrollpane = new JScrollPane(table);
		scrollpane.setBounds(44, 206, 858, 454);
		add(scrollpane);

		JLabel 지역선택 = new JLabel("지역 선택");
		지역선택.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		지역선택.setHorizontalAlignment(SwingConstants.CENTER);
		지역선택.setBounds(10, 135, 127, 38);
		add(지역선택);

		JLabel 서울특별시 = new JLabel("서울특별시");
		서울특별시.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		서울특별시.setHorizontalAlignment(SwingConstants.CENTER);
		서울특별시.setBounds(128, 135, 139, 38);
		add(서울특별시);

		JComboBox 구 = new JComboBox();
		구.setBackground(new Color(233, 235, 232));
		구.setForeground(Color.BLACK);
		구.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		구.setModel(new DefaultComboBoxModel(new String[] { "구 선택", "강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구",
				"금천구", "노원구", "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구",
				"은평구", "종로구", "중구", "중량구" }));
		구.setBounds(260, 135, 139, 38);
		add(구);

		JComboBox 동 = new JComboBox();
		동.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		동.setBackground(new Color(233, 235, 232));
		동.setForeground(Color.BLACK);
		동.setModel(new DefaultComboBoxModel(new String[] { "동 선택" }));
		동.setBounds(416, 135, 127, 38);
		add(동);

		JButton 검색 = new JButton("검색");
		검색.setBackground(new Color(188, 188, 188));
		검색.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		검색.setBounds(704, 134, 92, 40);
		add(검색);

		JLabel lblNewLabel = new JLabel("▶ 동네 음식점 조회하기");
		lblNewLabel.setForeground(new Color(57, 59, 58));
		lblNewLabel.setFont(new Font("HY엽서M", Font.BOLD, 34));
		lblNewLabel.setBounds(0, 40, 978, 60);
		add(lblNewLabel);

		JComboBox 업종분류 = new JComboBox();
		업종분류.setModel(new DefaultComboBoxModel(
				new String[] { "전체", "한식", "분식", "양식", "중식", "일식/수산물", "패스트푸드", "커피점/카페", "유흥주점" }));
		업종분류.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		업종분류.setBackground(new Color(233, 235, 232));
		업종분류.setBounds(560, 135, 127, 38);
		add(업종분류);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setOpaque(true);
		lblNewLabel_1.setBackground(new Color(138, 139, 141));
		lblNewLabel_1.setBounds(0, 115, 978, 5);
		add(lblNewLabel_1);

		JButton 지도보기 = new JButton("지도 보기");
		지도보기.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		지도보기.setFont(new Font("맑은 고딕", Font.PLAIN, 22));
		지도보기.setBounds(813, 134, 148, 40);
		지도보기.setBackground(new Color(188, 188, 188));
		add(지도보기);

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

				String gu = 구.getSelectedItem().toString();
				String dong = 동.getSelectedItem().toString();
				String sectors = 업종분류.getSelectedItem().toString();
				if (!gu.equals("구 선택") && !dong.equals("동 선택") && !sectors.equals("전체")) {
					try {
						// 식당조회 요청
						Protocol protocol = new Protocol(Protocol.TYPE5_VIEW_REQ, Protocol.T5_식당표조회);
						String data = gu + "!" + dong + "!" + sectors;
						protocol.setString(data);
						output.write(protocol.getPacket());

						// 식당 조회 응답
						protocol = new Protocol();
						byte[] header = protocol.getPacket();
						input.read(header);
						int protocolType = header[0];
						int protocolCode = header[1];
						rlist.clear();
						if (protocolType == Protocol.TYPE6_VIEW_RES && protocolCode == Protocol.T6_식당표조회승인) {

							int bodylength = protocol.byteToInt(header, 2);
							if (bodylength != 0) {
								byte[] body = new byte[bodylength];
								input.read(body);
								protocol.setPacket(header, body);
							}

						} else {
							JOptionPane.showMessageDialog(null, "예외처리 발생");
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
					JOptionPane.showMessageDialog(null, "모든 선택 박스를 체크하지 않았습니다.");
				}
			}
		});
		지도보기.addActionListener(new ActionListener() {
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
		setTitle("식당 조회");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 80, 900, 800);
		JFXPanel fxPanel = new JFXPanel();
		add(fxPanel);
		JButton btnClose = new JButton("닫기");
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