import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

class sellMenu extends Menu{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String name;
	int price;
	int count;
	sellMenu(String name, int price, int count) {
		super(name, price);
		// TODO Auto-generated constructor stub
		this.name = name;
		this.price = price;
		this.count = count;
	}
	
	int getCount(){
		return count;
	}
	void setCount(int count){
		this.count = count;
	}
}
class Order implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	int orderNumber;
	int totalPrice = 0;
	ArrayList<sellMenu> order;
	Order(int orderNumber,ArrayList<sellMenu> order){
		this.orderNumber = orderNumber;
		this.order = order;
		for(sellMenu i : order){
			this.totalPrice+=i.getPrice() * i.getCount();
		}
	}
	int getOrderNumber(){
		return orderNumber;
	}
	int getTotalPrice(){
		return totalPrice;
	}
	ArrayList<sellMenu> getOrderList(){
		return order;
	}
}
public class OrderManager {
	ArrayList<Order> order = new ArrayList<Order>();
	
	
}
