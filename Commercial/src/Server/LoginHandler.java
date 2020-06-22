package Server;

import java.io.*;
import java.sql.*;

public class LoginHandler {
    InputStream input;
    OutputStream output;
    Connection conn;
    Statement stmt;
    String Loginid = "";
    LoginHandler(InputStream input, OutputStream output, Connection conn){
        this.input = input;
        this.output = output;
        this.conn = conn;
    }


    public String getId() {
        return Loginid;
    }
    void login(Protocol protocol) throws IOException, SQLException {
        String[] temp = protocol.getString().split("/");
        String id = temp[0];
        String pwd = temp[1];

        stmt = conn.createStatement();

        String sql = "SELECT * FROM ���������";
        ResultSet rs = stmt.executeQuery(sql);

        boolean isUser = false;
        while(rs.next()) {
            if(id.equals(rs.getString("���̵�"))&&pwd.equals(rs.getString("��й�ȣ"))){isUser = true;}
        }

        if (isUser) {
            protocol = new Protocol(Protocol.TYPE4_LOGIN_RES, Protocol.T4_�α��ν���);
            output.write(protocol.getPacket());
           Loginid = id;
        } else {
           protocol = new Protocol(Protocol.TYPE4_LOGIN_RES, Protocol.T4_�α��ΰ���);
            output.write(protocol.getPacket());
        }
    }
}