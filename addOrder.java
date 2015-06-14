import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


class addOrder extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<sellMenu> order;
	private	JButton []menu;
	private JButton orderconfirm;
	private JButton cancel;
	private Container contentPane;
	private JPanel panel;
	private JPanel panel2;
	private JPanel pan;
	int selRow;
	private DefaultTableModel tm;
	listOrder lo;
	addOrder(DefaultTableModel tm){
		contentPane = getContentPane();
		this.tm = tm;
		setTitle("주문 추가");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200,200,1000,800);
		lo = new listOrder();
		addPane();
		contentPane.add(pan);
		contentPane.setVisible(true);

		ListSelectionModel listMod = lo.orderTable.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new selectLOListener());
		lo.setVisible(true);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				lo.setVisible(false);
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
	addOrder(DefaultTableModel tm,int selRow){
		contentPane = getContentPane();
		this.tm = tm;
		this.selRow = selRow;
		setTitle("주문 수정");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200,200,1000,800);
		lo = new listOrder();
		modifyPane();
		contentPane.add(pan);
		contentPane.setVisible(true);
		ListSelectionModel listMod = lo.orderTable.getSelectionModel();
		listMod.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listMod.addListSelectionListener(new selectLOListener());
		lo.setVisible(true);
		this.addWindowListener(new WindowListener(){

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				lo.setVisible(false);
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
	void addPane(){
		order = new ArrayList<sellMenu>();
		pan = new JPanel();
		panel = new JPanel();
		panel2 = new JPanel();
		pan.setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(5,5,20,20));
		panel.setPreferredSize(new Dimension(900,450));
		panel2.setLayout(new FlowLayout());
		orderconfirm = new JButton("추가");
		orderconfirm.addActionListener(new addconfirmListener());
		cancel = new JButton("취소");
		cancel.addActionListener(new cancelActionListener());
		menu = new JButton[MenuManager.menu.size()];
		for(int i=0;i<23-MenuManager.menu.size();i++){
			JButton b = new JButton();
			panel.add(b);
		}
		for(int i=0;i<MenuManager.menu.size();i++){
			menu[i] = new JButton(MenuManager.menu.get(i).getName());
			panel.add(menu[i],i);
			menu[i].addActionListener(new btnActionListener());
		}
		panel.add(orderconfirm);
		panel.add(cancel);
		pan.add(panel,BorderLayout.CENTER);
		//pan.add(panel2,BorderLayout.SOUTH);
	}
	void modifyPane(){
		int index = OrderManager.indexOfOrder((int)tm.getValueAt(selRow, 1));
		this.order = OrderManager.order.get(index).getOrderList();
		readList(index);
		pan = new JPanel();
		panel = new JPanel();
		panel2 = new JPanel();
		pan.setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(5,5,20,20));
		panel.setPreferredSize(new Dimension(900,450));
		panel2.setLayout(new FlowLayout());
		orderconfirm = new JButton("수정");
		orderconfirm.addActionListener(new modifyconfirmListener());
		cancel = new JButton("취소");
		cancel.addActionListener(new cancelActionListener());
		menu = new JButton[MenuManager.menu.size()];
		for(int i=0;i<23-MenuManager.menu.size();i++){
			JButton b = new JButton();
			panel.add(b);
		}
		for(int i=0;i<MenuManager.menu.size();i++){
			menu[i] = new JButton(MenuManager.menu.get(i).getName());
			panel.add(menu[i],i);
			menu[i].addActionListener(new btnActionListener());
		}
		panel.add(orderconfirm);
		panel.add(cancel);
		pan.add(panel,BorderLayout.CENTER);
		//pan.add(panel2,BorderLayout.SOUTH);
	}
	int checkOrder(String name){
		int index = order.size()+1;
		for(int i=0;i<order.size();i++){
			if(order.get(i).getName().equals(name)){
				index = i;
				break;
			}
		}
		return index;
	}
	void readList(int index){
		System.out.println(lo);
		for(int i =0 ; i<order.size();i++){
			System.out.println(1);
			lo.addArea(order.get(i), totalMenuPrice(order.get(i)));
			
		}
	}
	public class btnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String name =((JButton)e.getSource()).getText();
			int price = MenuManager.menu.get(MenuManager.indexMenu(name)).getPrice();
			int index = checkOrder(name);
			if(index>order.size()){
				sellMenu sm = new sellMenu(name,price,1);
				lo.addArea(sm,price);
				order.add(sm);
				
			} else{
				order.get(index).setCount(order.get(index).getCount()+1);
				lo.addArea(order.get(index),totalMenuPrice(order.get(index)));
			}
			System.out.println(name);
		}

		
	}
	int totalMenuPrice(sellMenu sel){
		int totalPrice =sel.getPrice()*sel.getCount();
		return totalPrice;
	}
	public class selectLOListener implements ListSelectionListener{

		@Override
		public void valueChanged(ListSelectionEvent e) {
			// TODO Auto-generated method stub
			int selRow = lo.orderTable.getSelectedRow();
			try{
				lo.orderTable.clearSelection();
			}catch(Exception ex){
				System.out.println("error");
			}
			Object value;
			if(selRow>=0){
				TableModel tm = lo.orderTable.getModel();
				value = tm.getValueAt(selRow, 1);
				if((int)value>0){
					int index = checkOrder((String)tm.getValueAt(selRow, 0));
					order.get(index).setCount((int)value-1);
					lo.addArea(order.get(index),totalMenuPrice(order.get(index)));
				}
			}
		}
		
	}
	public class addconfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(order.size()!=0){
				SimpleDateFormat f = new SimpleDateFormat("yyyy년 MM월 dd일 E요일 a h시 mm분 ss초 ");
				String date = f.format(new Date());
				OrderManager.order.add(new Order(OrderManager.orderCount+1,order,date,"미완료"));
				
				Object [] data = new Object[4];
				int i = OrderManager.indexOfOrder(OrderManager.orderCount+1);
				data[0]=OrderManager.order.get(i).getOrderDate();
				data[1]=OrderManager.order.get(i).getOrderNumber();
				data[2]=OrderManager.order.get(i).totalPrice;
				data[3] = OrderManager.order.get(i).getApproval();
				tm.insertRow(0, data);
				OrderManager.orderCount++;
				setVisible(false);
				lo.setVisible(false);
			} else{
				JOptionPane.showMessageDialog(pan, "입력값이 없습니다.");
				setVisible(false);
				lo.setVisible(false);
			}
		}
	}
	public class modifyconfirmListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(order.size()!=0){
				int index = OrderManager.indexOfOrder((int)tm.getValueAt(selRow, 1));
				OrderManager.order.get(index).setOrderList(order);
				Object [] data = new Object[4];
				data[0]=OrderManager.order.get(index).getOrderDate();
				data[1]=OrderManager.order.get(index).getOrderNumber();
				data[2]=OrderManager.order.get(index).totalPrice;
				data[3] = OrderManager.order.get(index).getApproval();
				tm.insertRow(selRow, data);
				tm.removeRow(selRow+1);
				OrderManager.orderCount++;
				setVisible(false);
				lo.setVisible(false);
			} else{
				JOptionPane.showMessageDialog(pan, "입력값이 없습니다.");
				setVisible(false);
				lo.setVisible(false);
			}
		}	
	}
	public class cancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			order.clear();
			setVisible(false);
			lo.setVisible(false);
		}
		
	}
}
class listOrder extends JFrame{

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	JTable orderTable;
	DefaultTableModel model;
	Container content;
	sellMenu sell;
	JScrollPane scroll;
	String info[] = {
			"메뉴명",
			"수량",
			"금액"
	};
	listOrder(){
		content = getContentPane();
		setTitle("주문 내용");
		setArea();
		content.add(scroll);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(1200,200,500,800);
		
	}
	void setArea(){
		model = new DefaultTableModel(info,0);
		orderTable = new JTable(model);
		orderTable.setRowHeight(25);
		orderTable.setFont(new Font("굴림",Font.CENTER_BASELINE,20));
		scroll = new JScrollPane(orderTable);
	}
	void addArea(sellMenu sell,int Price){
		int index =checkArea(sell.getName(),model);
		if(index <model.getRowCount()){
			Object[] data = new Object[3];
			data[0]=sell.getName();
			data[1]=sell.getCount();
			data[2]=Price;
			model.insertRow(index, data);
			model.removeRow(index+1);
		} else{
			Object[] data = new Object[3];
			data[0]=sell.getName();
			data[1]=sell.getCount();
			data[2]=Price;
			model.addRow(data);
		}
	}
	public int checkArea(String menu,DefaultTableModel md){
		String Value;
		int index= md.getRowCount()+1;
		for(int i = 0; i<md.getRowCount();i++){
			Value = (String) md.getValueAt(i, 0);
			if(Value.equals(menu)){
				index = i;
				break;
			}
		}
		return index;
	}
}