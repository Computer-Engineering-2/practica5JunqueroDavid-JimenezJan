package iluminarJardi;
import java.util.TreeSet;

public class PuntLlum {
	private String id;
	private TreeSet<Bombeta> bombetes;
	public PuntLlum(String id){
		this.id = id;
		this.bombetes = new TreeSet<Bombeta>();
	}
	public String getID(){
		return id;
	}
	public int getQBombetes(){
		return bombetes.size();
	}
	public void addAll(TreeSet<Bombeta> b){
		this.bombetes.addAll(b);
	}
	public void addBombeta(Bombeta b){
		if(bombetes.contains(b)) throw new IllegalArgumentException("La bombeta ja existeix en el punt de llum indicat");
		bombetes.add(b);
	}
	public void removeBombeta(Bombeta b){
		if(!bombetes.contains(b)) throw new IllegalArgumentException("La bombeta NO existeix en el punt de llum indicat");
	}
	public String toString(){
		return "Punt de Llum nº"+id;
	}
	
	public int duracioTotal(){
		int duracio=0;
		for(Bombeta b:bombetes){
			duracio+=b.getDuracio();
		}
		return duracio;
	}
	public TreeSet<Bombeta> getBombetes(){return this.bombetes;}
}
