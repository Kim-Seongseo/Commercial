package Server;

import java.io.*;
import java.net.*;
import java.sql.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
   // 연결할 포트를 지정합니다.
   private static final int PORT = 9090;
   // 스레드 풀의 최대 스레드 개수를 지정합니다.
   private static final int THREAD_CNT = 50;
   private static ExecutorService threadPool = Executors.newFixedThreadPool(THREAD_CNT);

   public static void main(String[] args) {
      ServerSocket serverSocket = null;
      try {

         // 서버소켓 생성
         serverSocket = new ServerSocket(PORT);

         // 소켓을 호스트의 포트와 binding
         String localHostAddress = InetAddress.getLocalHost().getHostAddress();
         System.out.println("[server] binding! \naddress:" + localHostAddress + ", port:" + PORT);

         System.out.println("<클라이언트 접속 대기중...>");

         // 소켓서버가 종료될때까지 무한루프
         while (true) {
            // 소켓 접속 요청이 올때까지 대기합니다.
            Socket socket = serverSocket.accept();

            // 연결이 되었다는 메시지 출력
            InetSocketAddress remoteSocketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
            String remoteHostName = remoteSocketAddress.getAddress().getHostAddress();
            int remoteHostPort = remoteSocketAddress.getPort();
            System.out.println("[server] connected! \nconnected socket address:" + remoteHostName + ", port:"
                  + remoteHostPort);

            try {
               // 요청이 오면 스레드 풀의 스레드로 소켓을 넣어줍니다.
               // 이후는 스레드 내에서 처리합니다.
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
         // 스트림
         OutputStream output = socket.getOutputStream();
         InputStream input = socket.getInputStream();

         // DB connection
         // ?useSSL=false
         Class.forName("com.mysql.jdbc.Driver");
         Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/commercial?useSSL=false", "root", "123");

         // 서버처리담당객체생성
         SignUpHandler signUpHandler = new SignUpHandler(input, output, conn);
         LoginHandler loginHandler = new LoginHandler(input, output, conn);
         ViewHandler viewHandler = new ViewHandler(input, output, conn);

         // 프로토콜
         Protocol protocol;
         String id = "";
         // 학생,로그인 관련 변수
         boolean exit = false;

         // 받은패킷처리
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
            case Protocol.EXIT: // 프로그램 종료 수신
               input.close();
               output.close();
               socket.close();
               System.out.println("[" + remoteHostName + " 클라이언트 정상종료]");
               exit = true;
               break;
            case Protocol.TYPE1_SIGNUP_REQ: // 로그인 인증요청 수신 -> 로그인 인증 요청에 대한 응답 발신
               System.out.println("[" + remoteHostName + " 클라이언트가 회원가입 요청을 보냄]");
               signUpHandler.signUp(protocol);
               break;
            case Protocol.TYPE3_LOGIN_REQ: // 로그인 인증요청 수신 -> 로그인 인증 요청에 대한 응답 발신
               System.out.println("[" + remoteHostName + " 클라이언트가 로그인 요청을 보냄]");
               loginHandler.login(protocol);
               id = loginHandler.getId();
               break;
            case Protocol.TYPE5_VIEW_REQ: // 조회요청 -> 조회요청에 대한 응답 발신
               System.out.println("[" + remoteHostName + " 클라이언트가 조회요청 메세지를 보냄]");
               switch (protocolCode) {
               case Protocol.T5_식당표조회:
                  viewHandler.CODE1(protocol);
                  break;
               case Protocol.T5_유동인구표조회:
                  viewHandler.CODE2(protocol);
                  break;
               case Protocol.T5_상권분석결과조회:
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