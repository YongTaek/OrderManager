import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;


class searchMenuOrder extends JFrame{

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
	String name;
	searchMenuOrder(String name){
		this.name=name;
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
			for(sellMenu j : OrderManager.order.get(i).getOrderList()){
				if(j.getName().equals(name)){
					data[0]=OrderManager.order.get(i).getOrderDate();
					data[1]=OrderManager.order.get(i).getOrderNumber();
					data[2]=OrderManager.order.get(i).totalPrice;
					data[3] = OrderManager.order.get(i).getApproval();
					tableModel.addRow(data);
					break;
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