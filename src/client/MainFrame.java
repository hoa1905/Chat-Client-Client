package client;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.net.Inet4Address;
import java.net.UnknownHostException;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import tags.Tags;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame implements WindowListener {

	private JPanel contentPane;
	private Client clientNode;
	private static String IPClient = "", nameUser = "", dataUser = "";
	private static int portClient = 0;
	private static JList<String> listActive;
	private static int portServer;
	private String name;
	static DefaultListModel<String> model = new DefaultListModel<>();
	String file = System.getProperty("user.dir") + "\\Server.txt";

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainFrame(String arg, int arg1, String name, String msg, int port_Server) throws Exception {
		IPClient = arg;
		portClient = arg1;
		nameUser = name;
		dataUser = msg;
		portServer = port_Server;
		System.out.println("Port Server Main UI: " + portServer);

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void updateFriendMainFrame(String msg) {
		model.addElement(msg);
	}

	public static void resetList() {
		model.clear();
	}

	/**
	 * Create the frame.
	 *
	 * @throws Exception
	 */
	public MainFrame() throws Exception {
		this.addWindowListener(this);
		setResizable(false);

		System.out.println("Port Server Main UI: " + portServer);
		updateFriendMainFrame("12");
		clientNode = new Client(IPClient, portClient, nameUser, dataUser, portServer);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 483, 441);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Chat Client");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setForeground(Color.BLACK);
		lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD, 35));
		lblNewLabel.setBounds(0, 10, 469, 64);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Welcome " + nameUser);
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(27, 80, 309, 47);
		contentPane.add(lblNewLabel_1);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(new CompoundBorder(null, UIManager.getBorder("CheckBoxMenuItem.border")), "List of online users", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBackground(Color.WHITE);
		panel.setBounds(27, 137, 407, 246);

		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 1));

		listActive = new JList<>(model);
		listActive.setBorder(new EmptyBorder(5, 5, 5, 5));
		listActive.setBackground(Color.WHITE);
		listActive.setForeground(Color.BLACK);
		listActive.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		listActive.setBounds(10, 20, 577, 332);
		JScrollPane listPane = new JScrollPane(listActive);

		panel.add(listPane);
//		try {
//		} catch (UnknownHostException e) {
//			e.printStackTrace();
//		}
		listActive.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				name = listActive.getModel().getElementAt(listActive.locationToIndex(arg0.getPoint()));
				connectChat();
			}
		});
	}

	private void connectChat() {
		// TODO Auto-generated method stub
		int n = JOptionPane.showConfirmDialog(this, "Bạn có muốn kết nối với người này không?", "Kết nối",
				JOptionPane.YES_NO_OPTION);
		if (n == 0) {
			System.out.println(name);
			if (name.equals("") || Client.clientarray == null) {
				Tags.show(this, "Invaild username", false);
				return;
			}
			if (name.equals(nameUser)) {
				Tags.show(this, "This software doesn't support chat yourself function", false);
				return;
			}
			int size = Client.clientarray.size();
			for (int i = 0; i < size; i++) {
				if (name.equals(Client.clientarray.get(i).getName())) {
					try {
						clientNode.intialNewChat(Client.clientarray.get(i).getHost(),
								Client.clientarray.get(i).getPort(), name);
						return;
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
			Tags.show(this, "Friend is not found. Please wait to update your list friend", false);
		}
	}

	public static int request(String msg, boolean type) {
		JFrame frameMessage = new JFrame();
		return Tags.show(frameMessage, msg, type);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		// Only debug
//		Tags.show(this, "Are you sure to leave", true);
		try {
			clientNode.exit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
