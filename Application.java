package projeto;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import projeto.entities.Perfil;
import projeto.entities.Post;
import projeto.exceptions.InvalidPasswordException;
import projeto.exceptions.UserNotFoundException;

public class Application {

	static List<Perfil> perfis = new ArrayList<Perfil>();
	static Perfil perfilLogado = new Perfil();
	static DateTimeFormatter dataFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	static DateTimeFormatter horaFormat = DateTimeFormatter.ofPattern("HH:mm:SS");
	static Scanner sc = new Scanner(System.in);
	
	static boolean flagMenu = true;
	static boolean flagMenuLogado = true;
	static boolean welcomeFlag = true;
	
	public static void main(String[] args) {
		
		while(flagMenu) {
			loadMenu();
			if(flagMenuLogado) {
				if(userLogged()) {
					while(flagMenuLogado) {
						loadMenuLogado();
					}
				}
			}
		}
		sc.close();
	}

	static void loadMenu() {
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
			case "V":
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
		
	static void loadMenuLogado() {
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
	
	static void createPerfil() {
		Perfil perfil = new Perfil();
		
		System.out.println("Informe um nome: ");
		String nome = sc.nextLine();
		if(nome.equals("")) {
			System.out.println("O nome não pode ser vazio.");
			return;
		} else {
			perfil.setNome(nome);
		}
		System.out.println("Informe um login: ");
		String login = sc.nextLine();
		if(login.equals("")) {
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
		if(senha.equals("")) {
			System.out.println("A senha não pode ser vazia.");
			return;
		} else {
			perfil.setSenha(senha);
		}
		perfis.add(perfil);
		System.out.println("Usuário " + perfil.getNome() + " cadastrado com sucesso!");
	}
	
	static void login() {
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
	
	static void executeLogin() throws UserNotFoundException, InvalidPasswordException {
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
						System.out.println("Usuário " + p.getNome() + " logado com sucesso.");
					} else {
						throw new UserNotFoundException("Senha incorreta. Tente novamente.");
					}
				}
			}
		} else {
			throw new UserNotFoundException("Este perfil não existe. Tente novamente.");
		}
	}
	
	static void listPerfis() {
		if(systemHasAnyUser()) {
			System.out.println("Usuários cadastados no sistema: ");
			for(Perfil i : perfis) {
				System.out.println("Nome: " + i.getNome() + " - Login: " + i.getLogin());
			}
			System.out.println("-----------------------------");
		}
	}

	static void createPost() {
		System.out.println("Escreva o conteúdo do post.");
		String conteudo = sc.nextLine();
		if(conteudo.equals("") || conteudo.isBlank()) {
			System.out.println("Nenhum conteúdo inserido no post.");
			return;
		} else {
			perfilLogado.generateNewPost(conteudo);
			System.out.println("Post criado com sucesso!");
		}
	}
	
	static boolean timelineHasPosts() {
		if(perfilLogado.getPosts().size() > 0) {
			return true;
		} else {
			System.out.println("Ainda não há nenhum post deste usuário.");
			return false;
		}
	}
	
	static void viewTimelineOfLoggedUser() {
		if(timelineHasPosts()) {
			System.out.println("Timeline do usuário " + perfilLogado.getLogin());
			System.out.println("-----------------------------");
			for(Post p : perfilLogado.getPosts()) {
				System.out.println(
					"Post " + p.getId() 
					+ "\nData: " + p.getData().format(dataFormat) + " " + p.getHora().format(horaFormat) 
					+ "\nTexto: " + p.getConteudo()
					+ "\n-----------------------------");
			}
		};
	}
	
	static void listPostsOfOtherUser() {
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
									+ "\nData: " + i.getData().format(dataFormat) + " " + i.getHora().format(horaFormat)
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
	
	static void checkWelcome() {
		if(welcomeFlag) {
			System.out.println("Bem-vindo(a), " + perfilLogado.getNome() +".");
		}
		welcomeFlag = false;
	}
	
	static boolean loginExists(String login) {
		boolean loginExists = false;
		for (Perfil p : perfis) {
			if(login.equalsIgnoreCase(p.getLogin())) {
				loginExists = true;
			}
		}
		return loginExists;
	}
	
	static boolean systemHasAnyUser() {
		if(perfis.size() > 0) {
			return true;
		} else {
			System.out.println("Não há nenhum perfil cadastrado na rede.");
			return false;
		}
	}
	
	static boolean userLogged() {
		if(perfilLogado.getLogin() != null) {
			return true;
		} else {
			return false;
		}
	}
		
	static void logout() {
		if(userLogged()) {
			perfilLogado.setLogin(null);
		}
		flagMenuLogado = false;
		welcomeFlag = true;
		System.out.println("\n-----------------------------");
		System.out.println("Usuário deslogado.");
	}
	
	static void systemShutdown() {
		System.out.println("\n-----------------------------");
		System.out.println("Sistema encerrado.");
		flagMenuLogado = false;
		flagMenu = false;
	}
	
}
