package iluminarJardi;

public class Bombeta implements Comparable{
	private int id;
	private int duracio;
	public Bombeta(int id, int duracio){
		this.id = id;
		this.duracio = duracio;
	}
	public int getID(){
		return this.id;
	}
	public int getDuracio(){
		return this.duracio;
	}
	public String toString(){
		return "Bombeta nº"+id+" té una duració de: "+duracio;
	}
	public boolean equals(Object o){
		return this.compareTo(o)==0;
	}
	public int compareTo(Object o){
		if(!(o instanceof Bombeta)) throw new IllegalArgumentException("No es poden comparar Bombetes amb"+ o.getClass());
		return this.id-((Bombeta)o).getID();
	}
}
