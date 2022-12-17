package projeto;

import projeto.entities.RedeSocial;

public class Application {

	public static void main(String[] args) {
		
		RedeSocial rede = new RedeSocial();
		
		while(rede.isFlagMenu()) {
			rede.loadMenu();
			if(rede.isFlagMenuLogado()) {
				if(rede.isAnyUserLogged()) {
					while(rede.isFlagMenuLogado()) {
						rede.loadMenuLogado();
					}
				}
			}
		}
		
	}		

}
