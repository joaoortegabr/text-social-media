Social media created using Java as an exercise for Sinqia training's first module.

# A Rede Social

Você deverá implementar sua própria rede social textual, ou seja, toda a interface do sistema deverá ser em modo texto, e o usuário deverá
interagir com as "telas” por meio de comandos do teclado.
A rede social deve possuir uma lista de perfis, onde cada perfil deve possui nome, login e senha.

A primeira tela a ser mostrada deve ser o menu inicial.
## 1. Menu inicial
De início, deverão ser apresentadas três opções: “cadastrar-se”, “entrar” e “fechar”. Poderão ser usados códigos de atalho para todas as opções.
Exemplo:
“C” para cadastrar-se.
“E” para entrar
“F” para fechar,

### 1.1 Fechar
Se o usuário selecionar a opção “fechar” o programa deverá ser encerrado.

### 1.2 Cadastrar-se
Só deverá ser possível fazer login se o usuário estiver cadastrado. Logo, o cadastro deverá ser a primeira ação a ser realizada pelo usuário para conseguir entrar na rede. Nessa etapa, o sistema
deve solicitar que o usuário digite nome, login e senha. Em seguida, o sistema deverá validar os valores digitados e,se os dados estiverem corretos, criar um perfil. Ao final, o perfilrecém-criado
deverá ser adicionado à lista de perfis da rede social e uma mensagem de sucesso deverá ser exibida. Se houver dados inválidos, mensagens de erro deverão ser mostradas e o menu inicial
exibido novamente.

Validação obrigatória:
O nome de usuário (login) deve ser único, isto é, caso o usuário digite um login que já esteja sendo usado, deverá ser mostrada uma mensagem de erro.
O nome, login e senha não podem ser vazios.
Após a conclusão do cadastro, o menu inicial deverá ser mostrado novamente.

### 1.3 Entrar
A opção “entrar” deverá mostrar a tela de login. O login consiste em receber um nome de usuário (login) e uma senha digitados, percorrer a lista de usuários da rede social e retornar o
usuário com o nome igual ao digitado, caso exista. Se não houver usuário com o nome informado, levantar uma exceção específica para o caso (sugestão: UserNotFoundException)
com a respectiva mensagem de erro.

Se o usuário existir, verificar se a senha corresponde à informada, caso contrário, levantar uma exceção do tipo InvalidPasswordException ou similar com a respectiva mensagem de erro e
voltar para o menu do usuário. Se o login for bem-sucedido, mostrar o menu do usuário.

## 2 Menu do usuário
Após o login, deverá ser apresentado um menu com a mensagem: Bem-vindo, {nome}. Onde {nome} deverá ser substituído pelo nome do usuário. Além da mensagem de boas-vindas,
também deverá ser exibida uma lista de opções, são elas: “postar”, “timeline” ou “sair”.

### 2.1 Postar
Além do nome, login e senha, um usuário também deve ter uma lista de posts. Um post deve possuir data, hora e conteúdo. O conteúdo do post será apenas um texto. Os campos data e
hora devem ser strings formatadas, exemplo:
data: “22/11/2022”
hora: “22:38”
Ao publicar um post, o usuário deverá informar a data, hora e o conteúdo a ser postado. Ao final o post deverá ser adicionado à lista de posts do usuário e o sistema deverá retornar ao menu de
usuário.

### 2.2 Timeline
Esta opção deverá mostrar o histórico de publicações (posts) do usuário que está logado, no formato do exemplo abaixo:
21/11/2022 às 23:43 – “Boa noite!”
22/11/2022 às 07:21 – “Bom dia!”
22/11/2022 às 13:55 – “Boa tarde!”
Depois de exibir a timeline, o menu do usuário deverá ser disponibilizado novamente.

### 2.3 Sair
Estão opção deve encerrar a sessão do usuário, que neste caso consiste em simplesmentemostrar a tela inicial.
