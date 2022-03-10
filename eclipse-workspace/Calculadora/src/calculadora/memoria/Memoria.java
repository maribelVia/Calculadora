package calculadora.memoria;

import java.util.ArrayList;
import java.util.List;

public class Memoria {
	
	private enum TipoComando{
		ZERAR, NUMERO, DIV, MULT, SUB, SOMA, IGUAL, VIRGULA, SINAL, PORCENTAGEM, VOLTAR,
		POTENCIA, RAIZ, DIVX;
	};
	
	private static final Memoria instancia = new Memoria();
	
	private final List<MemoriaObservador> observadores = new ArrayList<>();
	
	private TipoComando ultimaOperacao = null;
	private boolean substitui = false;
	private String textoAtual = "";
	private String textoBuffer = "";
	
	private Memoria() {}
	
	public static Memoria getInstancia() {
		return instancia;
	}
	
	public void adicionarObservador(MemoriaObservador observador) {
		observadores.add(observador);
	}
	
	public String getTextoAtual() {
		return textoAtual.isEmpty() ? "0" : textoAtual;
	}
	
	public void processarComando(String texto) {
		
		TipoComando tipoComando = detectarTipoComando(texto);
		
		if(tipoComando == null) {
			return;
		} else if(tipoComando == TipoComando.ZERAR) {
			ultimaOperacao = null;
			substitui = false;
			textoAtual = "";
			textoBuffer = "";
		} else if(tipoComando == TipoComando.SINAL && textoAtual.contains("-")) {
			textoAtual = textoAtual.substring(1);
		} else if(tipoComando == TipoComando.SINAL && !textoAtual.contains("-")) {
			textoAtual = "-" + textoAtual;
		} else if(tipoComando == TipoComando.NUMERO || tipoComando== TipoComando.VIRGULA) {
			textoAtual = substitui ? texto : textoAtual + texto;
			substitui = false;
		} else if(tipoComando == TipoComando.VOLTAR) {
			textoAtual = textoAtual.replaceFirst(".$", "");
		} else {
			substitui = true;
			textoAtual = obterResultado();
			textoBuffer = textoAtual;
			ultimaOperacao = tipoComando;
		}
		
		observadores.forEach(o -> o.valorAlternado(getTextoAtual()));
	}

	private String obterResultado() {
		if(ultimaOperacao == null || ultimaOperacao == TipoComando.IGUAL) {
			return textoAtual;
		}
		
		double numeroBuffer = Double.parseDouble(textoBuffer.replace(",", "."));
		double numeroAtual = Double.parseDouble(textoAtual.replace(",", "."));
		double resultado = 0;
		
		if(ultimaOperacao == TipoComando.SOMA) {
			resultado = numeroBuffer + numeroAtual;
		} else if(ultimaOperacao == TipoComando.SUB) {
			resultado = numeroBuffer - numeroAtual;
		} else if(ultimaOperacao == TipoComando.MULT) {
			resultado = numeroBuffer * numeroAtual;
		} else if(ultimaOperacao == TipoComando.DIV) {
			resultado = numeroBuffer / numeroAtual;
		} else if(ultimaOperacao == TipoComando.PORCENTAGEM) {
				resultado = numeroAtual * 0.01;
		} else if(ultimaOperacao == TipoComando.POTENCIA){
			resultado = numeroAtual * numeroAtual;
		} else if(ultimaOperacao == TipoComando.RAIZ) {
			resultado = Math.sqrt(numeroAtual);
		} else if(ultimaOperacao == TipoComando.DIVX) {
			resultado = 1 / numeroAtual;
			System.out.println(resultado);
		}
		
		String exibeResultado = Double.toString(resultado).replace(".", ",");
		boolean inteiro = exibeResultado.endsWith(",0");
		
		return inteiro ? exibeResultado.replace(",0", "") : exibeResultado;
	}

	private TipoComando detectarTipoComando(String texto) {
		if(textoAtual.isEmpty() && texto =="0") {
			return null;
		}
		
		try {
			Integer.parseInt(texto);
			return TipoComando.NUMERO;
		} catch (NumberFormatException e){
			if("CE".equals(texto)) {
				return TipoComando.ZERAR;
			} else if("/".equals(texto)) {
				return TipoComando.DIV;
			} else if("X".equals(texto)) {
				return TipoComando.MULT;
			} else if("+".equals(texto)) {
				return TipoComando.SOMA;
			} else if("-".equals(texto)) {
				return TipoComando.SUB;
			}else if(",".equals(texto) && !textoAtual.contains(",")) {
				return TipoComando.VIRGULA;
			} else if("=".equals(texto)) {
				return TipoComando.IGUAL;
			} else if("+/-".equals(texto)) {
				return TipoComando.SINAL;	
			} else if("%".equals(texto)) {
				return TipoComando.PORCENTAGEM;
			} else if("<-".equals(texto)) {
				return TipoComando.VOLTAR;
			} else if("x²".equals(texto)) {
				return TipoComando.POTENCIA;
			} else if("sqrt(x)".equals(texto)) {
				return TipoComando.RAIZ;
			} else if("1/x".equals(texto)) {
				return TipoComando.DIVX;
			}
		}
		return null;
	}
}
