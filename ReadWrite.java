import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class ReadWrite {
	public ArrayList<Menu> input(String string){
		FileInputStream fin = null;
		ObjectInputStream ois = null;

		try{
			fin = new FileInputStream(string);
			ois = new ObjectInputStream(fin);
			
			ArrayList<Menu> list = (ArrayList<Menu>)ois.readObject();
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
	
	public void output(ArrayList<Menu> b,String file){
		FileOutputStream fout = null;
		ObjectOutputStream oos = null;
		
		try{
			fout = new FileOutputStream(file);
			oos = new ObjectOutputStream(fout);
			Menu m = new Menu("히레까스",7000);
			Menu m2 = new Menu("로스까스",7500);
			b.add(m);
			b.add(m2);
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
