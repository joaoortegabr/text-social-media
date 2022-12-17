package projeto.entities;

import java.time.LocalDateTime;

public class Post {

	private Long id;
	private LocalDateTime data;
	private String conteudo;
	
	public Post() {
	}

	public Post(LocalDateTime data, String conteudo) {
		this.data = data;
		this.conteudo = conteudo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	@Override
	public String toString() {
		return "Post " + id + "\nData: " + data + "\nTexto: " + conteudo;
	}
	
}
