package calculadora.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import calculadora.model.Memoria;

@SuppressWarnings("serial")
public class Teclado extends JPanel implements ActionListener{
	//private final Color CINZA_ESCURO = new Color(230, 230, 230);
	private final Color CINZA_MEDIO = new Color(240, 240, 240);
	private final Color MARFIM = new Color(250, 250, 250);
	private final Color AZUL_CLARO = new Color(163, 201, 239);
	
	public Teclado() {
		setLayout(new GridLayout(6, 4));
		
		adicionarBotao("%", CINZA_MEDIO);
		adicionarBotao("CE", CINZA_MEDIO);
		adicionarBotao("C", CINZA_MEDIO);
		adicionarBotao("<-", CINZA_MEDIO);
		
		adicionarBotao("1/x", CINZA_MEDIO);
		adicionarBotao("x²", CINZA_MEDIO);
		adicionarBotao("sqrt(x)", CINZA_MEDIO);
		adicionarBotao("/", CINZA_MEDIO);
		
		adicionarBotao("7", MARFIM);
		adicionarBotao("8", MARFIM);
		adicionarBotao("9", MARFIM);
		adicionarBotao("X", CINZA_MEDIO);
		
		adicionarBotao("4", MARFIM);
		adicionarBotao("5", MARFIM);
		adicionarBotao("6", MARFIM);
		adicionarBotao("-", CINZA_MEDIO);
		
		adicionarBotao("1", MARFIM);
		adicionarBotao("2", MARFIM);
		adicionarBotao("3", MARFIM);
		adicionarBotao("+", CINZA_MEDIO);
		
		adicionarBotao("+/-", MARFIM);
		adicionarBotao("0", MARFIM);
		adicionarBotao(",", MARFIM);
		adicionarBotao("=", AZUL_CLARO);
	}
	
	private void adicionarBotao(String texto, Color cor){
		setLayout(new GridLayout(6, 4));
		Botao botao = new Botao(texto, cor);
		botao.addActionListener(this);
		add(botao);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() instanceof JButton) {
			JButton botao = (JButton) e.getSource();
			Memoria.getInstancia().processarComando(botao.getText());
		}
	}
}
