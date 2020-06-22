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

        String sql = "SELECT * FROM 사용자정보";
        ResultSet rs = stmt.executeQuery(sql);

        boolean isUser = false;
        while(rs.next()) {
            if(id.equals(rs.getString("아이디"))&&pwd.equals(rs.getString("비밀번호"))){isUser = true;}
        }

        if (isUser) {
            protocol = new Protocol(Protocol.TYPE4_LOGIN_RES, Protocol.T4_로그인승인);
            output.write(protocol.getPacket());
           Loginid = id;
        } else {
           protocol = new Protocol(Protocol.TYPE4_LOGIN_RES, Protocol.T4_로그인거절);
            output.write(protocol.getPacket());
        }
    }
}