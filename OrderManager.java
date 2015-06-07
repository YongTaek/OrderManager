import java.io.Serializable;
import java.util.ArrayList;

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
	String date;
	ArrayList<sellMenu> order;
	Order(int orderNumber,ArrayList<sellMenu> order,String date){
		this.orderNumber = orderNumber;
		this.order = order;
		this.date = date;
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
	String getOrderDate(){
		return date;
	}
}
public class OrderManager {
	static ArrayList<Order> order = new ArrayList<Order>();
	static int orderCount =0;
	ReadWrite fio = new ReadWrite();
	static int indexOfOrder(int n){
		int index=order.size()+1;
		for(int i=0; i<order.size(); i++){
			if(order.get(i).getOrderNumber()==n){
				index = i;
				break;
			}
		}
		return index;
	}
}
