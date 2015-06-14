import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jfree.ui.RefineryUtilities;


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
		cancel.addActionListener(new cancelListener());
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
	public class cancelListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			setVisible(false);
		}
	}
}