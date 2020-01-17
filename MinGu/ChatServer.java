package chat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JLabel;
public class ChatServer implements Runnable{
	private JLabel jLabel1 = new JLabel();
    ServerSocket ss;
    Service service;
    ArrayList<Service> list = new ArrayList();
       
       public ChatServer() {
              try {
                     ss = new ServerSocket(7777);
                     System.out.println("ä�� ���� ���� ��...");
                     new Thread(this).start();
              } catch (Exception e) {
                     System.out.println("ChatServer err : " + e);
              }
       }
       
       @Override
       public void run() {  //ChatServer�� run
              while(true) {
                     try {
                           Socket socket = ss.accept();
                           service = new Service(socket);
                           service.start();
                           service.chat_name = service.in.readLine();  //�������� �̸� �б�
                           //System.out.println(service.chat_name);
                           
                           service.messageSend("/c" + service.chat_name);
                           for(Service ser : list) {
                                  ser.messageSend("/c" + service.chat_name);
                                  service.messageSend("/c" + service.chat_name);
                           }
                           
                           list.add(service);
                     } catch (Exception e) {
                           System.out.println("ChatServer run err :" + e);
                     }
              }
       }
       
       //client �� ó���� ���� ���� Ŭ����
       class Service extends Thread {
              String chat_name;
              BufferedReader in;
              OutputStream out;
              Socket socket;
              public Service(Socket socket) {
                     try {
                           this.socket = socket;
                           
                           in = new BufferedReader(
                                         new InputStreamReader(socket.getInputStream(), "euc-kr"));
                           out = socket.getOutputStream();
                     } catch (Exception e) {
                           System.out.println("Service err : " + e);
                     }
              }
              
              @Override
              public void run() {
                     while(true) {
                           try {
                                  String msg = in.readLine();
                                  
                                  if(msg == null || msg.equals("")) continue;
                                  
                                  if(msg.charAt(0) == '/') {
                                         if(msg.charAt(1) == 'r') { //��ȭ�� ����
                                                messageAll("/r" + chat_name + "-" + msg.substring(2));
                                                chat_name = msg.substring(2);
                                         }else if(msg.charAt(1) == 'q') { //����
                                                try {
                                                       messageAll("/q" + chat_name);
                                                       list.remove(this);
                                                       in.close();
                                                       out.close();
                                                       socket.close();
                                                } catch (Exception e) {
                                                       // TODO: handle exception
                                                }
                                                break;
                                                }
                                  }else {  //�Ϲ� �޼���
                                	  messageAll(chat_name + ">" + msg);
                                  }
                           } catch (Exception e) {
                                  break;
                           }
                     }
              }
              
              public void messageAll(String msg) {
                     try {
                           for (int i = 0; i < list.size(); i++) {
                                  Service ser = list.get(i);
                                  ser.messageSend(msg);
                           }
                     } catch (Exception e) {
                           System.out.println("messageAll err : " + e);
                     }
              }
              
              public void messageSend(String msg) {
                     try {
                           out.write((msg + "\n").getBytes("euc-kr"));
                     } catch (Exception e) {
                           System.out.println("messageSend err : " + e);
                     }
              }
       }
       
       public static void main(String[] args) {
              new ChatServer();
       }
}