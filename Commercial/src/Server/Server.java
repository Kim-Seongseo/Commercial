package Server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
   // ������ ��Ʈ�� �����մϴ�.
   private static final int PORT = 9090;
   // ������ Ǯ�� �ִ� ������ ������ �����մϴ�.
   private static final int THREAD_CNT = 50;
   private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);

   public static void main(String[] args) {
      ServerSocket serverSocket = null;
      try {

         // �������� ����
         serverSocket = new ServerSocket(PORT);

         // ������ ȣ��Ʈ�� ��Ʈ�� binding
         String localHostAddress = InetAddress.getLocalHost().getHostAddress();
         System.out.println("[server] binding! \naddress:" + localHostAddress + ", port:" + PORT);

         System.out.println("<Ŭ���̾�Ʈ ���� �����...>");

         // ���ϼ����� ����ɶ����� ���ѷ���
         while (true) {
            // ���� ���� ��û�� �ö����� ����մϴ�.
            Socket socket = serverSocket.accept();

            // ������ �Ǿ��ٴ� �޽��� ���
            InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
            int remoteHostPort = remoteSocketAddress.getPort();
            System.out.println("[server] connected! \nconnected socket address:" + remoteHostName + ", port:"
                  + remoteHostPort);

            try {
               // ��û�� ���� ������ Ǯ�� ������� ������ �־��ݴϴ�.
               // ���Ĵ� ������ ������ ó���մϴ�.
               threadPool.execute(new ConnectionWrap(socket));
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}

class ConnectionWrap implements Runnable {

   Socket socket = null;
   int cnt;

   public ConnectionWrap(Socket socket) {
      this.socket = socket;
   }

   @Override
   public void run() {

      try {
         // ��Ʈ��
         OutputStream output = socket.getOutputStream();
         InputStream input = socket.getInputStream();

         // DB connection
         // ?useSSL=false
         Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/commercial?useSSL=false", "root", "123");

         // ����ó����簴ü����
         SignUpHandler signUpHandler = new SignUpHandler(input, output, conn);
         LoginHandler loginHandler = new LoginHandler(input, output, conn);
         ViewHandler viewHandler = new ViewHandler(input, output, conn);

         // ��������
         Protocol protocol;
         String id = "";
         // �л�,�α��� ���� ����
         boolean exit = false;

         // ������Ŷó��
         while (true) {
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

            InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();

            switch (protocolType) {
            case Protocol.EXIT: // ���α׷� ���� ����
               input.close();
               output.close();
               socket.close();
               System.out.println("[" + remoteHostName + " Ŭ���̾�Ʈ ��������]");
               exit = true;
               break;
            case Protocol.TYPE1_SIGNUP_REQ: // �α��� ������û ���� -> �α��� ���� ��û�� ���� ���� �߽�
               System.out.println("[" + remoteHostName + " Ŭ���̾�Ʈ�� ȸ������ ��û�� ����]");
               signUpHandler.signUp(protocol);
               break;
            case Protocol.TYPE3_LOGIN_REQ: // �α��� ������û ���� -> �α��� ���� ��û�� ���� ���� �߽�
               System.out.println("[" + remoteHostName + " Ŭ���̾�Ʈ�� �α��� ��û�� ����]");
               loginHandler.login(protocol);
               id = loginHandler.getId();
               break;
            case Protocol.TYPE5_VIEW_REQ: // ��ȸ��û -> ��ȸ��û�� ���� ���� �߽�
               System.out.println("[" + remoteHostName + " Ŭ���̾�Ʈ�� ��ȸ��û �޼����� ����]");
               switch (protocolCode) {
               case Protocol.T5_�Ĵ�ǥ��ȸ:
                  viewHandler.CODE1(protocol);
                  break;
               case Protocol.T5_�����α�ǥ��ȸ:
                  viewHandler.CODE2(protocol);
                  break;
               case Protocol.T5_��Ǻм������ȸ:
                  viewHandler.CODE3(protocol, id);
                  break;
               }
               break;
            }
         }
      } catch (IOException e) {
      } catch (ClassNotFoundException d) {
      } catch (SQLException a) {
      }
   }
}