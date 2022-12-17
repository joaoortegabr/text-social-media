package projeto.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Perfil {

	private String nome;
	private String login;
	private String senha;
	private List<Post> posts = new ArrayList<Post>();
	
	public Perfil() {
	}

	public Perfil(String nome, String login, String senha) {
		this.nome = nome;
		this.login = login;
		this.senha = senha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Post> getPosts() {
		return posts;
	}
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void generateNewPost() {
		Post p = new Post();
		p.setData(LocalDateTime.now());
		if(posts == null) {
			p.setId((long) 1);
		} else {
			p.setId((long) (posts.size() + 1));
		}
		posts.add(p);
	}
	
	@Override
	public String toString() {
		return "Nome=" + nome + ", login=" + login + ", senha=" + senha;
	}
	
}
