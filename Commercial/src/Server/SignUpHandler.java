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
			String sql = "insert into ���������(���̵�, ��й�ȣ) values('" + id + "', '" + pwd + "')";
			stmt.executeUpdate(sql);
			protocol = new Protocol(Protocol.TYPE2_SIGNUP_RES, Protocol.T2_ȸ�����Խ���);
			output.write(protocol.getPacket());
		} catch (Exception e) {
			protocol = new Protocol(Protocol.TYPE2_SIGNUP_RES, Protocol.T2_ȸ�����԰���);
			output.write(protocol.getPacket());
		}
	}
}
