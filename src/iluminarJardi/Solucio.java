package iluminarJardi;


import java.util.TreeSet;

import Keyboard.*;

public class Solucio {
	private static Bombeta[] bombetes; // Conjunt de bombetes a col·locar
 	private int M;
 	private static PuntLlum[] puntsLlum; // Conjunt de punts de llum disponibles
	private int N;
	private boolean [][] encaixen; // Informació de compatibilitat punt de llum-bombeta
	@SuppressWarnings("rawtypes")
	private TreeSet<Bombeta>[] solucio;
	private TreeSet<Bombeta>[] millorSolucio;
	private int duradaMinima;
	
	
	public Solucio(int numBombetes, int numPuntsLlum){
		N = numPuntsLlum;
		puntsLlum = new PuntLlum[N];
		M = numBombetes;
		bombetes = new Bombeta[M];
		this.duradaMinima=0;
		encaixen = new boolean[M][N];
		for(int i=0; i<M; i++){
			for(int j=0; j<N; j++){
				encaixen[i][j] = false;
			}
		}
	}
	
	  ///////////////////////////////////
	 ////////		Main		////////
	///////////////////////////////////
	
	public static void main(String[] args) {
		int qPuntsllum = 0;
		int qBombetes = 0;
		Solucio s = null;
		System.out.println("Generar aleatòriament? Y/N");
		char input;
		do{
			input=Character.toUpperCase(Keyboard.readChar());
		}while(input!='N' && input!='Y');
		if (input == 'N') {
			do{
				System.out.println("Entra quants punts de llum hi han disponibles:");
				qPuntsllum = Keyboard.readInt();
			}while(qPuntsllum<0);
			do{
				System.out.println("Entra quantes bombetes hi han disponibles(han de ser igual o més que la quantitat de punts):");
				qBombetes = Keyboard.readInt();
			} while(qBombetes<qPuntsllum);
			s = new Solucio(qBombetes, qPuntsllum);
			carregarMagatzemManualment(qPuntsllum, qBombetes, s);
		} else {
			qPuntsllum = (int) Math.floor((Math.random() * 10) + 1);
			qBombetes = qPuntsllum+(int) Math.floor((Math.random() * 5) + 1);
			s = new Solucio(qBombetes, qPuntsllum);
			carregarMagatzemAleatori(qPuntsllum, qBombetes, s);
		}
		s.getMillorSolucio();
		System.out.println(s);

	}
	
	// END Main
	
	private static void carregarMagatzemManualment(int qPuntsllum, int qBombetes, Solucio s){
		int cont=0;
		String idActual;
		do {
			System.out.println("Entra la ID del llum nº"+(cont+1)+" :");
			idActual = Keyboard.readString();
			for(int i=0; i<cont;i++){
				if(Solucio.puntsLlum[i].getID().equals(idActual)){
					System.out.println("Aquest punt de llum ja existeix");
				}
			}
			Solucio.puntsLlum[cont]=new PuntLlum(idActual);
			cont++;
			idActual = "";
		} while (cont<qPuntsllum);
		cont=0;
		int duracio;
		do {
			System.out.println("Entra la durada de la bombeta nº"+(cont+1)+" :");
			duracio = Keyboard.readInt();
			Solucio.bombetes[cont]=new Bombeta(cont, duracio);
			do{
				System.out.println("Entra la ID d'un punt de llum on encaixa aquesta bombeta o un punt per acabar:");
				idActual = Keyboard.readString();
				boolean trobat = false;
				for(int i=0; i<qPuntsllum;i++){
					if(Solucio.puntsLlum[i].getID().equals(idActual)){
						trobat = true;
						s.encaixen[cont][i] = true;
					}
				}
				if(!trobat&&!idActual.equals(new String(".")))	System.out.println("ERROR: ID incorrecte");
			}while(!idActual.equals(new String(".")));
			cont++;
		} while (cont<qBombetes);
	}
	private static void carregarMagatzemAleatori(int qPuntsllum, int qBombetes, Solucio s){
		for (int i = 0; i < qPuntsllum; i++) {
			Solucio.puntsLlum[i] = new PuntLlum(i+"");
		}
		int qEncaixen; 
		int puntEncaixen;
		for (int i = 0; i < qBombetes; i++) {
			Solucio.bombetes[i] = new Bombeta(i, (int) Math.floor((Math.random() * 50) + 1));
			qEncaixen = (int) Math.floor((Math.random() * qPuntsllum-1) + 1);
			for(int j=0; j<qEncaixen;j++){
				puntEncaixen = (int) Math.floor(Math.random() * qPuntsllum);
				s.encaixen[i][puntEncaixen] = true;
			}
		}
	}
	
	  //////////////////////////////////////////
	 ////////		Backtracking		///////
	//////////////////////////////////////////
	
	private void backMillorDistribucio (int bombeta){		
		for(int i=0;i<puntsLlum.length;i++){
			
				// if(encaixen[bombeta][i]){ // if(esAcceptable)
				if(true){ // if(esAcceptable)
					solucio[i].add(bombetes[bombeta]);
					
					if(bombeta==bombetes.length-1){ // Si es solució (totes les bombetes usades).
						if(esMillorSolucio()){
							for(int j=0;j<puntsLlum.length;j++){
								this.millorSolucio[j].clear();
								this.millorSolucio[j].addAll(solucio[j]);
							}
						}
					}
					else backMillorDistribucio(bombeta+1); // Si no és solució, sempre será completable

					solucio[i].remove(bombetes[bombeta]);
				}
			}	
		}


	private boolean esMillorSolucio(){
		int duradaPunt=0;
		for(int i=0;i<puntsLlum.length;i++){
			TreeSet<Bombeta> ts=solucio[i];
			for(Bombeta b:ts){
				duradaPunt+=b.getDuracio();
			}
			if(duradaPunt<duradaMinima){
				duradaMinima=duradaPunt; // S'assigna la millor durada aquí per no haber-la de calcular un altre vegada.
				return true;
			}
		}
		return false;
	}
	
	public String toString(){

		String output;
		output = "MAGATZEM \n";
		output += "\n----------------\n";
		output += "Llistat de Bombetes (" + M + "): \n";
		output += "----------------\n\n";
		for (int j = 0; j < bombetes.length; j++) {
			output+=bombetes[j].toString()+"\n";
		}
		
		output += "\n----------------\n";
		output += "Punts de Llum (" + N + "): \n";
		output += "----------------\n\n";
		for (int i = 0; i < puntsLlum.length; i++) {
			int duracio =0 ;
			output += '\n' + puntsLlum[i].toString() + "\n";
			TreeSet<Bombeta> ts=puntsLlum[i].getBombetes();
			output += "\n Bombetes contingudes:";
			for(Bombeta b : ts){
				output += '\n' + '\t'+  b.toString() ;
				duracio+=b.getDuracio();
			}
			output+="\n\n Duració Total: " + duracio +"\n\n";
		}
		
		return output;

	}
	
	public void getMillorSolucio(){
		
		this.millorSolucio = new TreeSet[puntsLlum.length];
		this.solucio = new TreeSet[puntsLlum.length];
		for(int i=0;i<millorSolucio.length;i++){
			millorSolucio[i] = new TreeSet<Bombeta>();
			solucio[i] = new TreeSet<Bombeta>();
		}
		this.backMillorDistribucio(0);
		for(int i=0;i<millorSolucio.length;i++){
			for(Bombeta b:millorSolucio[i]){
				puntsLlum[i].addBombeta(b);
			}
		}
	}
	
}
