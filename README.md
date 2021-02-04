# Seal Library
A Seal Library foi criada com o objetivo de tornar mais fácil para mim(Focamacho) criar plugins compatíveis com múltiplas plataformas.<br><br>
Para suas funcionalidades funcionarem corretamente é necessário ter um plugin de economia e permissões compatível.<br>
Os plugins suportados até o momento são:
- **Permissões:**
  - [LuckPerms](https://luckperms.net/)
  - [BungeePerms](https://www.spigotmc.org/resources/bungeeperms.25/)
  - Um plugin de permissões compatível com a API do [Vault](https://www.spigotmc.org/resources/vault.34315/).
- **Economia:**
  - Um plugin de economia compatível com a API do Sponge.
  - Um plugin de economia compatível com a API do [Vault](https://www.spigotmc.org/resources/vault.34315/).

As implementações feitas até o momento são as seguintes:

**Para Bukkit, Sponge e BungeeCord:**
- Criação de arquivos de configuração e idioma.
- Manipulação de jogadores, incluindo métodos para manipulação de permissões, kick, enviar mensagem...
- MessageWaiter para aguardar a mensagem de um jogador no chat.

**Para Bukkit e Sponge:**
- Criação de Menus com inventários de baú.
- Criação e manipulação de ItemStacks.
- Manipulação de jogadores, incluindo métodos para manipulação de economia, abrir menus...
- Conversão de ItemStacks do MCP para do Bukkit/Sponge e vice-versa.