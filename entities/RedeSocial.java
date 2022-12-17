package projeto.entities;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import projeto.exceptions.InvalidPasswordException;
import projeto.exceptions.UserNotFoundException;

public class RedeSocial {

	DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
	Scanner sc = new Scanner(System.in);
	
	private List<Perfil> perfis = new ArrayList<Perfil>();
	private Perfil perfilLogado = new Perfil();
	
	private boolean flagMenu;
	private boolean flagMenuLogado = true;
	private boolean welcomeFlag = true;
	
	public RedeSocial() {
		this.flagMenu = true;
		this.flagMenuLogado = true;
		this.welcomeFlag = true;
	}

	public boolean isFlagMenu() {
		return flagMenu;
	}
	
	public boolean isFlagMenuLogado() {
		return flagMenuLogado;
	}
	
	public void loadMenu() {
		System.out.println("\n====== MENU PRINCIPAL =======");
		System.out.println("Escolha uma opção: ");
		System.out.println("C - Cadastrar\n"
					+ "E - Entrar\n"
					+ "T - Ver timeline de um usuário\n"
					+ "L - Listar usuários\n"
					+ "F - Fechar");
		
		String menu = sc.nextLine().toUpperCase();
		switch(menu) {
			case "C":
				createPerfil();
				break;
			case "E":
				login();
				break;
			case "T":
				listPostsOfOtherUser();
				break;
			case "L":
				listPerfis();
				break;
			case "F":
				systemShutdown();
				break;
			default: 
				System.out.println("Opção inválida.");
		}
	}
	
	public void loadMenuLogado() {
		checkWelcome();
		System.out.println("\n====== MENU DO USUÁRIO ======");
		System.out.println("Escolha uma opção: ");
		System.out.println("P - Criar novo post\n"
					+ "T - Ver timeline de um usuário\n"
					+ "M - Minha timeline\n"
					+ "D - Deslogar\n"
					+ "F - Fechar");
		
		String menuLogado = sc.nextLine().toUpperCase();
		switch(menuLogado) {
			case "P":
				createPost();
				break;
			case "T":
				listPostsOfOtherUser();
				break;
			case "M":
				viewTimelineOfLoggedUser();	
				break;
			case "D":
				logout();
				break;
			case "F":
				systemShutdown();
				break;
			default: 
				System.out.println("Opção inválida.");
		}
	}
	
	private void createPerfil() {
		Perfil perfil = new Perfil();
		
		System.out.println("Informe um nome: ");
		String nome = sc.nextLine();
		if(nome.equals(" ") || nome.isBlank()) {
			System.out.println("O nome não pode ser vazio.");
			return;
		} else {
			perfil.setNome(nome);
		}
		System.out.println("Informe um login: ");
		String login = sc.nextLine();
		if(login.equals(" ") || login.isBlank()) {
			System.out.println("O login não pode ser vazio.");
			return;
		} else if (loginExists(login)){
			System.out.println("Este login já existe.");
			return;
		} else {
			perfil.setLogin(login);
		}
		System.out.println("Informe uma senha: ");
		String senha = sc.nextLine();
		if(senha.equals(" ") || senha.isBlank()) {
			System.out.println("A senha não pode ser vazia.");
			return;
		} else {
			perfil.setSenha(senha);
		}
		perfis.add(perfil);
		System.out.println("O perfil de " + perfil.getNome() + " foi criado com sucesso!");
	}
	
