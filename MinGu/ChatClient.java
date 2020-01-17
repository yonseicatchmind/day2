package chat;

import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.io.*;
import java.net.*;
public class ChatClient extends JFrame implements ActionListener, Runnable {
       private JLabel jLabel1 = new JLabel();
       private JLabel jLabel2 = new JLabel();
       private JLabel jLabel3 = new JLabel();
       private JLabel jLabel4 = new JLabel();
       private JTextField txtname1 = new JTextField();
       private JTextField txtname2 = new JTextField();
       private JButton btnconn = new JButton();
       private JTextArea txtarea = new JTextArea();
       private JScrollPane jScrollPane1 = new JScrollPane();
       private JTextField txtsend = new JTextField();
       private JButton btnok = new JButton();
       private JLabel lblinwon = new JLabel();
       private JButton btnclose = new JButton();
       private List list = new List();
       private JButton btnchange = new JButton();
       private BufferedReader in;
       private OutputStream out;
       private Socket soc;
       int count = 0; // ���� �ο���
       public ChatClient() {
              try {
                     jbInit();
                     addListener();
              } catch (Exception e) {
                     e.printStackTrace();
              }
       }
       private void jbInit() throws Exception {
    	   this.getContentPane().setLayout(null);
           this.setSize(new Dimension(1280, 800));
           this.setTitle("ĳġ���ε�");
           this.setBackground(new Color(198, 214, 255));
           btnchange.setText("��ȭ�� ����");
           btnchange.setBounds(new Rectangle(15, 10, 200, 40));
           btnchange.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           jLabel1.setText("�г���:");
           jLabel1.setFont(new Font("������� ExtraBold",Font.BOLD,40));
           jLabel1.setBounds(new Rectangle(15, 60, 140, 40));
           txtname1.setBounds(new Rectangle(150, 60, 140, 40));
           txtname1.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           jLabel4.setText("���ھ�:");
           jLabel4.setFont(new Font("������� ExtraBold",Font.BOLD,40));
           jLabel4.setBounds(new Rectangle(15, 100, 140, 40));
           txtname2.setBounds(new Rectangle(150, 100, 140, 40));
           txtname2.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           btnconn.setText("����");
           btnconn.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           btnconn.setBounds(new Rectangle(15, 140, 130, 40));
           btnclose.setText("������");
           btnclose.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           btnclose.setBounds(new Rectangle(150, 140, 140, 40));
           lblinwon.setText("0");
           lblinwon.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           lblinwon.setBounds(new Rectangle(203, 190, 88, 42));
           lblinwon.setBackground(new Color(198, 198, 200));
           lblinwon.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
           lblinwon.setHorizontalAlignment(SwingConstants.CENTER);
           lblinwon.setHorizontalTextPosition(SwingConstants.CENTER);
           jLabel3.setText("���� �ο�:");
           jLabel3.setFont(new Font("������� ExtraBold",Font.BOLD,40));
           jLabel3.setBounds(new Rectangle(15, 190, 210, 40));
           jLabel2.setText("������ ���");
           jLabel2.setFont(new Font("������� ExtraBold",Font.BOLD,40));
           jLabel2.setBounds(new Rectangle(15, 240, 250, 40));
           list.setBounds(new Rectangle(15, 290, 280, 230)); //���� �ο� ����Ʈ ��ġ
           list.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           jScrollPane1.setBounds(new Rectangle(295, 530, 960, 155)); // ä��â ��ġ
           txtarea.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           txtsend.setBounds(new Rectangle(295, 690, 850, 50)); //ä�� �Է� ��ġ
           txtsend.setFont(new Font("������� ExtraBold",Font.BOLD,40));
           btnok.setText("Ȯ��");
           btnok.setFont(new Font("������� ExtraBold",Font.BOLD,30));
           btnok.setBounds(new Rectangle(1150, 690, 103, 50));
           ButtonGroup group = new ButtonGroup();
           this.getContentPane().add(btnchange, null);
           this.getContentPane().add(jLabel1, null);
           this.getContentPane().add(jLabel2, null);
           this.getContentPane().add(jLabel3, null);
           this.getContentPane().add(jLabel4, null);
           this.getContentPane().add(txtname1, null);
           this.getContentPane().add(txtname2, null);
           this.getContentPane().add(list, null);
           this.getContentPane().add(btnclose, null);
           this.getContentPane().add(lblinwon, null);
           this.getContentPane().add(btnok, null);
           this.getContentPane().add(txtsend, null);
           jScrollPane1.getViewport().add(txtarea, null);
           this.getContentPane().add(jScrollPane1, null);
           this.getContentPane().add(btnconn, null);
       }
       public void addListener() {
              txtname1.addActionListener(this);
              txtname2.addActionListener(this);
              txtsend.addActionListener(this);
              btnok.addActionListener(this);
              btnconn.addActionListener(this);
              btnclose.addActionListener(this);
              btnchange.addActionListener(this);
       }
       public void actionPerformed(ActionEvent e) {
              if(e.getSource() == txtname1 || e.getSource() == btnconn) {
                     //��ȭ�� �Է� �� ����
                     if(txtname1.getText().equals("")) {
                           JOptionPane.showMessageDialog(this, "��ȭ�� �Է�");
                           txtname1.requestFocus();
                           return;
                     }
                     
                     try {
                           soc = new Socket("192.168.35.31", 7777);
                           in = new BufferedReader(
                                         new InputStreamReader(soc.getInputStream(), "euc-kr"));
                           out = soc.getOutputStream();
                           out.write((txtname1.getText() + "\n").getBytes("euc-kr"));
                           new Thread(this).start();  //run()�� ȣ��
                     } catch (Exception e2) {
                           System.out.println("���� ����:" + e2);
                     }
              }else if(e.getSource() == txtsend || e.getSource() == btnok) {
                     //�޼��� ����
                     try {
                    	 out.write((txtsend.getText() + "\n").getBytes("euc-kr"));
                         
                    	 txtsend.setText("");
                         txtsend.requestFocus();
                     } catch (Exception e2) {
                           System.out.println("�޼��� ���� ����:" + e2);
                     }
              }else if(e.getSource() == btnchange) {
                     //��ȭ�� ����
                     if(btnchange.getText().equals("��ȭ�� ����")) {
                           btnchange.setText("����Ȯ��");
                           txtname1.setEditable(true);
                           txtname1.requestFocus();
                     }else {
                           btnchange.setText("��ȭ�� ����");
                           txtname1.setEditable(false);
                           try {
                                  out.write(("/r" + txtname1.getText() + "\n").getBytes("euc-kr"));
                           } catch (Exception e2) {
                                  System.out.println("��ȭ�� ���� ����:" + e2);
                           }
                     }
              }else if(e.getSource() == btnclose) {
                     //������
                     try {
                           out.write(("/q\n").getBytes());
                           in.close();
                           out.close();
                           soc.close();
                     } catch (Exception e2) {
                           System.out.println("������ ����:" + e2);
                     } finally {
                           System.exit(0);
                     }
              }
       }
       
