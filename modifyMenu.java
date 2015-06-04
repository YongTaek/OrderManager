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


class modifyMenu extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Container contentPane;
	JPanel panel;
	JPanel mMenuPane;
	JPanel mPricePane;
	JPanel confirmPane;
	JLabel mmenulbl;
	JLabel mpricelbl;
	JTextField mmenutext;
	JTextField mpricetext;
	JButton confirm;
	JButton cancel;
	String beforename;
	DefaultTableModel tableModel;
	modifyMenu(DefaultTableModel tableModel,String name){
		this.tableModel = tableModel;
		this.beforename = name;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle("메뉴 수정");
		setBounds(500,500,250,200);
		modifyPane();
		contentPane = getContentPane();
		contentPane.add(panel);
		contentPane.setVisible(true);
	}
	void modifyPane(){
		panel = new JPanel();
		mMenuPane = new JPanel();
		mPricePane = new JPanel();
		confirmPane = new JPanel();
		panel.setLayout(new GridLayout(3,1));
		mMenuPane.setLayout(new FlowLayout());
		mPricePane.setLayout(new FlowLayout());
		confirmPane.setLayout(new FlowLayout());
		confirm = new JButton("수정");
		cancel = new JButton("취소");
		mmenulbl = new JLabel("메뉴");
		mpricelbl = new JLabel("가격");
		mmenutext = new JTextField("");
		mpricetext = new JTextField("");
		mmenutext.setPreferredSize(new Dimension(100,30));
		mpricetext.setPreferredSize(new Dimension(100,30));
		mmenulbl.setPreferredSize(new Dimension(50,30));
		mpricelbl.setPreferredSize(new Dimension(50,30));
		confirm.setPreferredSize(new Dimension(60,40));
		cancel.setPreferredSize(new Dimension(60,40));
		confirm.addActionListener(new confirmActionListener());
		cancel.addActionListener(new cancelActionListener());
		mMenuPane.add(mmenulbl);
		mMenuPane.add(mmenutext);
		mPricePane.add(mpricelbl);
		mPricePane.add(mpricetext);
		confirmPane.add(confirm);
		confirmPane.add(cancel);
		panel.add(mMenuPane);
		panel.add(mPricePane);
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
			String name = mmenutext.getText();
			String price = mpricetext.getText();
			if(name == null || isDigit(price)){
				JOptionPane.showMessageDialog(panel, "잘못입력하셨습니다.","경고",JOptionPane.ERROR_MESSAGE);
				mmenutext.setText("");
				mpricetext.setText("");
				setVisible(false);
			} else if(name != null && !isDigit(price)){
				int Row = MenuManager.indexMenu(beforename);
				System.out.println(Row);
				if(MenuManager.modify(Row, name, Integer.parseInt(price))){
					Menu m = MenuManager.menu.get(Row);
					modifyRow(m,Row+1);
					JOptionPane.showMessageDialog(panel, "수정되었습니다.");
				} else{
					JOptionPane.showMessageDialog(panel, "수정 오류가 있습니다.");
				}

				mmenutext.setText("");
				mpricetext.setText("");
				setVisible(false);
			}
		}	
		public void modifyRow(Menu menu,int Row){
			Object[] data = new Object[2];
			data[0]=menu.getName();
			data[1]=menu.getPrice();
			tableModel.insertRow(Row, data);
			tableModel.removeRow(Row+1);
		}
	}
	class cancelActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			mmenutext.setText("");
			mpricetext.setText("");
			setVisible(false);
		}

	}
}