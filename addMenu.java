import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


class addMenu extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container contentPane;
	JPanel panel;
	JPanel aMenuPane;
	JPanel aPricePane;
	JPanel confirmPane;
	JLabel amenulbl;
	JLabel apricelbl;
	JTextField amenutext;
	JTextField apricetext;
	JButton confirm;
	JButton cancel;
	DefaultTableModel tableModel;
	addMenu(DefaultTableModel tableModel){
		this.tableModel = tableModel;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("새로운 메뉴 등록");
		setBounds(500,500,250,200);
		addPane();
		contentPane = getContentPane();
		contentPane.add(panel);
		contentPane.setVisible(true);
	}
	
	void addPane(){
		panel = new JPanel();
		aMenuPane = new JPanel();
		aPricePane = new JPanel();
		confirmPane = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		aMenuPane.setLayout(new FlowLayout());
		aPricePane.setLayout(new FlowLayout());
		confirmPane.setLayout(new FlowLayout());
		confirm = new JButton("등록");
		cancel = new JButton("취소");
		amenulbl = new JLabel("메뉴");
		apricelbl = new JLabel("가격");
		amenutext = new JTextField("");
		apricetext = new JTextField("");
		amenutext.setPreferredSize(new Dimension(100,30));
		apricetext.setPreferredSize(new Dimension(100,30));
		amenulbl.setPreferredSize(new Dimension(50,30));
		apricelbl.setPreferredSize(new Dimension(50,30));
		confirm.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		confirm.addActionListener(new confirmActionListener());
		cancel.addActionListener(new cancelActionListener());
		aMenuPane.add(amenulbl);
		aMenuPane.add(amenutext);
		aPricePane.add(apricelbl);
		aPricePane.add(apricetext);
		confirmPane.add(confirm);
		confirmPane.add(cancel);
		panel.add(aMenuPane);
		panel.add(aPricePane);
		panel.add(confirmPane);
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
	class confirmActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String name = amenutext.getText();
			String price = apricetext.getText();
			if(name == null || isDigit(price)){
				JOptionPane.showMessageDialog(panel, "잘못입력하셨습니다.","경고",JOptionPane.ERROR_MESSAGE);
				amenutext.setText("");
				apricetext.setText("");
				setVisible(false);
			} else if(name != null && !isDigit(price)){
				Menu m = new Menu(name,Integer.parseInt(price));
				MenuManager.menu.add(m);
				amenutext.setText("");
				apricetext.setText("");
				setVisible(false);
				addRow(m);
			}
		}	
		public void addRow(Menu menu){
			Object[] data = new Object[2];
			data[0]=menu.getName();
			data[1]=menu.getPrice();
			tableModel.addRow(data);
		}
	}
	class cancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			amenutext.setText("");
			apricetext.setText("");
			setVisible(false);
		}

	}
}