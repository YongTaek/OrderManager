import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.GridLayout;
import java.awt.Paint;
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

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;


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
	String []info2 = {"날짜","주문번호","금액"};
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
		lOrderArea.setColumnSelectionAllowed(false);
		lOrderArea.setRowSelectionAllowed(true);
		lOrderArea.setAutoscrolls(true);
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
		oSelect.setLayout(new GridLayout(1,6,5,5));
		oAddbtn = new JButton("주문 추가");
		oAddbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		oAddbtn.addActionListener(new addOrderListener());
		oFindbtn= new JButton("판매 분석");
		oFindbtn.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		oFindbtn.addActionListener(new findOrderListener());
		oRemove = new JButton("주문 제거");
		oRemove.setFont(new Font("\\OrderManager\\lib\\08SEOULNAMSANM.TTF",Font.BOLD,20));
		oRemove.addActionListener(new removeOrderListener());
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
		detail.add(lOrderArea);
		detail.add(listDetail);
		oSelect.add(oAddbtn);
		oSelect.add(oRemove);
		oSelect.add(oFindbtn);
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
	public void addRow(Order order){
		Object[] data = new Object[3];
		data[0] = order.getOrderDate();
		data[1] = order.getOrderNumber();
		data[2] = order.getTotalPrice();
		tableModel2.addRow(data);
	}
	public void listOrder(){
		Object [] data = new Object[3];
		removetable(tableModel2);
		tableModel2.addRow(info2);

		for(int i=0 ;i<OrderManager.order.size();i++){
			data[0]=OrderManager.order.get(i).getOrderDate();
			data[1]=OrderManager.order.get(i).getOrderNumber();
			data[2]=OrderManager.order.get(i).totalPrice;
			tableModel2.addRow(data);
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
	public class listOrderListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			listOrder();
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
				if(selRows>0){
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

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String OrderNumber = JOptionPane.showInputDialog("제거할 주문번호를 입력하세요");
			if(OrderNumber == null || isDigit(OrderNumber)){
				JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");
			} else if(OrderManager.indexOfOrder(Integer.parseInt(OrderNumber))>OrderManager.order.size()){
				JOptionPane.showMessageDialog(null, "잘못입력하셨습니다.");
			} else{
				int num = OrderManager.indexOfOrder(Integer.parseInt(OrderNumber));
				OrderManager.order.remove(num);
				listOrder();
			}
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
class findOrder extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] ChartList = {
			"메뉴별 판매 현황",
			"월별 판매 현황",
			"요일별 매출현황"
	};
	private Container content;
	JComboBox<String> box;
	JButton confirm;
	JButton cancel;
	findOrder(){
		content = getContentPane();
		content.setLayout(new GridLayout(2,2));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500,500,250,200);
		Combobox();
	}
	void Combobox(){
		content.add(new JLabel("차트 종류"));
		box = new JComboBox<String>(ChartList);
		content.add(box);
		confirm  = new JButton("확인");
		confirm.addActionListener(new confirmListener());
		cancel = new JButton("취소");
		content.add(confirm);
		content.add(cancel);
	}
	public class confirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String list = (String) box.getSelectedItem();
			if(list.equals(ChartList[0])){
				CountPerMenu cpm = new CountPerMenu("메뉴별 판매현황");
				//cpm.pack();
				RefineryUtilities.centerFrameOnScreen(cpm);
				cpm.setVisible(true);
				setVisible(false);
			} else if(list.equals(ChartList[1])){
				CountperMonth cpM = new CountperMonth("월별 매출현황");
				//cpM.pack();
				RefineryUtilities.centerFrameOnScreen(cpM);
				cpM.setVisible(true);
				setVisible(false);
			}
			else if(list.equals(ChartList[2])){
				Countperday cpd = new Countperday("요일별 매출현황");
				//cpd.pack();
				RefineryUtilities.centerFrameOnScreen(cpd);
				cpd.setVisible(true);
				setVisible(false);
			}

		}
		
	}
}

