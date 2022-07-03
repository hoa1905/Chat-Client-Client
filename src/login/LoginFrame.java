package login;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import client.MainFrame;
import tags.Encode;
import tags.Tags;

public class LoginFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtIPServer;
    private JTextField txtPort;
    private JTextField txtUserName;
    private JPasswordField passwordField;
    private JButton btnNewButton;
	private JButton btnThot;
    private JPanel contentPane;
    
	Socket mngSocket = null;
	String mngServer = "";
	int mngPort = 0;
    
	int port;
	String IP, userName;
	JButton btnConnectServer;
	String file = System.getProperty("user.dir") + "\\Server.txt";
	List<String> listServer = new ArrayList<>();



    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginFrame ah = new LoginFrame();
                    ah.setVisible(true);		
                    ah.setLocationRelativeTo(null);	
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
    
	void updateServer(String IP, String port) {
		txtIPServer.setText(IP);
		txtPort.setText(port);
	}

	String[] readFileServer() throws FileNotFoundException {
		// Read file
		System.out.println(file);
		Scanner scanner = new Scanner(new File(file));
		while (scanner.hasNext()) {
			String server = scanner.nextLine();
			System.out.println(server + "-" + port);
			listServer.add(server);
		}
		scanner.close();
		String[] array = listServer.toArray(new String[0]);
		return array;
	}

    /**
     * Create the frame.
     */
    public LoginFrame() {
        setTitle("Login Frame");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 315);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lbldangNhap = new JLabel("Connect Server");
		lbldangNhap.setHorizontalAlignment(SwingConstants.CENTER);
		lbldangNhap.setFont(new Font("Times New Roman", Font.BOLD, 35));
		lbldangNhap.setBounds(0, 10, 436, 32);
		contentPane.add(lbldangNhap);
		
		JLabel lblIPServer = new JLabel("IP Address  Server:");
		lblIPServer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblIPServer.setHorizontalAlignment(SwingConstants.RIGHT);
		lblIPServer.setBounds(10, 64, 170, 29);
		contentPane.add(lblIPServer);

		txtIPServer = new JTextField();
		txtIPServer.setText("127.0.0.1");
		txtIPServer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtIPServer.setBounds(201, 63, 192, 29);
		contentPane.add(txtIPServer);
		txtIPServer.setColumns(10);
		
		JLabel lblPort = new JLabel("Port:");
        lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPort.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblPort.setBounds(96, 103, 84, 28);
        contentPane.add(lblPort);
        
        txtPort = new JTextField();
        txtPort.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        txtPort.setColumns(10);
        txtPort.setBounds(201, 102, 192, 29);
        contentPane.add(txtPort);
        
        JLabel lblUserName = new JLabel("User name:");
        lblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUserName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        lblUserName.setBounds(48, 141, 132, 28);
        contentPane.add(lblUserName);
        
        txtUserName = new JTextField();
        txtUserName.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        txtUserName.setColumns(10);
        txtUserName.setBounds(201, 140, 192, 29);
        contentPane.add(txtUserName);
		
		JLabel lblmatKhau = new JLabel("Password:");
		lblmatKhau.setHorizontalAlignment(SwingConstants.RIGHT);
		lblmatKhau.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblmatKhau.setBounds(96, 175, 84, 29);
		contentPane.add(lblmatKhau);

		passwordField = new JPasswordField();
		passwordField.setEchoChar('*');
		passwordField.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		passwordField.setBounds(201, 174, 192, 29);
		contentPane.add(passwordField);
		
		JLabel txtCheck = new JLabel("Show password");
		txtCheck.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtCheck.setBackground(new Color(240, 248, 255));
		txtCheck.setBounds(246, 209, 147, 21);
		contentPane.add(txtCheck);
		
		JCheckBox checkBox = new JCheckBox();
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setFont(new Font("Times New Roman", Font.BOLD, 18));
		checkBox.setBackground(new Color(240, 248, 255));
		  checkBox.addActionListener(new ActionListener() {
		   public void actionPerformed(ActionEvent e) {
		    if(checkBox.isSelected()){
		     txtCheck.setText("Hide password");
		     passwordField.setEchoChar((char)0);
		    }else{
		     txtCheck.setText("Show password");
		     passwordField.setEchoChar('*');
		    }
		   }
		  });
		  checkBox.setBounds(211, 209, 28, 21);
		  contentPane.add(checkBox);


		btnNewButton = new JButton("Connect");
		btnNewButton.setForeground(new Color(0, 0, 255));
		btnNewButton.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnNewButton.setBounds(134, 239, 110, 29);
        btnNewButton.addActionListener(new ActionListener() {

        	public void actionPerformed(ActionEvent e) {
    			userName = txtUserName.getText();
    			IP = txtIPServer.getText();
				String tenTaiKhoan = txtUserName.getText();
                String matKhau = passwordField.getText();
				mngServer = txtIPServer.getText();
				mngPort = Integer.parseInt(txtPort.getText());
				try {
                    Connection connection = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/swing_demo",
                        "root", "Hoa30025091");

                    PreparedStatement st = (PreparedStatement) connection
                        .prepareStatement("Select name, password from client_info where name=? and password=?");

                    st.setString(1, tenTaiKhoan);
                    st.setString(2, matKhau);
                    ResultSet rs = st.executeQuery();
                    Component btnConnect = null;
					if (rs.next()) {
						try {
							// Login
							Random rd = new Random();
							int portPeer = 10000 + rd.nextInt() % 1000;
							InetAddress ipServer = InetAddress.getByName(IP);
							int portServer = Integer.parseInt(txtPort.getText());
							Socket socketClient = new Socket(ipServer, portServer);

							String msg = Encode.getCreateAccount(userName, Integer.toString(portPeer));
							ObjectOutputStream serverOutputStream = new ObjectOutputStream(socketClient.getOutputStream());
							serverOutputStream.writeObject(msg);
							serverOutputStream.flush();
							ObjectInputStream serverInputStream = new ObjectInputStream(socketClient.getInputStream());
							msg = (String) serverInputStream.readObject();

							socketClient.close();
							mngSocket = new Socket();
	    					if (mngSocket != null) {
	    						JOptionPane.showMessageDialog(btnConnect, "Successful connect");
	    					}
							if (msg.equals(Tags.SESSION_DENY_TAG)) {
								return;
							}
							System.out.println("Port Server Login: " + portServer);
							new MainFrame(IP, portPeer, userName, msg, portServer);
							dispose();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(btnConnect, "User name or password invalid");
					}																																	
				} catch (Exception e2) {
					e2.printStackTrace();
					
				}					
			}
		});
        
        contentPane.add(btnNewButton);
        
		btnThot = new JButton("Exit");
		btnThot.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnThot.setForeground(new Color(0, 0, 255));
		btnThot.setFont(new Font("Times New Roman", Font.BOLD, 18));
		btnThot.setBounds(271, 240, 85, 28);
		contentPane.add(btnThot);
           
    }
}