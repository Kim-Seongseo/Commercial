package Server;

import java.io.*;
import java.sql.*;

public class SignUpHandler {
	InputStream input;
	OutputStream output;
	Connection conn;
	Statement stmt;

	SignUpHandler(InputStream input, OutputStream output, Connection conn) {
		this.input = input;
		this.output = output;
		this.conn = conn;
	}

	void signUp(Protocol protocol) throws IOException, SQLException {
		String[] temp = protocol.getString().split("/");
		String id = temp[0];
		String pwd = temp[1];

		stmt = conn.createStatement();
		try {
			String sql = "insert into 사용자정보(아이디, 비밀번호) values('" + id + "', '" + pwd + "')";
			stmt.executeUpdate(sql);
			protocol = new Protocol(Protocol.TYPE2_SIGNUP_RES, Protocol.T2_회원가입승인);
			output.write(protocol.getPacket());
		} catch (Exception e) {
			protocol = new Protocol(Protocol.TYPE2_SIGNUP_RES, Protocol.T2_회원가입거절);
			output.write(protocol.getPacket());
		}
	}
}