	private void login() {
		if(systemHasAnyUser()) {
			try {
				executeLogin();
				flagMenuLogado = true;
			} catch (UserNotFoundException e) {
				System.out.println(e.getMessage());
			} catch (InvalidPasswordException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
	private void executeLogin() throws UserNotFoundException, InvalidPasswordException {
		System.out.println("Informe o login: ");
		String login = sc.nextLine();		
		if(loginExists(login)) {
			for (Perfil p : perfis) {
				if(login.equalsIgnoreCase(p.getLogin())) {
					System.out.println("Informe a senha: ");
					if(sc.nextLine().equals(p.getSenha())) {
						perfilLogado.setLogin(p.getLogin());
						perfilLogado.setNome(p.getNome());
						perfilLogado.setSenha(p.getSenha());
						perfilLogado.setPosts(p.getPosts());
						System.out.println("\n-----------------------------");
						System.out.println("Usuário " + p.getLogin() + " logado com sucesso.");
					} else {
						throw new UserNotFoundException("Senha incorreta. Tente novamente.");
					}
				}
			}
		} else {
			throw new UserNotFoundException("Este perfil não existe. Tente novamente.");
		}
	}
	
	private void listPerfis() {
		if(systemHasAnyUser()) {
			System.out.println("Usuários cadastrados no sistema: ");
			for(Perfil i : perfis) {
				System.out.println("Nome: " + i.getNome() + " - Login: " + i.getLogin());
			}
			System.out.println("-----------------------------");
		}
	}

	private void createPost() {
		System.out.println("Escreva o conteúdo do post:");
		String conteudo = sc.nextLine();
		if(conteudo.equals(" ") || conteudo.isBlank()) {
			System.out.println("Nenhum conteúdo inserido no post.");
			return;
		} else {
			perfilLogado.generateNewPost();
			int id = perfilLogado.getPosts().size() - 1;
			perfilLogado.getPosts().get(id).setConteudo(conteudo);
			System.out.println("Post criado com sucesso!");
		}
	}
	
	private boolean timelineHasPosts() {
		if(perfilLogado.getPosts().size() > 0) {
			return true;
		} else {
			System.out.println("Ainda não há nenhum post deste usuário.");
			return false;
		}
	}
	
	private void viewTimelineOfLoggedUser() {
		if(timelineHasPosts()) {
			System.out.println("Timeline do usuário " + perfilLogado.getLogin());
			System.out.println("-----------------------------");
			for(Post p : perfilLogado.getPosts()) {
				System.out.println(
					"Post " + p.getId() 
					+ "\nData: " + p.getData().format(dataFormat) 
					+ "\nTexto: " + p.getConteudo()
					+ "\n-----------------------------");
			}
		};
	}
	
	private void listPostsOfOtherUser() {
		if(systemHasAnyUser()) {
			System.out.println("Deseja ver os posts de qual usuário?\nInforme o login dele: ");		
			String login = sc.nextLine();
			for(Perfil p : perfis) {
				if(loginExists(login)) {
					if(login.equalsIgnoreCase(p.getLogin())) {
						if(p.getPosts().size() > 0) {
							System.out.println("Timeline do usuário " + p.getLogin());
							System.out.println("-----------------------------");
							for(Post i : p.getPosts()) {
								System.out.println(
									"Post " + i.getId()
									+ "\nData: " + i.getData().format(dataFormat)
									+ "\nTexto: " + i.getConteudo()
									+ "\n-----------------------------");
							}
						} else {
							System.out.println("Este usuário ainda não criou nenhum post.");
						}
						return;
					}
				} else {
					System.out.println("Usuário não encontrado.");
					return;
				}
			}
		}
	}
	
	private void checkWelcome() {
		if(welcomeFlag) {
			System.out.println("Bem-vindo(a), " + perfilLogado.getNome() +".");
		}
		welcomeFlag = false;
	}
	
	private boolean loginExists(String login) {
		boolean loginExists = false;
		for (Perfil p : perfis) {
			if(login.equalsIgnoreCase(p.getLogin())) {
				loginExists = true;
			}
		}
		return loginExists;
	}
	
	private boolean systemHasAnyUser() {
		if(perfis.size() > 0) {
			return true;
		} else {
			System.out.println("Não há nenhum perfil cadastrado na rede.");
			return false;
		}
	}
	
	public boolean isAnyUserLogged() {
		if(perfilLogado.getLogin() != null) {
			return true;
		} else {
			return false;
		}
	}
		
	private void logout() {
		if(isAnyUserLogged()) {
			perfilLogado.setLogin(null);
		}
		flagMenuLogado = false;
		welcomeFlag = true;
		System.out.println("\n-----------------------------");
		System.out.println("Usuário deslogado.");
	}
	
	private void systemShutdown() {
		System.out.println("\n-----------------------------");
		System.out.println("Sistema encerrado.");
		flagMenuLogado = false;
		flagMenu = false;
	}
		
}
