package calculadora.visual;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;

import calculadora.memoria.Memoria;
import calculadora.memoria.MemoriaObservador;

@SuppressWarnings("serial")
public class Display extends JPanel implements MemoriaObservador{
	
	private final JLabel label;
	
	public Display() {
		Memoria.getInstancia().adicionarObservador(this);
		
		setBackground(new Color(230, 230, 230));
		label = new JLabel(Memoria.getInstancia().getTextoAtual());
		label.setForeground(Color.BLACK);
		label.setFont(new Font("courier", Font.PLAIN, 50));
		
		setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 55));
		
		add(label);
	}

	@Override
	public void valorAlternado(String novoValor) {
		label.setText(novoValor);
	}
	
}
