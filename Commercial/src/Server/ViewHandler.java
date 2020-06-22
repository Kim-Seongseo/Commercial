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
      String sql = "SELECT * FROM commercial.�󰡾������� where ��Ǿ����ߺз��� = \"" + sectors + "\" and �����ּ� like '%" + gu + " "
            + dong + "%';";
      System.out.println(sql);
      try {
         ResultSet rs = stmt.executeQuery(sql);
         while (rs.next()) {
            result += rs.getString("��ȣ��") + "!" + rs.getString("��Ǿ����ߺз���") + "!" + rs.getString("���θ��ּ�") + "%";
         }
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_�Ĵ�ǥ��ȸ����);
         protocol.setString(result);
         output.write(protocol.getPacket());
      } catch (Exception e) {
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_�Ĵ�ǥ��ȸ����);
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
      String sql = "select FORMAT(���������α���,0), FORMAT(���������α���,0) from ���������α� where ����ڵ��='" + value + "'";
      String sql2 = "select FORMAT(10�������α���,0), FORMAT(20�������α���,0), FORMAT(30�������α���,0), FORMAT(40�������α���,0), FORMAT(50�������α���,0) from ���������α� where ����ڵ��='"
            + value + "'";
      String sql3 = "select FORMAT(3�ð��������α���,0), FORMAT(4�ð��������α���,0), FORMAT(5�ð��������α���,0), FORMAT(6�ð��������α���,0) from ���������α� where ����ڵ��='"
            + value + "'";
      String tmp1 = "", tmp2 = "", tmp3 = "", result = "";
      try {
         rs = stmt.executeQuery(sql);
         rs2 = stmt2.executeQuery(sql2);
         rs3 = stmt3.executeQuery(sql3);

         while (rs.next()) {
            tmp1 = rs.getString("FORMAT(���������α���,0)") + "!" + rs.getString("FORMAT(���������α���,0)");
         }
         while (rs2.next()) {
            tmp2 = rs2.getString("FORMAT(10�������α���,0)") + "!" + rs2.getString("FORMAT(20�������α���,0)") + "!"
                  + rs2.getString("FORMAT(30�������α���,0)") + "!" + rs2.getString("FORMAT(40�������α���,0)") + "!"
                  + rs2.getString("FORMAT(50�������α���,0)");
         }
         while (rs3.next()) {
            tmp3 = rs3.getString("FORMAT(3�ð��������α���,0)") + "!" + rs3.getString("FORMAT(4�ð��������α���,0)") + "!"
                  + rs3.getString("FORMAT(5�ð��������α���,0)") + "!" + rs3.getString("FORMAT(6�ð��������α���,0)");
         }
         result = tmp1 + "#" + tmp2 + "#" + tmp3;
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_�����α�ǥ��ȸ����);
         protocol.setString(result);
         output.write(protocol.getPacket());
      } catch (Exception e) {
         System.out.println(e);
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_�����α�ǥ��ȸ����);
         output.write(protocol.getPacket());
      }
   }

   void CODE3(Protocol protocol, String id) throws IOException, SQLException {
      Statement stmt = conn.createStatement();
      ResultSet rs = null;
      String result = "";
      String[] value = protocol.getString().split("!");
      String gu = value[0]; // ��
      String dong = value[1]; // ��
      String street = value[2]; // ���θ�
      Double latitude = Double.parseDouble(value[3]); // ����
      Double longitude = Double.parseDouble(value[4]); // �浵
      String hopefulIndustry = value[5]; // �������
      try {
         // ������ ���� ���� ���θ� �ּҰ� �־�� �Ѵ�.
         String sql = "SELECT * FROM commercial.���θ����� where ���鵿�� = \"" + dong + "\" and ���θ� like '%" + street
               + "%';";
         rs = stmt.executeQuery(sql);
         rs.last();
         if (rs.getRow() > 0) {
            // ���� ���� ������ȸ �� �αپ��� ��ȸ
            sql = "SELECT * FROM commercial.�󰡾������� where ��Ǿ����ߺз��� = \"" + hopefulIndustry + "\" and �����ּ� like '%"
                  + gu + " " + dong + "%' and ���θ��ּ� like '%" + street + "%';";
            // �α� ����
            System.out.println(sql);
            String neighborhoodStore = null;
            double distance = 9999;
            rs = null;
            rs = stmt.executeQuery(sql);
            // �α� ���� ����Ʈ
            Map<String, Double> neighboringIndustries = new HashMap<String, Double>();
            int range = 0;
            int storeNum = 0;
            while (rs.next()) {
               String name = rs.getString("��ȣ��");
               double lat = rs.getDouble("����");
               double lon = rs.getDouble("�浵");
               int storeNumTmp = rs.getInt("�󰡾��ҹ�ȣ");
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
               // �ι�° �ڸ����� �ݿø�
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
            int sectorsStoreCount = rs.getRow();// ��ġ ���� ���� ����
            result += sectorsStoreCount + "%";
            // ���� ����
            sql = "SELECT * FROM commercial.��ľ��������� where ����ڵ�� like '%" + street + "%' and ���񽺾����ڵ�� like '%"
                  + hopefulIndustry + "%';";
            rs = null;
            rs = stmt.executeQuery(sql);
            long sales = 0; // ����
            int storeCnt = 0; // ������
            // �������� �˻��� ������ ��� ó���� ���� cnt
            while (rs.next()) {
               sales += rs.getLong("���߸���ݾ�");
               sales += rs.getLong("�ָ�����ݾ�");
               storeCnt += rs.getInt("������");
            }
            sales = (sales / storeCnt);
            result += sales + "%";

            // �����α�
            Map<String, Integer> floatingPopulationAge = new HashMap<String, Integer>();
            Map<String, Integer> floatingPopulationTime = new HashMap<String, Integer>();
            sql = "SELECT * FROM commercial.���������α� where ����ڵ�� like '%" + street + "%';";

            int total = 0;
            int avg = 0;
            rs = stmt.executeQuery(sql);
            int tmpFloat = 0;
            while (rs.next()) {
               tmpFloat = rs.getInt("��ȣ");
               total += rs.getInt("���������α���");
               total += rs.getInt("���������α���");
               floatingPopulationAge.put("10��", rs.getInt("10�������α���"));
               floatingPopulationAge.put("20��", rs.getInt("20�������α���"));
               floatingPopulationAge.put("30��", rs.getInt("30�������α���"));
               floatingPopulationAge.put("40��", rs.getInt("40�������α���"));
               floatingPopulationAge.put("50��", rs.getInt("50�������α���"));
               floatingPopulationTime.put("11��~14��", rs.getInt("3�ð��������α���"));
               floatingPopulationTime.put("14��~17��", rs.getInt("4�ð��������α���"));
               floatingPopulationTime.put("17��~21��", rs.getInt("5�ð��������α���"));
               floatingPopulationTime.put("21��~24��", rs.getInt("6�ð��������α���"));
               avg++;
            }
            total = total / avg;
            List<String> keySetListAge = new ArrayList<>(floatingPopulationAge.keySet());
            List<String> keySetListTime = new ArrayList<>(floatingPopulationTime.keySet());
            // �����α� ���ɴ뺰 ��������
            Collections.sort(keySetListAge,
                  (o1, o2) -> (floatingPopulationAge.get(o2).compareTo(floatingPopulationAge.get(o1))));
            Collections.sort(keySetListTime,
                  (o1, o2) -> (floatingPopulationTime.get(o2).compareTo(floatingPopulationTime.get(o1))));

            for (String key : keySetListAge) {
               result += total + "/" + key + "/";
               break;
            } // �����α� �ð��뺰 ��������
            for (String key : keySetListTime) {
               result += key + "%";
               break;
            }

            List<Object> floatingTotalPopulationAge = new ArrayList<Object>();
            int totalPopulationAge = 0;
            sql = "SELECT * FROM commercial.���������α�;";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
               totalPopulationAge += rs.getInt("���������α���");
               totalPopulationAge += rs.getInt("���������α���");
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
            // �����α�
            Map<String, Integer> residentPopulationAge = new HashMap<String, Integer>();
            sql = "SELECT * FROM commercial.�����α� where ����ڵ�� like '%" + street + "%';";
            total = 0;
            rs = stmt.executeQuery(sql);
            int tmpResident = 0;
            while (rs.next()) {
               tmpResident = rs.getInt("��ȣ");
               total += rs.getInt("���������α���");
               total += rs.getInt("���������α���");
               residentPopulationAge.put("10��", rs.getInt("10������α���"));
               residentPopulationAge.put("20��", rs.getInt("20������α���"));
               residentPopulationAge.put("30��", rs.getInt("30������α���"));
               residentPopulationAge.put("40��", rs.getInt("40������α���"));
               residentPopulationAge.put("50��", rs.getInt("50������α���"));
            }
            keySetListAge = new ArrayList<>(residentPopulationAge.keySet());
            Collections.sort(keySetListAge,
                  (o1, o2) -> (residentPopulationAge.get(o2).compareTo(residentPopulationAge.get(o1))));
            for (String key : keySetListAge) {
               result += total + "/" + key + "%";
               break;
            }
            // ��������
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

            protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_��Ǻм������ȸ����);
            protocol.setString(result);

            // insert or update
            // select�� ������ row count�� 0���̸� insert. else�� update
            sql = "SELECT * FROM commercial.����������ȸ���� where ���̵�=" + id;
            rs = stmt.executeQuery(sql);
            int tmp = 0;
            while (rs.next()) {
               tmp = rs.getInt("�󰡾��ҹ�ȣ");
            }
            System.out.println(tmp);
            rs.last();
            if (rs.getRow() > 0) {
               sql = "UPDATE ����������ȸ���� SET �󰡾��ҹ�ȣ=" + storeNum + ", ��='" + gu + "', ��='" + dong + "' WHERE ���̵�='"
                     + id + "' and �󰡾��ҹ�ȣ=" + tmp;
               stmt.executeUpdate(sql);

            } else { // insert
               sql = "INSERT INTO commercial.����������ȸ���� (`���̵�`, `�󰡾��ҹ�ȣ`, `��`, `��`) VALUES ('" + id + "', '"
                     + storeNum + "', '" + gu + "', '" + dong + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.�����α���ȸ���� where ���̵�=" + id;
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
               tmp = rs.getInt("��ȣ");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               sql = "UPDATE �����α���ȸ���� SET ���̵�='" + id + "', ��ȣ='" + tmpResident + "' WHERE ���̵�='" + id
                     + "' and ��ȣ=" + tmp;
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.�����α���ȸ���� (`���̵�`, `��ȣ`) VALUES ('" + id + "', '" + tmpResident + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.���������α���ȸ���� where ���̵�=" + id;
            rs = stmt.executeQuery(sql);
            while(rs.next()) {
               tmp = rs.getInt("��ȣ");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               sql = "UPDATE ���������α���ȸ���� SET ���̵�='" + id + "', ��ȣ='" + tmpFloat + "' WHERE ���̵�='" + id + "' and ��ȣ="
                     + tmp;
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.���������α���ȸ���� (`���̵�`, `��ȣ`) VALUES ('" + id + "', '" + tmpFloat + "');";
               stmt.executeUpdate(sql);
            }

            sql = "SELECT * FROM commercial.����������ȸ���� where ���̵�=" + id;
            rs = stmt.executeQuery(sql);
            String tmpStr1 = null;
            String tmpStr2 = null;
            while(rs.next()) {
               tmpStr1 = rs.getString("����ڵ��");
               tmpStr2 = rs.getString("���񽺾����ڵ��");
            }
            rs.last();
            if (rs.getRow() > 0) { // update
               System.out.println("��������");
               sql = "UPDATE ����������ȸ���� SET ���̵�=" + id + ", ����ڵ��='" + street + "', ���񽺾����ڵ��='" + hopefulIndustry
                     + "' WHERE ���̵�='" + id + "' and ����ڵ��='" + tmpStr1 + "' and ���񽺾����ڵ��='" + tmpStr2+"'";
               stmt.executeUpdate(sql);
            } else { // insert
               sql = "INSERT INTO commercial.����������ȸ���� (`���̵�`, `����ڵ��`, `���񽺾����ڵ��`) VALUES ('" + id + "', '"
                     + street + "', '" + hopefulIndustry + "');";
               stmt.executeUpdate(sql);
            }

            output.write(protocol.getPacket());
         } else {
            // JOptionPane.showMessageDialog(null, "���� ���θ��� ������ ��ġ���� �ʽ��ϴ�.");
            result = "���� ���θ��� ������ ��ġ���� �ʽ��ϴ�.%";
            protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_��Ǻм������ȸ����);
            protocol.setString(result);
            output.write(protocol.getPacket());
         }
      } catch (Exception e) {
         System.out.println(e.getMessage());
         result = "�ش� ��ġ�� ���� ������ �����ϴ�.%";
         protocol = new Protocol(Protocol.TYPE6_VIEW_RES, Protocol.T6_��Ǻм������ȸ����);
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