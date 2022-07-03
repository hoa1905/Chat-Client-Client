package server;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.net.Inet4Address;
//import java.net.UnknownHostException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import data.Peer;
import javax.swing.SwingConstants;

public class ServerFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtIP;
	private JTextField txtPort;
	private static JTextArea txtMessage;
	public static int port = 5500;
	static ServerCore server;
	JButton btnStopServer, btnStartServer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void updateMessage(String msg) {
		txtMessage.append(msg + "\n");
	}

	public static void updateNumberClient() {
		displayUser();

	}

	public static void decreaseNumberClient() {
		displayUser();

	}

	static void displayUser() {
		txtMessage.setText("");
		ArrayList<Peer> list = server.getListUser();
		for (int i = 0; i < list.size(); i++) {
			txtMessage.append((i + 1) + " " + list.get(i).getName() + "\n");
		}
	}

	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		setResizable(false);
		setTitle("Server Frame");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 536, 555);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Manager Chatter");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
		lblNewLabel.setBounds(0, 0, 522, 76);
		contentPane.add(lblNewLabel);

		JPanel panel = new JPanel();
		panel.setBounds(31, 100, 279, 34);
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		JLabel lblIPaddress = new JLabel("IP Address:");
		lblIPaddress.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panel.add(lblIPaddress);

		panel.add(new JPanel());
		txtIP = new JTextField();
		txtIP.setBackground(Color.WHITE);
		txtIP.setForeground(Color.BLACK);
		txtIP.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		panel.add(txtIP);
		txtIP.setColumns(10);
		txtIP.setText("127.0.0.1");

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(31, 154, 279, 34);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblPort = new JLabel("Port:");
		lblPort.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPort.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblPort.setBounds(0, 0, 90, 34);
		panel_1.add(lblPort);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(92, 0, 10, 34);
		panel_1.add(panel_2);

		txtPort = new JTextField();
		txtPort.setBackground(Color.WHITE);
		txtPort.setForeground(Color.BLACK);
		txtPort.setText("5500");
		txtPort.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		txtPort.setBounds(103, 0, 175, 34);
		panel_1.add(txtPort);
		txtPort.setColumns(10);

		JPanel panel_6 = new JPanel();
		panel_6.setBorder(
				new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Users list", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		txtMessage = new JTextArea();
		txtMessage.setBackground(Color.WHITE);
		txtMessage.setForeground(Color.BLACK);
		txtMessage.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		panel_6.setBounds(31, 260, 456, 233);
		panel_6.setLayout(new GridLayout(0, 1, 0, 0));
		JScrollPane scrollPane = new JScrollPane(txtMessage);
		panel_6.add(scrollPane);
		contentPane.add(panel_6);
		
		btnStartServer = new JButton("Start Server");
		btnStartServer.setBounds(350, 100, 137, 31);
		contentPane.add(btnStartServer);
		btnStartServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					port = Integer.valueOf(txtPort.getText());
					server = new ServerCore(port);
					ServerFrame.updateMessage("Start server on port " + port);
				} catch (Exception e1) {
					ServerFrame.updateMessage("Start error");
					e1.printStackTrace();
				}
			}
		});
		btnStartServer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		
		btnStopServer = new JButton("Stop Server");
		btnStopServer.setBounds(350, 154, 137, 31);
		contentPane.add(btnStopServer);
		btnStopServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					server.stopserver();
					ServerFrame.updateMessage("Stop server");
				} catch (Exception ex) {
					ex.printStackTrace();
					ServerFrame.updateMessage("Stop server");
				}
			}
		});
		btnStopServer.setFont(new Font("Times New Roman", Font.PLAIN, 20));
	}

}
