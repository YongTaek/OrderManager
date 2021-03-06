import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ReadWrite {
	@SuppressWarnings("unchecked")
	public ArrayList<Menu> input(File f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			ArrayList <Menu>list = (ArrayList<Menu>)ois.readObject();
			return list;
		}catch(Exception ex){
			return null;
		}finally{
			try{
				if(ois!=null && fin!=null){
					ois.close();
					fin.close();
					}
			}catch(IOException ioe){}
		} // finally
	}
	public int input(){
		FileInputStream fin = null;
		ObjectInputStream ois = null;
		try{
			fin = new FileInputStream("OrderCount.txt");
			ois = new ObjectInputStream(fin);
			int i = (int) ois.readObject();
			return i;
		}catch(Exception ex){
			return 0;
		}finally{
			try{
				if(ois!=null && fin!=null){
					ois.close();
					fin.close();
				}
			}catch(IOException ioe){}
		}
	}
	@SuppressWarnings("unchecked")
	public ArrayList<Menu> input(String f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			ArrayList <Menu>list = (ArrayList<Menu>)ois.readObject();
			return list;
		}catch(Exception ex){
			return null;
		}finally{
			try{
				if(ois!=null && fin!=null){
					ois.close();
					fin.close();
					}
			}catch(IOException ioe){}
		} // finally		
	}
	public ArrayList<Order> inputOrder(File f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			@SuppressWarnings("unchecked")
			ArrayList <Order>list = (ArrayList<Order>)ois.readObject();
			return list;
		}catch(Exception ex){
			return null;
		}finally{
			try{
				if(ois!=null && fin!=null){
					ois.close();
					fin.close();
					}
			}catch(IOException ioe){}
		} // finally
	}
	public ArrayList<Order> inputOrder(String f){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(f);
			ois = new ObjectInputStream(fin);
			@SuppressWarnings("unchecked")
			ArrayList <Order>list = (ArrayList<Order>)ois.readObject();
			return list;
		}catch(Exception ex){
			return null;
		}finally{
			try{
				if(ois!=null && fin!=null){
					ois.close();
					fin.close();
					}
			}catch(IOException ioe){}
		} // finally		
	}
	public void output(ArrayList<Menu> b,File f){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
				
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
			
			System.out.println("저장되었습니다.");
		}catch(Exception ex){	
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}
	}
	public void output(ArrayList<Menu> b, String f){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
				
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
			
			System.out.println("저장되었습니다.");
		}catch(Exception ex){	
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}		
	}
	public void output(int OrderCount){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream("OrderCount.txt");
			oos = new ObjectOutputStream(fout);
				
			oos.writeObject(OrderCount);
			oos.reset();
			oos.writeObject(OrderCount);
			oos.reset();
			
			System.out.println("저장되었습니다.");
		}catch(Exception ex){	
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}		
	}
	public void outputOrder(ArrayList<Order> b,File f){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
				
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
			
			System.out.println("저장되었습니다.");
		}catch(Exception ex){	
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}
	}		
	public void outputOrder(ArrayList<Order> b, String f){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(f);
			oos = new ObjectOutputStream(fout);
				
			oos.writeObject(b);
			oos.reset();
			oos.writeObject(b);
			oos.reset();
			
			System.out.println("저장되었습니다.");
		}catch(Exception ex){	
		}finally{
			try{
				oos.close();
				fout.close();
			}catch(IOException ioe){}
		}	
	}
}

