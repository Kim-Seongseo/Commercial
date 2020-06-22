package Server;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.xml.transform.Result;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class ViewHandler {

   InputStream input;
   OutputStream output;
   Connection conn;

   ViewHandler(InputStream input, OutputStream output, Connection conn) {
      this.input = input;
      this.output = output;
      this.conn = conn;
   }

   void CODE1(Protocol protocol) throws IOException, SQLException {
      Statement stmt = conn.createStatement();
      String[] value = protocol.getString().split("!");
      String gu = value[0];
      String dong = value[1];
      String sectors = value[2];
      String result = "";
      String sql = "SELECT * FROM commercial.상가업소정보 where 상권업종중분류명 = \"" + sectors + "\" and 지번주소 like '%" + gu + " "
            + dong + "%';";
      System.out.println(sql);
      try {
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            result += rs.getString("상호명") + "!" + rs.getString("상권업종중분류명") + "!" + rs.getString("도로명주소") + "%";
         }
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_식당표조회승인);
         protocol.setString(result);
         output.write(protocol.getPacket());
      } catch (Exception e) {
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_식당표조회거절);
         output.write(protocol.getPacket());
      }

   }

   void CODE2(Protocol protocol) throws IOException, SQLException {
      String value = protocol.getString();
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      Statement stmt2 = conn.createStatement();
      ResultSet rs2 = null;
      Statement stmt3 = conn.createStatement();
      ResultSet rs3 = null;
      String sql = "select FORMAT(남성유동인구수,0), FORMAT(여성유동인구수,0) from 추정유동인구 where 상권코드명='" + value + "'";
      String sql2 = "select FORMAT(10대유동인구수,0), FORMAT(20대유동인구수,0), FORMAT(30대유동인구수,0), FORMAT(40대유동인구수,0), FORMAT(50대유동인구수,0) from 추정유동인구 where 상권코드명='"
            + value + "'";
      String sql3 = "select FORMAT(3시간대유동인구수,0), FORMAT(4시간대유동인구수,0), FORMAT(5시간대유동인구수,0), FORMAT(6시간대유동인구수,0) from 추정유동인구 where 상권코드명='"
            + value + "'";
      String tmp1 = "", tmp2 = "", tmp3 = "", result = "";
      try {
         rs = stmt.executeQuery(sql);
         rs2 = stmt2.executeQuery(sql2);
         rs3 = stmt3.executeQuery(sql3);

         while (rs.next()) {
            tmp1 = rs.getString("FORMAT(남성유동인구수,0)") + "!" + rs.getString("FORMAT(여성유동인구수,0)");
         }
         while (rs2.next()) {
            tmp2 = rs2.getString("FORMAT(10대유동인구수,0)") + "!" + rs2.getString("FORMAT(20대유동인구수,0)") + "!"
                  + rs2.getString("FORMAT(30대유동인구수,0)") + "!" + rs2.getString("FORMAT(40대유동인구수,0)") + "!"
                  + rs2.getString("FORMAT(50대유동인구수,0)");
         }
         while (rs3.next()) {
            tmp3 = rs3.getString("FORMAT(3시간대유동인구수,0)") + "!" + rs3.getString("FORMAT(4시간대유동인구수,0)") + "!"
                  + rs3.getString("FORMAT(5시간대유동인구수,0)") + "!" + rs3.getString("FORMAT(6시간대유동인구수,0)");
         }
         result = tmp1 + "#" + tmp2 + "#" + tmp3;
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_유동인구표조회승인);
         protocol.setString(result);
         output.write(protocol.getPacket());
      } catch (Exception e) {
         System.out.println(e);
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_유동인구표조회거절);
         output.write(protocol.getPacket());
      }
   }

   void CODE3(Protocol protocol, String id) throws IOException, SQLException {
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String result = "";
      String[] value = protocol.getString().split("!");
      String gu = value[0]; // 구
      String dong = value[1]; // 동
      String street = value[2]; // 도로명
      Double latitude = Double.parseDouble(value[3]); // 위도
      Double longitude = Double.parseDouble(value[4]); // 경도
      String hopefulIndustry = value[5]; // 희망업종
      try {
         // 선택한 동에 대한 도로명 주소가 있어야 한다.
         String sql = "SELECT * FROM commercial.도로명정보 where 읍면동명 = \"" + dong + "\" and 도로명 like '%" + street
               + "%';";
         rs = stmt.executeQuery(sql);
         rs.last();
         if (rs.getRow() > 0) {
            // 동일 업종 개수조회 및 인근업종 조회
            sql = "SELECT * FROM commercial.상가업소정보 where 상권업종중분류명 = \"" + hopefulIndustry + "\" and 지번주소 like '%"
                  + gu + " " + dong + "%' and 도로명주소 like '%" + street + "%';";
            // 인근 업종
            System.out.println(sql);
            String neighborhoodStore = null;
            double distance = 9999;
            rs = null;
            rs = stmt.executeQuery(sql);
            // 인근 업종 리스트
            Map<String, Double> neighboringIndustries = new HashMap<String, Double>();
            int range = 0;
            int storeNum = 0;
            while (rs.next()) {
               String name = rs.getString("상호명");
               double lat = rs.getDouble("위도");
               double lon = rs.getDouble("경도");
               int storeNumTmp = rs.getInt("상가업소번호");
               double distanceResult = distance(latitude, longitude, lat, lon);
               // if (range < 5) {
               neighboringIndustries.put(name, distanceResult);
               // result += name + "/" + distanceResult + "/";
               // }
               if (distanceResult < distance) {
                  distance = distanceResult;
                  neighborhoodStore = name;
                  storeNum = storeNumTmp;
               }
               // 두번째 자리에서 반올림
               distance = Math.round(distance * 100) / 100.0;
               range += 2;
            }
            List<String> keySetListIndustries = new ArrayList<>(neighboringIndustries.keySet());
            Collections.sort(keySetListIndustries,
                  (o1, o2) -> (neighboringIndustries.get(o1).compareTo(neighboringIndustries.get(o2))));
            int check = 0;
            for (String key : keySetListIndustries) {
               if (check == 5) {
                  break;
               } else {
                  result += key + "/" + neighboringIndustries.get(key) + "/";
                  check++;
               }
            }
            if (range > 10) {
               range = 10;
            }
            result += range + "%";
            result += distance + "/" + neighborhoodStore + "%";
            rs.last();
            int sectorsStoreCount = rs.getRow();// 위치 동일 업종 개수
            result += sectorsStoreCount + "%";
            // 업종 매출
            sql = "SELECT * FROM commercial.요식업추정매출 where 상권코드명 like '%" + street + "%' and 서비스업종코드명 like '%"
                  + hopefulIndustry + "%';";
            rs = null;
            rs = stmt.executeQuery(sql);
            long sales = 0; // 매출
            int storeCnt = 0; // 점포수
            // 포괄적인 검색이 들어오는 경우 처리를 위한 cnt
            while (rs.next()) {
               sales += rs.getLong("주중매출금액");
               sales += rs.getLong("주말매출금액");
               storeCnt += rs.getInt("점포수");
            }
            sales = (sales / storeCnt);
            result += sales + "%";

            // 유동인구
            Map<String, Integer> floatingPopulationAge = new HashMap<String, Integer>();
            Map<String, Integer> floatingPopulationTime = new HashMap<String, Integer>();
            sql = "SELECT * FROM commercial.추정유동인구 where 상권코드명 like '%" + street + "%';";

            int total = 0;
            int avg = 0;
            rs = stmt.executeQuery(sql);
            int tmpFloat = 0;
            while (rs.next()) {
               tmpFloat = rs.getInt("번호");
               total += rs.getInt("남성유동인구수");
               total += rs.getInt("여성유동인구수");
               floatingPopulationAge.put("10대", rs.getInt("10대유동인구수"));
               floatingPopulationAge.put("20대", rs.getInt("20대유동인구수"));
               floatingPopulationAge.put("30대", rs.getInt("30대유동인구수"));
               floatingPopulationAge.put("40대", rs.getInt("40대유동인구수"));
               floatingPopulationAge.put("50대", rs.getInt("50대유동인구수"));
               floatingPopulationTime.put("11시~14시", rs.getInt("3시간대유동인구수"));
               floatingPopulationTime.put("14시~17시", rs.getInt("4시간대유동인구수"));
               floatingPopulationTime.put("17시~21시", rs.getInt("5시간대유동인구수"));
               floatingPopulationTime.put("21시~24시", rs.getInt("6시간대유동인구수"));
               avg++;
            }
            total = total / avg;
            List<String> keySetListAge = new ArrayList<>(floatingPopulationAge.keySet());
            List<String> keySetListTime = new ArrayList<>(floatingPopulationTime.keySet());
            // 유동인구 연령대별 내림차순
            Collections.sort(keySetListAge,
                  (o1, o2) -> (floatingPopulationAge.get(o2).compareTo(floatingPopulationAge.get(o1))));
            Collections.sort(keySetListTime,
                  (o1, o2) -> (floatingPopulationTime.get(o2).compareTo(floatingPopulationTime.get(o1))));

            for (String key : keySetListAge) {
               result += total + "/" + key + "/";
               break;
            } // 유동인구 시간대별 내림차순
            for (String key : keySetListTime) {
               result += key + "%";
               break;
            }

            List<Object> floatingTotalPopulationAge = new ArrayList<Object>();
            int totalPopulationAge = 0;
            sql = "SELECT * FROM commercial.추정유동인구;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
               totalPopulationAge += rs.getInt("남성유동인구수");
               totalPopulationAge += rs.getInt("여성유동인구수");
               floatingTotalPopulationAge.add(totalPopulationAge);
               totalPopulationAge = 0;
            }
            floatingTotalPopulationAge.add(total);
            Double percent = 0.0;
            Collections.sort(floatingTotalPopulationAge, Collections.reverseOrder());
            for (int tmp = 0; tmp < floatingTotalPopulationAge.size(); tmp++) {
               if ((int) floatingTotalPopulationAge.get(tmp) == total) {
                  System.out.println("======================");
                  Double num = (double) tmp + 1;
                  Double den = (double) floatingTotalPopulationAge.size();
                  System.out.println(num);
                  System.out.println(den);
                  percent = (num / den) * 100;

                  break;
               }
            }
            // 상주인구
            Map<String, Integer> residentPopulationAge = new HashMap<String, Integer>();
            sql = "SELECT * FROM commercial.상주인구 where 상권코드명 like '%" + street + "%';";
            total = 0;
            rs = stmt.executeQuery(sql);
            int tmpResident = 0;
            while (rs.next()) {
               tmpResident = rs.getInt("번호");
               total += rs.getInt("남성상주인구수");
               total += rs.getInt("여성상주인구수");
               residentPopulationAge.put("10대", rs.getInt("10대상주인구수"));
               residentPopulationAge.put("20대", rs.getInt("20대상주인구수"));
               residentPopulationAge.put("30대", rs.getInt("30대상주인구수"));
               residentPopulationAge.put("40대", rs.getInt("40대상주인구수"));
               residentPopulationAge.put("50대", rs.getInt("50대상주인구수"));
            }
            keySetListAge = new ArrayList<>(residentPopulationAge.keySet());
            Collections.sort(keySetListAge,
                  (o1, o2) -> (residentPopulationAge.get(o2).compareTo(residentPopulationAge.get(o1))));
            for (String key : keySetListAge) {
               result += total + "/" + key + "%";
               break;
            }
            // 영업지수
            int businessIndex = 0;
            for (int k = 1; k <= sectorsStoreCount; k++) {
               businessIndex += (sales / Math.pow(distance, 2));
            }
            if (sales < 12000000) {
               sales = 5;
            } else if (sales < 29600000) {
               sales = 4;
            } else if (sales < 52500000) {
               sales = 3;
            } else if (sales < 76000000) {
               sales = 2;
            } else {
               sales = 1;
            }
            if (percent < 20.0) {
               percent = 1.0;
            } else if (percent < 40.0) {
               percent = 2.0;
            } else if (percent < 60.0) {
               percent = 3.0;
            } else if (percent < 80.0) {
               percent = 4.0;
            } else {
               percent = 5.0;
            }
            result += percent + "/" + sales + "/" + businessIndex + "%";

            protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_상권분석결과조회승인);
            protocol.setString(result);

            // insert or update
            // select한 다음에 row count가 0개이면 insert. else면 update
            sql = "SELECT * FROM commercial.업소정보조회내역 where 아이디=" + id;
            rs = stmt.executeQuery(sql);
            int tmp = 0;
            while (rs.next()) {
               tmp = rs.getInt("상가업소번호");
            }
            System.out.println(tmp);
            rs.last();
            if (rs.getRow() > 0) {
               sql = "UPDATE 업소정보조회내역 SET 상가업소번호=" + storeNum + ", 구='" + gu + "', 동='" + dong + "' WHERE 아이디='"
                     + id + "' and 상가업소번호=" + tmp;
               stmt.executeUpdate(sql);

            } else { // insert
               sql = "INSERT INTO commercial.업소정보조회내역 (`아이디`, `상가업소번호`, `구`, `동`) VALUES ('" + id + "', '"
                     + storeNum + "', '" + gu + "', '" + dong + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.상주인구조회내역 where 아이디=" + id;
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
               tmp = rs.getInt("번호");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               sql = "UPDATE 상주인구조회내역 SET 아이디='" + id + "', 번호='" + tmpResident + "' WHERE 아이디='" + id
                     + "' and 번호=" + tmp;
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.상주인구조회내역 (`아이디`, `번호`) VALUES ('" + id + "', '" + tmpResident + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.추정유동인구조회내역 where 아이디=" + id;
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
               tmp = rs.getInt("번호");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               sql = "UPDATE 추정유동인구조회내역 SET 아이디='" + id + "', 번호='" + tmpFloat + "' WHERE 아이디='" + id + "' and 번호="
                     + tmp;
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.추정유동인구조회내역 (`아이디`, `번호`) VALUES ('" + id + "', '" + tmpFloat + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.추정매출조회내역 where 아이디=" + id;
            rs = stmt.executeQuery(sql);
            String tmpStr1 = null;
            String tmpStr2 = null;
            while(rs.next()) {
               tmpStr1 = rs.getString("상권코드명");
               tmpStr2 = rs.getString("서비스업종코드명");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               System.out.println("추정매출");
               sql = "UPDATE 추정매출조회내역 SET 아이디=" + id + ", 상권코드명='" + street + "', 서비스업종코드명='" + hopefulIndustry
                     + "' WHERE 아이디='" + id + "' and 상권코드명='" + tmpStr1 + "' and 서비스업종코드명='" + tmpStr2+"'";
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.추정매출조회내역 (`아이디`, `상권코드명`, `서비스업종코드명`) VALUES ('" + id + "', '"
                     + street + "', '" + hopefulIndustry + "');";
               stmt.executeUpdate(sql);
            }

            output.write(protocol.getPacket());
         } else {
            // JOptionPane.showMessageDialog(null, "동과 도로명의 정보가 일치하지 않습니다.");
            result = "동과 도로명의 정보가 일치하지 않습니다.%";
            protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_상권분석결과조회거절);
            protocol.setString(result);
            output.write(protocol.getPacket());
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         result = "해당 위치에 대한 정보가 없습니다.%";
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_상권분석결과조회거절);
         protocol.setString(result);
         output.write(protocol.getPacket());
      }

   }

   private static double distance(double lat1, double lon1, double lat2, double lon2) {

      double theta = lon1 - lon2;
      double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
            + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

      dist = Math.acos(dist);
      dist = rad2deg(dist);
      dist = dist * 60 * 1.1515;
      dist = dist * 1609.344;

      return (dist);
   }

   // This function converts decimal degrees to radians
   private static double deg2rad(double deg) {
      return (deg * Math.PI / 180.0);
   }

   // This function converts radians to decimal degrees
   private static double rad2deg(double rad) {
      return (rad * 180 / Math.PI);
   }
}