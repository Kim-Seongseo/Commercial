package Client;

import java.nio.ByteBuffer;

public class Protocol {

   public static final int UNDEFINED = -1;
   public static final int EXIT = 0;

   // 타입
   public static final int TYPE1_SIGNUP_REQ = 1;   //회원가입 요청
   public static final int TYPE2_SIGNUP_RES = 2;   //회원가입 응답
   public static final int TYPE3_LOGIN_REQ = 3;   //로그인 요청
   public static final int TYPE4_LOGIN_RES = 4;   //로그인 응답
   public static final int TYPE5_VIEW_REQ = 5;      //조회 요청
   public static final int TYPE6_VIEW_RES = 6;      //조회 응답

   // 타입2 코드
   public static final int T2_회원가입승인 = 0;
   public static final int T2_회원가입거절 = 1;
   
   // 타입4 코드 
   public static final int T4_로그인승인 = 0;
   public static final int T4_로그인거절 = 1;
   
   // 타입5의코드
   public static final int T5_식당표조회 = 0;
   public static final int T5_유동인구표조회 = 1;
   public static final int T5_상권분석결과조회 = 2;
   
   // 타입6의코드
   public static final int T6_식당표조회승인 = 0;
   public static final int T6_식당표조회거절 = 1;
   public static final int T6_유동인구표조회승인 = 2;
   public static final int T6_유동인구표조회거절 = 3;
   public static final int T6_상권분석결과조회승인 = 4;
   public static final int T6_상권분석결과조회거절 = 5;

   // 랭스
   public static final int LEN_HEADER = 8;

   // ==========================================================================================================
   // 멤버변수
   public byte[] header;
   public byte[] body;

   // 생성자
   // 타입, 코드, 바디랭스, flag, last, seq_num
   public Protocol() {
      header = new byte[LEN_HEADER];
   }

   public Protocol(int protocolType) {
      this();
      header[0] = (byte) protocolType;
   }

   public Protocol(int protocolType, int protocolCode) {
      this(protocolType);
      header[1] = (byte) protocolCode;
   }

   public Protocol(int protocolType, int protocolCode, String data) {
      this(protocolType, protocolCode);
      body = new byte[data.getBytes().length];
      intToByte(data.getBytes().length, header, 2);
      System.arraycopy(data.getBytes(), 0, body, 0, data.getBytes().length);
   }

   public Protocol(int protocolType, int protocolCode, byte[] data) {
      this(protocolType, protocolCode);
      body = new byte[data.length];
      intToByte(data.length, header, 2);
      System.arraycopy(data, 0, body, 0, data.length);
   }

   public Protocol(int protocolType, int protocolCode, int flag, int last, int seqnum, String data) {
      this(protocolType, protocolCode, data);
      header[4] = (byte) flag;
      header[5] = (byte) last;
      intToByte(seqnum, header, 6);
   }

   public Protocol(int protocolType, int protocolCode, int flag, int last, int seqnum, byte[] data) {
      this(protocolType, protocolCode, data);
      header[4] = (byte) flag;
      header[5] = (byte) last;
      intToByte(seqnum, header, 6);
   }

   public void intToByte(int bd, byte[] header, int offset) {
      short body = (short) bd;
      byte[] temp = ByteBuffer.allocate(2).putShort(body).array();
      for (int i = 0; i < 2; i++)
         header[offset + i] = temp[i];
   }

   public int byteToInt(byte[] header, int offset) {
      int result = 0;

      result = (header[offset] & 0xff) << 8 | (header[offset + 1] & 0xff);

      return result;
   }

   // Type
   public int getType() {
      return (int) header[0];
   }

   public void setType(int Type) {
      header[0] = (byte) Type;
   }

   // Code
   public int getCode() {
      return (int) header[1];
   }

   public void setCode(int Code) {
      header[1] = (byte) Code;
   }

   // length
   public int getLength() {
      return byteToInt(header, 2);
   }

   public void setLength(int Length) {
      intToByte(Length, header, 2);
   }

   // flag
   public int getFlag() {
      return (int) header[4];
   }

   public void setFlag(int Flag) {
      header[4] = (byte) Flag;
   }

   // last
   public int getLast() {
      return (int) header[5];
   }

   public void setLast(int Last) {
      header[5] = (byte) Last;
   }

   // seq
   public int getSeqnum() {
      return byteToInt(header, 6);
   }

   public void setSeqnum(int Seqnum) {
      intToByte(Seqnum, header, 6);
   }

   // Packet
   public byte[] getPacket() {
      if (body != null) {
         byte[] temp = new byte[header.length + body.length];
         System.arraycopy(header, 0, temp, 0, LEN_HEADER);
         System.arraycopy(body, 0, temp, LEN_HEADER, body.length);
         return temp;
      } else {
         return header;
      }
   }

   public void setPacket(byte[] header, byte[] body) {
      this.header = header;
      this.body = body;
      intToByte(body.length, header, 2);
   }

   // header
   public byte[] getHeader() {
      return header;
   }

   public void setHeader(byte[] header) {
      this.header = header;
   }

   // string
   public String getString() {
      return new String(body);
   }

   public void setString(String data) {
      body = new byte[data.getBytes().length];
      System.arraycopy(data.getBytes(), 0, body, 0, data.getBytes().length);
      intToByte(data.getBytes().length, header, 2);
   }
}