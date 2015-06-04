import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.util.ArrayList;

import javafx.scene.input.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;



public class OrderGui extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Container contentPane;
	private JPanel menuPan;
	private JButton aMenubtn;
	private JButton sMenubtn;
	private JButton rMenubtn;
	private JButton lMenubtn;
	private JButton readMenubtn;
	private JButton writeMenubtn;
	private JTable lMenuArea;
	private JPanel mSelect;
	
	private JPanel orderPan;
	private JPanel oSelect;
	private JButton oAddbtn;
	private JButton oListbtn;
	private JButton oFindbtn;
	private JButton omodify;
	private JButton oRemove;
	private JButton readOrderbtn;
	private JTable lOrderArea;
	private JButton writeOrderbtn;
	private JPanel detail;
	private JTextArea listDetail;
	DefaultTableModel tableModel;
	DefaultTableModel tableModel2;
	ReadWrite fio = new ReadWrite();
	String []info = {"메뉴","가격"};
	String []info2 = {"주문번호","금액"};
	String []orderInfo = {"메뉴","수량","금액"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					String lookAndFeel = "";
					lookAndFeel = UIManager.getSystemLookAndFeelClassName();
					try{
						/*UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
						JFrame.setDefaultLookAndFeelDecorated(true);*/
						UIManager.setLookAndFeel(lookAndFeel);
					} catch(Exception e){
						e.printStackTrace();
					}
					OrderGui frame = new OrderGui();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public OrderGui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 800);
		contentPane = getContentPane();
		fileBar();
		JTabbedPane tab = new JTabbedPane();
		orderManage();
		menuManage();
		tab.add("주문관리",orderPan);
		tab.add("메뉴 관리",menuPan);
		tab.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		contentPane.add(tab);
	}

	public void fileBar(){
		JMenuBar menubar = new JMenuBar();
		JMenu file= new JMenu("file");
		menubar.add(file);
		JMenuItem open = new JMenuItem("open");
		JMenuItem save = new JMenuItem("save");
		JMenuItem exit = new JMenuItem("exit");
		open.addActionListener(new OpenMenuListener());
		file.add(open);
		file.add(save);
		file.add(exit);
		setJMenuBar(menubar);
	}
	public void orderManage(){
		tableModel2 = new DefaultTableModel(info2,0);
		lOrderArea = new JTable(tableModel2);
		new JScrollPane(lOrderArea);
		tableModel2.addRow(info2);
		lOrderArea.setPreferredSize(new Dimension(450,800));
		detail = new JPanel();
		detail.setLayout(new FlowLayout());
		listDetail = new JTextArea("");
		listDetail.setPreferredSize(new Dimension(450,800));
		listDetail.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,20));
		lOrderArea.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,18));
		lOrderArea.setRowHeight(30);
		orderPan = new JPanel();
		orderPan.setLayout(new BorderLayout());
		oSelect = new JPanel();
		oSelect.setPreferredSize(new Dimension(900,100));
		oSelect.setLayout(new GridLayout(1,6));
		oAddbtn = new JButton("메뉴 추가");
		oAddbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		oAddbtn.addActionListener(new addOrderListener());
		omodify= new JButton("메뉴 수정");
		omodify.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		oRemove = new JButton("메뉴 제거");
		oRemove.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		oListbtn = new JButton("메뉴 목록");
		oListbtn.addActionListener(new listOrderListener());
		oListbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		readOrderbtn = new JButton("메뉴 가져오기");
		readOrderbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		writeOrderbtn = new JButton("메뉴 저장하기");
		writeOrderbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		ListSelectionModel listMod = lOrderArea.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new selectTableListener());
		detail.add(lOrderArea);
		detail.add(listDetail);
		oSelect.add(oAddbtn);
		oSelect.add(omodify);
		oSelect.add(oRemove);
		oSelect.add(oListbtn);
		oSelect.add(readOrderbtn);
		oSelect.add(writeOrderbtn);
		orderPan.add(oSelect,BorderLayout.PAGE_START);
		orderPan.add(lOrderArea,BorderLayout.CENTER);
		orderPan.add(detail,BorderLayout.EAST);
	}
	public void menuManage(){
		//JTable
		tableModel = new DefaultTableModel(info,0);
		lMenuArea = new JTable(tableModel);
		new JScrollPane(lMenuArea);
		tableModel.addRow(info);
		lMenuArea.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,18));
		lMenuArea.setRowHeight(30);
		menuPan = new JPanel();
		menuPan.setLayout(new BorderLayout());
		mSelect = new JPanel();
		mSelect.setPreferredSize(new Dimension(1000,100));
		mSelect.setLayout(new GridLayout(1,6));
		aMenubtn = new JButton("메뉴 추가");
		aMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		aMenubtn.addActionListener(new AddActionListener());
		sMenubtn = new JButton("메뉴 수정");
		sMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		sMenubtn.addActionListener(new modifyMenuListener());
		rMenubtn = new JButton("메뉴 제거");
		rMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		rMenubtn.addActionListener(new removeMenuListener());
		lMenubtn = new JButton("메뉴 목록");
		lMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		lMenubtn.addActionListener(new ListMenuListener());
		readMenubtn = new JButton("메뉴 가져오기");
		readMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		readMenubtn.addActionListener(new OpenMenuListener());	
		writeMenubtn = new JButton("메뉴 저장하기");
		writeMenubtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		writeMenubtn.addActionListener(new SaveMenuListener());
		mSelect.add(aMenubtn);
		mSelect.add(sMenubtn);
		mSelect.add(rMenubtn);
		mSelect.add(lMenubtn);
		mSelect.add(readMenubtn);
		mSelect.add(writeMenubtn);
		menuPan.add(mSelect,BorderLayout.PAGE_START);
		menuPan.add("Center", lMenuArea);
	}
	public void removetable(DefaultTableModel table){
		while(table.getRowCount()!=0){
			table.removeRow(0);
		}
	}
	public void read(ArrayList<Menu> menu){
		Object [] data = new Object[2];
		removetable(tableModel);
		tableModel.addRow(info);
		if(menu!=null){
			for(Menu i : menu){
				data[0] = i.getName();
				data[1] = i.getPrice();
				tableModel.addRow(data);
			}
		}
	}
	public void addRow(Menu menu){
		Object[] data = new Object[2];
		data[0]=menu.getName();
		data[1]=menu.getPrice();
		tableModel.addRow(data);
	}
	public class SaveMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			File f;
			JFileChooser fc = new JFileChooser();
			int answer =  fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				fio.output(MenuManager.menu, f);
			}
		}
		
	}
	public class OpenMenuListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			File f;
			JFileChooser fc = new JFileChooser();
			int answer =  fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				ArrayList<Menu> menu2 = fio.input(f);
				if(menu2!=null){
					for(Menu i: menu2){
						MenuManager.menu.add(i);
						addRow(i);
					}
				}
			}
		}
	}
	public class AddActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addMenu m = new addMenu(tableModel);
			m.setVisible(true);
		}
		
	}
	public class ListMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			read(MenuManager.menu);
		}
		
	}
	public class removeMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = JOptionPane.showInputDialog("지우고 싶은 메뉴를 입력하세요.");
			int count = MenuManager.menu.size();
			for(Menu i : MenuManager.menu){
				if(i.getName().equals(name)){
					MenuManager.menu.remove(i);
					read(MenuManager.menu);
					break;
				}
			}
			if(count == MenuManager.menu.size()){
				JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");
			}
		}
		
	}
	public class modifyMenuListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = JOptionPane.showInputDialog("수정하고 싶은 메뉴를 입력하세요.");
			int count = MenuManager.menu.size();
			int Row = MenuManager.indexMenu(name);
			if(Row<=count){
				if(MenuManager.menu.get(Row).getName().equals(name)){
					modifyMenu m = new modifyMenu(tableModel,name);
					m.setVisible(true);
				}
			} else{
				JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");
			}
		}
	}
	public class addOrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			addOrder a = new addOrder(tableModel2);
			a.setVisible(true);
		}
		
	}
	public class listOrderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object [] data = new Object[2];
			removetable(tableModel2);
			tableModel2.addRow(info2);
			JButton[] button = new JButton[OrderManager.order.size()];
			for(int i=0 ;i<OrderManager.order.size();i++){
				button[i]= new JButton("자세히");
				data[0]=OrderManager.order.get(i).getOrderNumber();
				data[1]=OrderManager.order.get(i).totalPrice;
				tableModel2.addRow(data);
			}
		}
	}
	public class selectTableListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int selRows;
			Object Value;
			if(!e.getValueIsAdjusting()){
				selRows = lOrderArea.getSelectedRow();
				listDetail.setText("");
				if(selRows>0){
					TableModel tm = lOrderArea.getModel();
					Value = tm.getValueAt(selRows, 0);
					System.out.println("@"+Value);
					int index = OrderManager.indexOfOrder((int) Value);
					System.out.println(index);
					System.out.println("!"+OrderManager.order.get(index).order.size());
					listDetail.append("메뉴명\t수량\t금액\n");
					for(sellMenu i : OrderManager.order.get(index).order){
						listDetail.append(i.getName()+"\t"+i.getCount()+"\t"+i.getPrice()*i.getCount()+"\n");
					}
				}
			}
		}
		
	}
}

