import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.SimpleDateFormat;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


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