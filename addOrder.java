import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;


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
	private DefaultTableModel tm;
	addOrder(DefaultTableModel tm){
		contentPane = getContentPane();
		this.tm = tm;
		setTitle("주문 추가");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(200,200,1000,800);
		addPane();
		contentPane.add(pan);
		contentPane.setVisible(true);
	}
	void addPane(){
		order = new ArrayList<sellMenu>();
		pan = new JPanel();
		panel = new JPanel();
		panel2 = new JPanel();
		pan.setLayout(new BorderLayout());
		panel.setLayout(new GridLayout(5,5));
		panel.setPreferredSize(new Dimension(900,450));
		panel2.setLayout(new FlowLayout());
		orderconfirm = new JButton("추가");
		orderconfirm.addActionListener(new addconfirmListener());
		cancel = new JButton("취소");
		cancel.addActionListener(new cancelActionListener());
		menu = new JButton[MenuManager.menu.size()];
		for(int i=0;i<25-MenuManager.menu.size();i++){
			JButton b = new JButton();
			panel.add(b);
		}
		for(int i=0;i<MenuManager.menu.size();i++){
			menu[i] = new JButton(MenuManager.menu.get(i).getName());
			panel.add(menu[i],i);
			menu[i].addActionListener(new btnActionListener());
		}
		panel2.add(orderconfirm);
		panel2.add(cancel);
		pan.add(panel,BorderLayout.CENTER);
		pan.add(panel2,BorderLayout.SOUTH);
	}
	public class btnActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String name =((JButton)e.getSource()).getText();
			int price = MenuManager.menu.get(MenuManager.indexMenu(name)).getPrice();
			int index = checkOrder(name);
			if(index>order.size()){
				order.add(new sellMenu(name,price,1));
				System.out.println("$$"+order.size());
			} else{
				order.get(index).setCount(order.get(index).getCount()+1);
			}
			System.out.println(name);
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
		
	}
	public class addconfirmListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(order.size()!=0){
				System.out.println("a");
				OrderManager.order.add(new Order(OrderManager.orderCount+1,order));
				
				System.out.println(OrderManager.order.get(0).order.size());
				Object [] data = new Object[2];
				int i = OrderManager.indexOfOrder(OrderManager.orderCount+1);
				data[0]=OrderManager.order.get(i).getOrderNumber();
				data[1]=OrderManager.order.get(i).totalPrice;
				tm.addRow(data);
				OrderManager.orderCount++;
				System.out.println(OrderManager.order.get(0).order.size());
				setVisible(false);
			} else{
				JOptionPane.showMessageDialog(pan, "입력값이 없습니다.");
				setVisible(false);
			}
		}
	}
	public class cancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			order.clear();
			setVisible(false);
		}
		
	}
}