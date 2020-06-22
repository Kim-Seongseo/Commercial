package Client;

import java.nio.ByteBuffer;

public class Protocol {

   public static final int UNDEFINED = -1;
   public static final int EXIT = 0;

   // Ÿ��
   public static final int TYPE1_SIGNUP_REQ = 1;   //ȸ������ ��û
   public static final int TYPE2_SIGNUP_RES = 2;   //ȸ������ ����
   public static final int TYPE3_LOGIN_REQ = 3;   //�α��� ��û
   public static final int TYPE4_LOGIN_RES = 4;   //�α��� ����
   public static final int TYPE5_VIEW_REQ = 5;      //��ȸ ��û
   public static final int TYPE6_VIEW_RES = 6;      //��ȸ ����

   // Ÿ��2 �ڵ�
   public static final int T2_ȸ�����Խ��� = 0;
   public static final int T2_ȸ�����԰��� = 1;
   
   // Ÿ��4 �ڵ� 
   public static final int T4_�α��ν��� = 0;
   public static final int T4_�α��ΰ��� = 1;
   
   // Ÿ��5���ڵ�
   public static final int T5_�Ĵ�ǥ��ȸ = 0;
   public static final int T5_�����α�ǥ��ȸ = 1;
   public static final int T5_��Ǻм������ȸ = 2;
   
   // Ÿ��6���ڵ�
   public static final int T6_�Ĵ�ǥ��ȸ���� = 0;
   public static final int T6_�Ĵ�ǥ��ȸ���� = 1;
   public static final int T6_�����α�ǥ��ȸ���� = 2;
   public static final int T6_�����α�ǥ��ȸ���� = 3;
   public static final int T6_��Ǻм������ȸ���� = 4;
   public static final int T6_��Ǻм������ȸ���� = 5;

   // ����
   public static final int LEN_HEADER = 8;

   // ==========================================================================================================
   // �������
   public byte[] header;
   public byte[] body;

   // ������
   // Ÿ��, �ڵ�, �ٵ𷩽�, flag, last, seq_num
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