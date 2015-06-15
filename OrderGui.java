import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
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
	private JButton oFindbtn;
	private JButton omodify;
	private JButton readOrderbtn;
	private JTable lOrderArea;
	private JButton writeOrderbtn;
	private JPanel approvalPan;
	private JButton approval;
	private JButton cancelApproval;
	private JTextArea listDetail;
	private JPanel rightPanel;
	private JButton listOrderbtn;
	private JButton oListbtn;
	DefaultTableModel tableModel;
	DefaultTableModel tableModel2;
	ReadWrite fio = new ReadWrite();
	String []info = {"메뉴","가격"};
	String []info2 = {"날짜","주문번호","금액","결재"};
	String []orderInfo = {"메뉴","수량","금액"};
	static int OrderCount;
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
		setBounds(100, 100, 1100, 900);
		contentPane = getContentPane();
		fileBar();
		JTabbedPane tab = new JTabbedPane();
		orderManage();
		menuManage();
		tab.add("주문관리",orderPan);
		tab.add("메뉴 관리",menuPan);
		tab.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,18));
		contentPane.add(tab);
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = lOrderArea.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		tcm = lMenuArea.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}
		readMenu();
		readOrder();
		OrderGui.OrderCount=fio.input();
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				fio.output(MenuManager.menu, "data.dat");
				fio.outputOrder(OrderManager.order, "data.txt");
				fio.output(OrderCount);
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
			
		});
	}

	public void fileBar(){
		JMenuBar menubar = new JMenuBar();
		JMenu file= new JMenu("file");
		JMenuItem search = new JMenuItem("검색");
		JMenuItem modify = new JMenuItem("삭제");
		modify.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String order = JOptionPane.showInputDialog("삭제하실 주문 번호를 입력하세요");
				if(isDigit(order)){
					JOptionPane.showMessageDialog(null, "숫자만 입력해주세요");
				} else{
					int index = OrderManager.indexOfOrder(Integer.parseInt(order));
					if(index<OrderManager.order.size()){
						OrderManager.order.remove(index);
						listOrder();
						lOrderArea.updateUI();
					} else{
						JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");

					}
				}
			}
			
		});
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchOrder sm = new searchOrder();
				sm.setVisible(true);
			}
			
		});
		file.add(search);
		file.add(modify);
		menubar.add(file);
		
		setJMenuBar(menubar);
	}
	public void orderManage(){

		tableModel2 = new DefaultTableModel(info2,0);
		lOrderArea = new JTable(tableModel2);
		lOrderArea.setColumnSelectionAllowed(false);
		lOrderArea.setRowSelectionAllowed(true);
		lOrderArea.setAutoscrolls(true);
		JScrollPane scroll = new JScrollPane(lOrderArea);
		lOrderArea.setRowHeight(25);
		lOrderArea.setPreferredSize(new Dimension(450,800));
		listDetail = new JTextArea("");
		listDetail.setPreferredSize(new Dimension(450,800));
		listDetail.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,20));
		listDetail.setEnabled(false);
		lOrderArea.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,18));

		orderPan = new JPanel();
		orderPan.setLayout(new BorderLayout());
		rightPanel = new JPanel();
		rightPanel.setLayout(new BorderLayout());
		rightPanel.setPreferredSize(new Dimension(450,800));
		approvalPan = new JPanel();
		approvalPan.setLayout(new FlowLayout());
		approvalPane();
		approvalPan.setBackground(Color.WHITE);
		approvalPan.setPreferredSize(new Dimension(450,100)); 
		rightPanel.add(listDetail,BorderLayout.CENTER);
		rightPanel.add(approvalPan,BorderLayout.SOUTH);
		oSelect = new JPanel();
		oSelect.setPreferredSize(new Dimension(1000,100));
		oSelect.setLayout(new GridLayout(1,4,5,5));
		oAddbtn = new JButton("주문 추가");
		oAddbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		oAddbtn.addActionListener(new addOrderListener());
		oFindbtn= new JButton("판매 분석");
		oFindbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		oFindbtn.addActionListener(new findOrderListener());
		omodify = new JButton("주문 수정");
		omodify.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		omodify.addActionListener(new modifyListener());
		oListbtn = new JButton("주문 내역");
		oListbtn.addActionListener(new listOrderListener());
		oListbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		readOrderbtn = new JButton("<html>주문 내역<br /> 가져오기</html>");
		readOrderbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		readOrderbtn.addActionListener(new OpenOrderListener());
		writeOrderbtn = new JButton("<html>주문 내역<br /> 저장하기</html>");
		writeOrderbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		writeOrderbtn.addActionListener(new SaveOrderListener());
		ListSelectionModel listMod = lOrderArea.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new selectTableListener());
		oSelect.add(oAddbtn);
		oSelect.add(omodify);
		oSelect.add(oFindbtn);
		oSelect.add(oListbtn);
		oSelect.add(readOrderbtn);
		oSelect.add(writeOrderbtn);
		orderPan.add(oSelect,BorderLayout.PAGE_START);
		orderPan.add(scroll,BorderLayout.CENTER);
		orderPan.add(rightPanel,BorderLayout.EAST);
	}
	void approvalPane(){
		approval = new JButton("결재");
		approval.setPreferredSize(new Dimension(220,80));
		approval.addActionListener(new approvalListener());
		cancelApproval = new JButton("취소/삭제");
		cancelApproval.setPreferredSize(new Dimension(220,80));
		cancelApproval.addActionListener(new removeOrderListener());
		approvalPan.removeAll();
		approvalPan.add(approval);
		approvalPan.add(cancelApproval);
		approvalPan.updateUI();
	}
	void listOrderPane(){

		listOrderbtn = new JButton("홈");
		listOrderbtn.setPreferredSize(new Dimension(220,80));
		listOrderbtn.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				approvalPane();
				listOrder();				
			}
		});
		cancelApproval = new JButton("삭제");
		cancelApproval.setPreferredSize(new Dimension(220,80));
		cancelApproval.addActionListener(new removeOrderListener());
		approvalPan.removeAll();
		approvalPan.add(listOrderbtn);
		approvalPan.add(cancelApproval);
		orderPan.updateUI();
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
		mSelect.setLayout(new GridLayout(1,6,5,5));
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
	boolean isDigit(String str){
		for(int i=0; i<str.length();i++){
			char c = str.charAt(i);
			if(!Character.isDigit(c)){
				return true; // 문자가 있을경우
			}
		}
		return false; // 숫자로만 되어있을경우
	}
	public void readMenu(){
		try{
			ArrayList<Menu> menu2 = fio.input("data.dat");
			if(menu2!=null){
				for(Menu i: menu2){
					MenuManager.menu.add(i);
					addRow(i);
				}
			}
		}catch(Exception ex){}
	}
	public void readOrder(){
		try{
			ArrayList<Order> order2 =fio.inputOrder("data.txt");
			if(order2!=null){
				for(Order i : order2){
					OrderManager.order.add(i);
					addRow(i);
				}
			}
		}catch(Exception ex){}
	}
	public void removetable(DefaultTableModel table){
		while(table.getRowCount()!=0){
			table.removeRow(0);
		}
	}
	public void read(ArrayList<Menu> menu){
		Object [] data = new Object[2];
		removetable(tableModel);
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
	public void addRow(Order order){
		Object[] data = new Object[4];
		data[0] = order.getOrderDate();
		data[1] = order.getOrderNumber();
		data[2] = order.getTotalPrice();
		data[3] = order.getApproval();
		tableModel2.insertRow(0,data);
	}
	public void listOrder(){
		Object [] data = new Object[4];
		removetable(tableModel2);

		for(int i=0 ;i<OrderManager.order.size();i++){
			data[0]=OrderManager.order.get(i).getOrderDate();
			data[1]=OrderManager.order.get(i).getOrderNumber();
			data[2]=OrderManager.order.get(i).totalPrice;
			data[3] = OrderManager.order.get(i).getApproval();
			tableModel2.insertRow(0,data);
		}
	}
	public class approvalListener implements ActionListener{
		int selRow;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			Object value;
			selRow = lOrderArea.getSelectedRow();
			if(selRow>=0){
				TableModel tm = lOrderArea.getModel();
				value =tm.getValueAt(selRow, 1);
				int index = OrderManager.indexOfOrder((int)value);
				int reply = JOptionPane.showConfirmDialog(null, "결재 완료 하시겠습니까?","결재확인", JOptionPane.YES_NO_OPTION);
				if(reply == JOptionPane.YES_NO_OPTION){
					OrderManager.order.get(index).setApproval("완료");
					tm.setValueAt(OrderManager.order.get(index).getApproval(), selRow, 3);
				} else{
					OrderManager.order.get(index).setApproval("미완료");
				}
			}
		}
		
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
	public class SaveOrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			File f;
			JFileChooser fc = new JFileChooser();
			int answer =  fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				fio.outputOrder(OrderManager.order,f);
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
				try{
					ArrayList<Menu> menu2 = fio.input(f);
					if(menu2!=null){
						for(Menu i: menu2){
							MenuManager.menu.add(i);
							addRow(i);
						}
					}
				}catch(Exception ex){}
			}
		}
	}
	public class OpenOrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			File f;
			JFileChooser fc = new JFileChooser();
			int answer =  fc.showOpenDialog(null);
			if(answer == JFileChooser.APPROVE_OPTION){
				f = fc.getSelectedFile();
				ArrayList<Order> order = fio.inputOrder(f);
				if(order!=null){
					for(Order i: order){
						OrderManager.order.add(i);
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
	public class modifyListener implements ActionListener{
		int selRow;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selRow = lOrderArea.getSelectedRow();
			if(selRow>=0){
				addOrder modify = new addOrder(tableModel2,selRow);
				modify.setVisible(true);
			}
		}
		
	}
	public class listOrderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			listOrderPane();
			Object [] data = new Object[4];
			removetable(tableModel2);

			for(int i=0 ;i<OrderManager.order.size();i++){
				if(OrderManager.order.get(i).getApproval().equals("미완료")){
				} else{
					data[0]=OrderManager.order.get(i).getOrderDate();
					data[1]=OrderManager.order.get(i).getOrderNumber();
					data[2]=OrderManager.order.get(i).totalPrice;
					data[3] = OrderManager.order.get(i).getApproval();
					tableModel2.insertRow(0,data);
					}
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
				listDetail.setEnabled(false);
				if(selRows>=0){
					TableModel tm = lOrderArea.getModel();
					Value = tm.getValueAt(selRows, 1);
					int index = OrderManager.indexOfOrder((int) Value);
					listDetail.append(OrderManager.order.get(index).getOrderDate()+"\n");
					listDetail.append("메뉴명\t수량\t금액\n");
					for(sellMenu i : OrderManager.order.get(index).order){
						listDetail.append(i.getName()+"\t"+i.getCount()+"\t"+i.getPrice()*i.getCount()+"\n");
					}
				}
			}
		}	
	}
	public class removeOrderListener implements ActionListener{
		int selRow;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			selRow = lOrderArea.getSelectedRow();
			if(selRow>=0){
				TableModel tm = lOrderArea.getModel();
				int OrderNumber = (int)tm.getValueAt(selRow, 1);
				if(OrderManager.indexOfOrder(OrderNumber)>OrderManager.order.size()){
					JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");
				} else{
					int num = OrderManager.indexOfOrder(OrderNumber);
					OrderManager.order.remove(num);
					tableModel2.removeRow(selRow);
				}
			}
		}
	}
	public class findOrderListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			findOrder f = new findOrder();
			f.setVisible(true);
		}
		
	}
}
class searchOrder extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container content;
	JPanel pane;
	JComboBox<String> list;
	JButton confirm;
	JButton cancel;
	JLabel search;
	JComboBox<String> menu;
	String listSearch[] = {
			"메뉴",
			"날짜",
			"요일",
			"월"
	};
	confirmListener con = new confirmListener();
	menuconfirmListener men = new menuconfirmListener();
	dayconfirmListener daycon = new dayconfirmListener();
	MonthconfirmListener mcon = new MonthconfirmListener();
	searchOrder(){
		content = getContentPane();
		createPanel();
		content.add(pane,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500,500,250,250);
	}
	void createPanel(){
		pane = new JPanel();
		pane.setLayout(new GridLayout(2,2));
		search = new JLabel("검색 종류");
		list = new JComboBox<String>(listSearch);
		confirm = new JButton("확인");
		confirm.addActionListener(con);
		cancel = new JButton("취소");
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		pane.add(search);
		pane.add(list);
		pane.add(confirm);
		pane.add(cancel);
	}
	public class confirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String select = (String)list.getSelectedItem();
			if(select.equals("메뉴")){
				selectMenu();
			} else if(select.equals("날짜")){
				selectDate sd = new selectDate();
				sd.setVisible(true);
				setVisible(false);
			} else if(select.equals("요일")){
				selectDay();
			} else if(select.equals("월")){
				selectMonth();
			}
		}
	}
	public class menuconfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String menu = (String)list.getSelectedItem();
			if(menu!=null){
				searchMenuOrder sm = new searchMenuOrder(menu);
				sm.setVisible(true);
				setVisible(false);
			}
		}
	}
	public class dayconfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String day = (String)list.getSelectedItem();
			if(day!=null){
				searchDayOrder sd = new searchDayOrder(day);
				sd.setVisible(true);
				setVisible(false);
			}
		}
		
	}
	public class MonthconfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String month = (String)list.getSelectedItem();
			if(month!=null){
				searchMonthOrder sm = new searchMonthOrder(month);
				sm.setVisible(true);
				setVisible(false);
			}
		}
		
	}
	void selectDay(){
		String day[]= {
				"월요일",
				"화요일",
				"수요일",
				"목요일",
				"금요일",
				"토요일",
				"일요일"
		};
		list.removeAllItems();
		for(int i =0;i<day.length;i++){
			list.addItem(day[i]);
		}
		search.setText("요일");
		pane.updateUI();
		confirm.removeActionListener(con);
		confirm.addActionListener(daycon);
	}
	void selectMonth(){
		String Month[] ={"01","02","03","04","05","06","07","08","09","10","11","12"};
		list.removeAllItems();
		for(int i=0;i<Month.length;i++){
			list.addItem(Month[i]);
		}
		search.setText("월");
		pane.updateUI();
		confirm.removeActionListener(con);
		confirm.addActionListener(mcon);
	}
	void selectMenu(){
		String menuName[] = new String[MenuManager.menu.size()];
		list.removeAllItems();
		for(int i =0 ;i<MenuManager.menu.size();i++){
			menuName[i]=MenuManager.menu.get(i).getName();
			list.addItem(menuName[i]);
		}
		search.setText("메뉴");
		pane.updateUI();
		confirm.removeActionListener(con);
		confirm.addActionListener(men);
	}
}
