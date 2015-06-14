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
			"�޴��� �Ǹ� ��Ȳ",
			"���� �Ǹ� ��Ȳ",
			"���Ϻ� ������Ȳ"
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
		content.add(new JLabel("��Ʈ ����"));
		box = new JComboBox<String>(ChartList);
		content.add(box);
		confirm  = new JButton("Ȯ��");
		confirm.addActionListener(new confirmListener());
		cancel = new JButton("���");
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
				CountPerMenu cpm = new CountPerMenu("�޴��� �Ǹ���Ȳ");
				//cpm.pack();
				RefineryUtilities.centerFrameOnScreen(cpm);
				cpm.setVisible(true);
				setVisible(false);
			} else if(list.equals(ChartList[1])){
				CountperMonth cpM = new CountperMonth("���� ������Ȳ");
				//cpM.pack();
				RefineryUtilities.centerFrameOnScreen(cpM);
				cpM.setVisible(true);
				setVisible(false);
			}
			else if(list.equals(ChartList[2])){
				Countperday cpd = new Countperday("���Ϻ� ������Ȳ");
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