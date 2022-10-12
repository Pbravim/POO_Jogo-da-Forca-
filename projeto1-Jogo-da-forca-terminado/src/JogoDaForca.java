import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*O Jogo da Forca
É um jogo em que o jogador tem seis tentativas para adivinhar as letras de uma palavra, baseando-se
apenas na quantidade de letras e na dica da palavra.
O jogo começa com o sorteio de uma palavra e dica do arquivo de palavras. Em seguida, tem-se as
tentativas de adivinhação de uma letra dentro da palavra. A cada tentativa correta, o jogador recebe as
posições (ocorrências) da letra dentro da palavra. A cada tentativa errada, o jogador recebe uma penalidade
entre 1 e 6, que identifica a parte do corpo a ser retirada (1-“perna”, 2-“perna”, 3-”braço”, 4-”braço”, 5-”tronco”
e 6-”cabeça”), iniciando-se pela penalidade 1. O jogo termina com o acerto de todas as letras da palavra ou
com o enforcamento do jogador (penalidade 6). Uma tentativa repetida é contabilizada como erro.*/


public class JogoDaForca {
	private ArrayList<String> palavras = new ArrayList<>(); // lista de palavras lidas do arquivo
	private ArrayList<String> dicas = new ArrayList<>(); // lista de dicas lidas do arquivo
	private ArrayList<String> chutes = new ArrayList<>(); // letras adivinhadas
	private String dica=""; // dica da palavra sorteada
	private String[] letras; // letras da palavra sorteada
	private int acertos; // contador de acertos
	private int penalidade; // penalidade atual
	
	
	public JogoDaForca(String nomearquivo) throws Exception {
//		lê as palavras + dicas do arquivo e as coloca nas respectivas listas. 
//		Lança (throw) a exceção “arquivo inexistente”, caso o arquivo não exista.
			File arquivo = new File(nomearquivo);
			if (!arquivo.exists())
				throw new Exception("arquivo inexistente");
			Scanner sc = new Scanner(arquivo);
			String linha;
			String [] partes;
			while(sc.hasNextLine()) {
				linha = sc.nextLine();
				partes = linha.split(";");
				this.palavras.add(partes[0]);
				this.dicas.add(partes[1]) ;
			}
			sc.close();
	}

	public void iniciar() {
//		realiza o sorteio de uma das palavras existentes na lista de palavras.
		Random sortear = new Random();
		int tamanho, valor;
		String palavra;
//		tamanho = this.palavras.size();
//		valor = sortear.nextInt(tamanho);
		valor = sortear.nextInt(this.palavras.size());
//		palavra = palavras.get(valor);
//		this.letras = palavra.split("");
		this.letras = this.palavras.get(valor).split("");
		this.dica = this.dicas.get(valor);
		this.acertos = 0;
		this.penalidade = 0;
	}
	
	public String getDica() {
//		retorna a dica associada à palavra sorteada no momento.	
		return dica;
	}
	
	public int getTamanho() {
//	retorna o tamanho da palavra sorteada no momento
		return this.letras.length;
	}
	
	
	public ArrayList<Integer> getPosicoes(String letra) throws Exception{
//	retorna uma lista com as posições encontradas da letra na palavra sorteada ou retorna uma lista vazia. Substitui as
//	letras encontradas na palavra por “*”. Contabiliza um acerto, para cada ocorrência encontrada, ou incrementa a
//	penalidade, no caso da inexistência da letra. O parâmetro letra é válido se tem apenas 1 caractere alfabético sem
//	acento – pode ser maiúscula ou minúscula.
		if (!letra.matches("[a-zA-Z]+"))
			throw new Exception("Digite um caractere válido");
		if(letra.length() != 1)
			throw new Exception("Digite apenas um caractere");
		if (chutes.contains(letra))
		    	throw new Exception ("Adivinhe uma letra diferente");
		
		ArrayList<Integer> posicoes = new ArrayList<>();
		int contador = 0;
		chutes.add(letra);
	
		for (int i = 0; i < letras.length; i++) {
		    if (letra.toUpperCase().equals(letras[i])){
		      posicoes.add(i);
		      this.acertos += 1;
		      this.letras[i] = "*";
		    }
	
		    else
		      contador += 1;
		    }
		  if (contador == getTamanho())
			  penalidade += 1;
		    
		  return posicoes;

}
	
	
	public boolean terminou() {
//	retorna true, se o total de acertos é igual ao tamanho da palavra sorteada ou se a penalidade é 6.
		if (this.getAcertos() == this.getTamanho() || penalidade == 6)
			return true;
		else
			return false;
	}
		
	public int getAcertos() {
//	retorna o total de acertos
		return acertos;
	}
	
	public int getPenalidade() {
//	retorna a penalidade atual (0, 1, 2, ... 6)
		return penalidade;
	}
	
	public String getResultado() {
//	retorna “jogo em andamento”, “você venceu” ou “você foi enforcado”.
		if (!this.terminou())	
			return "jogo em andamento";
		else
			if (this.getPenalidade() ==6)
				return "você foi enforcado";
			else
				return "você venceu";
	}
}
