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


class CountPerMenu extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int count[] = new int[MenuManager.menu.size()];
	DefaultCategoryDataset dataset = new DefaultCategoryDataset();
	JFreeChart chart;

	public CountPerMenu(String title) {
		super(title);
		// TODO Auto-generated constructor stub
		getCountMenu();
		System.out.println(count);
		createDataset();
		createChart();
		ChartPanel chartPanel = new ChartPanel(chart);
		//chartPanel.setPreferredSize(new Dimension(500,300));
		setContentPane(chartPanel);
		setSize(800,600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	void getCountMenu(){
		for(Order i : OrderManager.order){
			for(sellMenu j : i.order){
				for(int k=0; k<MenuManager.menu.size();k++){
					if(j.getName().equals(MenuManager.menu.get(k).getName())){
						count[k]= count[k]+j.getCount();
					}
				}
			}
		}
	}
	void createDataset(){
		String category = "메뉴별 판매현황";
		for(int i=0;i<MenuManager.menu.size();i++){
			dataset.addValue(count[i],MenuManager.menu.get(i).getName(),category);
		}
	}
	void createChart(){
		chart = ChartFactory.createBarChart(
				"메뉴별 판매현황",
				"메뉴",
				"판매량",
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