       @Override
       public void run() {
    	   while (true) {
                     try {
                           String msg = in.readLine();  //�����κ��� �޼��� ����
                           if(msg == null || msg.equals("")) return;
                           
                           if(msg.charAt(0) == '/') {
                                  if(msg.charAt(1) == 'c') { //��ȭ��(����)
                                         //  /cȫ�浿
                                         list.add(msg.substring(2), count);
                                         count++;
                                         lblinwon.setText(String.valueOf(count));
                                         txtarea.append("**" + msg.substring(2) + "���� �����߽��ϴ�.\n");
                                         txtname1.setEditable(false);  //��ȭ�� �Է� �Ұ�
                                         btnconn.setEnabled(false);
                                  }else if(msg.charAt(1) == 'q') { //����
                                         txtarea.append("^^" + msg.substring(2) + "���� �����߽��ϴ�.\n");
                                         String cname = msg.substring(2);
                                         
                                         for (int i = 0; i < count; i++) {
                                                if(cname.equals(list.getItem(i))) {
                                                       list.remove(i);
                                                       count--;
                                                       lblinwon.setText(String.valueOf(count));
                                                       break;
                                                }
                                         }
                                  }else if(msg.charAt(1) == 'r') { //��ȭ�� ����
                                         //   /roldName-newName
                                         String oldName = msg.substring(2, msg.indexOf('-'));
                                         String newName = msg.substring(msg.indexOf('-') + 1);
                                         txtarea.append("*" + oldName + "���� ��ȭ���� " +
                                                       newName + "���� ����ƽ��ϴ�\n");
                                         
                                         for (int i = 0; i < count; i++) {
                                                if(oldName.equals(list.getItem(i))) {
                                                       list.replaceItem(newName, i);
                                                       break;
                                                }
                                         }
                                  }
                           }else { //�Ϲ� �޼���
                                  txtarea.append(msg + "\n");
                           }
                     } catch (Exception e) {
                           System.out.println("run err : " + e);
                     }                    
              }
       }
       
       public static void main(String args[]) {
              ChatClient fr = new ChatClient();
              fr.getPreferredSize();
              fr.setLocation(200, 200);
              fr.setVisible(true);
              fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       }
}