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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
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
						//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
						//UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
						//UIManager.setLookAndFeel("net.infonode.gui.laf.InfoNodeLookAndFeel");
						//UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
						//UIManager.setLookAndFeel("ch.randelshofer.quaqua.QuaquaLookAndFeel");
						//JFrame.setDefaultLookAndFeelDecorated(true);
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
	}

	public void fileBar(){
		JMenuBar menubar = new JMenuBar();
		JMenu file= new JMenu("file");
		JMenuItem search = new JMenuItem("검색");
		search.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchMenu sm = new searchMenu();
				sm.setVisible(true);
			}
			
		});
		file.add(search);
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
		lOrderArea.setPreferredSize(new Dimension(450,800));
		listDetail = new JTextArea("");
		listDetail.setPreferredSize(new Dimension(450,700));
		listDetail.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,20));
		listDetail.setEnabled(false);
		lOrderArea.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,18));
		lOrderArea.setRowHeight(30);
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
		tableModel2.addRow(data);
	}
	public void listOrder(){
		Object [] data = new Object[4];
		removetable(tableModel2);

		for(int i=0 ;i<OrderManager.order.size();i++){
			data[0]=OrderManager.order.get(i).getOrderDate();
			data[1]=OrderManager.order.get(i).getOrderNumber();
			data[2]=OrderManager.order.get(i).totalPrice;
			data[3] = OrderManager.order.get(i).getApproval();
			tableModel2.addRow(data);
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
					tableModel2.addRow(data);
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
class searchMenu extends JFrame{

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
			"몇 원 이상"
	};
	confirmListener con = new confirmListener();
	menuconfirmListener men = new menuconfirmListener();
	searchMenu(){
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
				setVisible(false);
			} else if(select.equals("날짜")){
				selectDate sd = new selectDate();
				setVisible(false);
			} else if(select.equals("요일")){
				
			} else if(select.equals("몇 원 이상")){
				
			}
		}
	}
	public class menuconfirmListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			String menu = (String)list.getSelectedItem();
			if(menu!=null){
				searchMenuOrder sm = new searchMenuOrder(menu);
				sm.setVisible(true);
			}
		}
	}
	public class dateconfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
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
class selectDate extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	SimpleDateFormat f = new SimpleDateFormat("yyyyMMdd");
	Container content;
	JPanel pane;
	JLabel Month;
	JLabel date;
	JComboBox<String> Monthbox;
	JComboBox<String> datebox;
	JButton confirm;
	JButton cancel;
	DefaultComboBoxModel<String> listModel;
	String MonthList[]={
			"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
	};
	String dateList[][] = new String[12][];
	void createDateList(){
		for(int i=0;i<MonthList.length;i++){
			if(MonthList[i].equals("01")||MonthList[i].equals("03")||MonthList[i].equals("05")||
					MonthList[i].equals("07")||MonthList[i].equals("08")||MonthList[i].equals("10")||MonthList[i].equals("12")){
				dateList[i]= new String[31];
				for(int j=0;j<31;j++){
					dateList[i][j]=String.valueOf(j+1);
				}
			} else if(MonthList[i].equals("04")||MonthList[i].equals("06")||MonthList[i].equals("09")||
					MonthList[i].equals("11")){
				dateList[i] = new String[30];
				for(int j=0;j<30;j++){
					dateList[i][j]=String.valueOf(j+1);
				}
			} else{
				dateList[i] = new String[28];
				for(int j=0 ;j<28;j++){
					dateList[i][j]=String.valueOf(j+1);
				}
			}
		}
	}
	selectDate(){
		createPane();
		content = getContentPane();
		setBounds(500,500,200,200);
		content.add(pane);
		setVisible(true);
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void createPane(){
		createDateList();
		listModel = new DefaultComboBoxModel(dateList[0]);
		Month = new JLabel("월");
		date = new JLabel("일");
		Monthbox = new JComboBox<String>(MonthList);
		confirm = new JButton("확인");
		confirm.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				searchDateOrder sd = new searchDateOrder((String)Monthbox.getSelectedItem(),(String)listModel.getSelectedItem());
				sd.setVisible(true);
			}
			
		});
		cancel = new JButton("취소");
		cancel.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setVisible(false);
			}
			
		});
		pane = new JPanel();
		pane.setLayout(new GridLayout(3,3));
		pane.setPreferredSize(new Dimension(240,240));
		for(int i=0;i<31;i++){
			listModel.addElement(dateList[0][i]);
		}
		datebox = new JComboBox<String>(listModel);
		datebox.setMaximumRowCount(listModel.getSize());
		Monthbox.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				for(int i=0 ; i<MonthList.length;i++){
					if(MonthList[i].equals(Monthbox.getSelectedItem())){
						listModel.removeAllElements();
						for(int j = 0;j<dateList[i].length;j++){
							listModel.addElement(dateList[i][j]);
						}
						datebox.setMaximumRowCount(listModel.getSize());
					}
				}
			}
		});
		pane.add(Month);
		pane.add(Monthbox);
		pane.add(date);
		pane.add(datebox);
		pane.add(confirm);
		pane.add(cancel);
	}
}
class searchDateOrder extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container content;
	JPanel leftPane;
	JPanel rightPane;
	JTable table;
	DefaultTableModel tableModel;
	JTextArea listDetail;
	JScrollPane scroll;
	String []info2 = {"날짜","주문번호","금액","결재"};
	String month,date;
	searchDateOrder(String month,String date){
		this.month = month;
		this.date = date;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(300,300,1200,800);
		content = getContentPane();
		createPane();
		DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();
		dtcr.setHorizontalAlignment(SwingConstants.CENTER);
		TableColumnModel tcm = table.getColumnModel();
		for(int i=0;i<tcm.getColumnCount();i++){
			tcm.getColumn(i).setCellRenderer(dtcr);
		}

		content.add(scroll, BorderLayout.CENTER);
		content.add(listDetail,BorderLayout.EAST);
	}
	public void createPane(){
		tableModel = new DefaultTableModel(info2,0);
		table = new JTable(tableModel);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(true);
		table.setAutoscrolls(true);
		scroll = new JScrollPane(table);
		table.setPreferredSize(new Dimension(500,800));
		listDetail = new JTextArea("");
		listDetail.setPreferredSize(new Dimension(500,800));
		listDetail.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,20));
		listDetail.setEnabled(false);
		table.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.CENTER_BASELINE,18));
		table.setRowHeight(30);
		rightPane = new JPanel();
		leftPane = new JPanel();
		listOrder();
		leftPane.setPreferredSize(new Dimension(500,800));
		rightPane.setPreferredSize(new Dimension(500,800));
		ListSelectionModel listMod = table.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new selectTableListener());
	}
	public void removetable(DefaultTableModel table){
		while(table.getRowCount()!=0){
			table.removeRow(0);
		}
	}
	public void listOrder(){
		Object [] data = new Object[4];
		removetable(tableModel);
		for(int i=0 ;i<OrderManager.order.size();i++){
			System.out.println(OrderManager.order.get(i).getOrderDate().substring(6, 8));
			System.out.println(OrderManager.order.get(i).getOrderDate().substring(10, 12));
			if(OrderManager.order.get(i).getOrderDate().substring(6,8).equals(month) && OrderManager.order.get(i).getOrderDate().substring(10, 12).equals(date)){
				for(sellMenu j : OrderManager.order.get(i).getOrderList()){
					data[0]=OrderManager.order.get(i).getOrderDate();
					data[1]=OrderManager.order.get(i).getOrderNumber();
				data[2]=OrderManager.order.get(i).totalPrice;
				data[3] = OrderManager.order.get(i).getApproval();
				tableModel.addRow(data);
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
				selRows = table.getSelectedRow();
				listDetail.setText("");
				listDetail.setEnabled(false);
				if(selRows>=0){
					TableModel tm = table.getModel();
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
}