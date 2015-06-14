import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;


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
		chart.getLegend().setItemFont(new Font("돋음",Font.PLAIN,12));
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