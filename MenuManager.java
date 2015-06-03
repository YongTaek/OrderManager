import java.util.ArrayList;

import javax.swing.JTextArea;


class Menu {
	String name;
	int price;
	
	Menu(String name, int price){
		this.name = name;
		this.price = price;
	}
	public String getName(){
		return name;
	}
	public int getPrice(){
		return price;
	}
	public void setName(String name){
		this.name = name;
	}
	public void setPrice(int price){
		this.price = price;
	}
}

class MenuManager{
	ArrayList<Menu> menu = new ArrayList<Menu>();
	ReadWrite fio = new ReadWrite();
	void Reader(){
		ArrayList<Menu> menu2 = fio.input("data.dat");
		if(menu2!=null){
			for(Menu i: menu2){
				menu.add(i);
			}
		}
	}
	
	void Writer(){
		fio.output(menu,"data.txt");
	}
	
	boolean add(Menu menu){
		try{
			this.menu.add(menu);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	boolean checkMenu(String name){
		boolean check = true;
		for(Menu i : menu){
			if(i.getName().equals(i)){
				check = false;
			}
		}
		return check;
	}
	
	int indexMenu(String name){
		int index = menu.size()+1;
		for(int i=0;i<menu.size();i++){
			if(menu.get(i).getName().equals(name)){
				index = i;
				break;
			}
		}
		return index;
	}
	boolean modify(int index,String name, int price){
		try{
			menu.get(index).setName(name);
			menu.get(index).setPrice(price);
			return true;
		}catch(Exception ex){
			return false;
		}
	}
	
	
	
}