class CountperMonth extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int Price[] = new int[12];
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	JFreeChart chart;
	String Month[]={
			"01월",
			"02월",
			"03월",
			"04월",
			"05월",
			"06월",
			"07월",
			"08월",
			"09월",
			"10월",
			"11월",
			"12월"
	};
	public CountperMonth(String title) {
		super(title);
		// TODO Auto-generated constructor stub
		getPriceMenu();
		createDataset();
		createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		//chartPanel.setPreferredSize(new Dimension(500,300));
		setContentPane(chartPanel);
		setSize(800,600);
		//chartPanel.setPreferredSize(new Dimension(800,600));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	void getPriceMenu(){
		for(Order i : OrderManager.order){
			for(int j=0 ;j<Price.length;j++){
				System.out.println(i.getOrderDate().substring(6, 9));
				if(Month[j].equals(i.getOrderDate().substring(6, 9))){
					Price[j]=Price[j]+i.getTotalPrice();
				}
			}
		}
		System.out.println(Price);
	}
	void createDataset(){
		String category = "월별 매출현황";
		for(int i=0;i<Price.length;i++){
			dataset.addValue(Price[i],Month[i],category);
		}
	}
	void createChart(){
		chart = ChartFactory.createBarChart(
				"월별 매출현황",
				"월",
				"매출",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		Font chartFont = chart.getTitle().getFont();
		chart.getTitle().setFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getDomainAxis().getLabelFont();
		plot.getDomainAxis().setLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getDomainAxis().getTickLabelFont();
		plot.getDomainAxis().setTickLabelFont((new Font("굴림",chartFont.getStyle(),chartFont.getSize())));
		chartFont = plot.getRangeAxis().getLabelFont();
		plot.getRangeAxis().setLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getRangeAxis().getTickLabelFont();
		plot.getRangeAxis().setTickLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		GradientPaint gp[] = new GradientPaint[MenuManager.menu.size()];
		Paint paint[] = {
				Color.BLUE,
				Color.cyan,
				Color.orange,
				Color.green,
				Color.pink,
				Color.yellow,
				Color.red,
				Color.DARK_GRAY,
				Color.MAGENTA,
		};
		int j  = 0;
		for(int i =0 ;i<gp.length;i++){
			if(i<paint.length){
				gp[i]= new GradientPaint(
						0.0f,0.0f,(Color) paint[j],
						0.0f,0.0f,Color.LIGHT_GRAY
						);
				j++;
			} else{
				j = 0;
			}
			renderer.setSeriesPaint(i, gp[i]);
		}
		renderer.setMaximumBarWidth(.35);
	}
	class windowCloseListener implements WindowListener{

		public void windowClosing(WindowEvent e){
			dispose();
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
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
}
class Countperday extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int Price[] = new int[7];
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	JFreeChart chart;
	String day[]={
			"월요일",
			"화요일",
			"수요일",
			"목요일",
			"금요일",
			"토요일",
			"일요일",
	};
	public Countperday(String title) {
		super(title);
		// TODO Auto-generated constructor stub
		getPriceMenu();
		createDataset();
		createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		//chartPanel.setPreferredSize(new Dimension(500,300));
		setContentPane(chartPanel);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	void getPriceMenu(){
		for(Order i : OrderManager.order){
			for(int j=0 ;j<Price.length;j++){
				if(day[j].equals(i.getOrderDate().substring(14, 17))){
					Price[j]=Price[j]+i.getTotalPrice();
				}
			}
		}
		System.out.println(Price);
	}
	void createDataset(){
		String category = "요일별 판매현황";
		for(int i=0;i<Price.length;i++){
			dataset.addValue(Price[i],day[i],category);
		}
	}
	void createChart(){
		chart = ChartFactory.createBarChart(
				"요일별 판매현황",
				"요일",
				"매출",
				dataset,
				PlotOrientation.VERTICAL,
				true,
				true,
				false
				);
		chart.setBackgroundPaint(Color.WHITE);
		CategoryPlot plot = chart.getCategoryPlot();
		plot.setBackgroundPaint(Color.LIGHT_GRAY);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		Font chartFont = chart.getTitle().getFont();
		chart.getTitle().setFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getDomainAxis().getLabelFont();
		plot.getDomainAxis().setLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getDomainAxis().getTickLabelFont();
		plot.getDomainAxis().setTickLabelFont((new Font("굴림",chartFont.getStyle(),chartFont.getSize())));
		chartFont = plot.getRangeAxis().getLabelFont();
		plot.getRangeAxis().setLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		chartFont = plot.getRangeAxis().getTickLabelFont();
		plot.getRangeAxis().setTickLabelFont(new Font("굴림",chartFont.getStyle(),chartFont.getSize()));
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		renderer.setDrawBarOutline(false);
		GradientPaint gp[] = new GradientPaint[MenuManager.menu.size()];
		Paint paint[] = {
				Color.BLUE,
				Color.cyan,
				Color.orange,
				Color.green,
				Color.pink,
				Color.yellow,
				Color.red,
				Color.DARK_GRAY,
				Color.MAGENTA,
		};
		int j  = 0;
		for(int i =0 ;i<gp.length;i++){
			if(i<paint.length){
				gp[i]= new GradientPaint(
						0.0f,0.0f,(Color) paint[j],
						0.0f,0.0f,Color.LIGHT_GRAY
						);
				j++;
			} else{
				j = 0;
			}
			renderer.setSeriesPaint(i, gp[i]);
		}
		renderer.setMaximumBarWidth(.35);
	}
	class windowCloseListener implements WindowListener{

		public void windowClosing(WindowEvent e){
			dispose();
		}

		@Override
		public void windowOpened(WindowEvent e) {
			// TODO Auto-generated method stub
			
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
}