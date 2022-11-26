package projeto.entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Post {

	private Long id;
	private LocalDate data;
	private LocalTime hora;
	private String conteudo;
	
	public Post() {
	}

	public Post(LocalDate data, LocalTime hora, String conteudo) {
		this.data = data;
		this.hora = hora;
		this.conteudo = conteudo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getData() {
		return data;
	}

	public void setData(LocalDate data) {
		this.data = data;
	}

	public LocalTime getHora() {
		return hora;
	}

	public void setHora(LocalTime hora) {
		this.hora = hora;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Post " + id + "\nData: " + data + " " + hora + "\nTexto: " + conteudo;
	}
	
}
