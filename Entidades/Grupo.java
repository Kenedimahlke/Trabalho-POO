public class Grupo extends Usuario {
    private UsuarioIndividual[] membros;
    private UsuarioIndividual administrador;
    private int quantidadeMembros;
    private int limiteMaximoMembros;

    public Grupo(String nome, String email, UsuarioIndividual administrador) {
        super(nome, email);
        this.limiteMaximoMembros = 10; // Limite padrão
        this.membros = new UsuarioIndividual[limiteMaximoMembros];
        this.administrador = administrador;
        this.quantidadeMembros = 0;
        
        // Adiciona o administrador como primeiro membro
        this.membros[0] = administrador;
        this.quantidadeMembros = 1;
    }

    // CONSTRUTOR simplificado (compatibilidade com Main)
    public Grupo(String nome, String descricao) {
        super(nome, nome + "@grupo.com"); // Email gerado
        this.limiteMaximoMembros = 10;
        this.membros = new UsuarioIndividual[limiteMaximoMembros];
        this.administrador = null; // Será definido depois
        this.quantidadeMembros = 0;
    }

    @Override
    public boolean podeCriarLancamento() {
        // Grupo pode criar lançamento se tiver pelo menos um membro ativo
        for (int i = 0; i < quantidadeMembros; i++) {
            if (membros[i] != null && membros[i].isAtivo()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTipoPerfil() {
        return "GRUPO";
    }

    // Métodos específicos do Grupo
    public boolean adicionarMembro(UsuarioIndividual usuario) {
        // Verifica se há espaço disponível
        if (quantidadeMembros >= limiteMaximoMembros) {
            return false;
        }
        
        // Verifica se o usuário já é membro
        if (contemMembro(usuario)) {
            return false;
        }
        
        // Adiciona o novo membro
        membros[quantidadeMembros] = usuario;
        quantidadeMembros++;
        return true;
    }

    public boolean removerMembro(UsuarioIndividual usuario) {
        // Não pode remover o administrador
        if (usuario.equals(administrador)) {
            return false;
        }
        
        // Procura o usuário no array
        int indice = encontrarIndiceMembro(usuario);
        if (indice == -1) {
            return false; // Usuário não encontrado
        }
        
        // Remove o usuário movendo os elementos subsequentes
        for (int i = indice; i < quantidadeMembros - 1; i++) {
            membros[i] = membros[i + 1];
        }
        
        // Limpa a última posição e decrementa a quantidade
        membros[quantidadeMembros - 1] = null;
        quantidadeMembros--;
        return true;
    }

    // Método auxiliar para verificar se um usuário já é membro
    private boolean contemMembro(UsuarioIndividual usuario) {
        for (int i = 0; i < quantidadeMembros; i++) {
            if (membros[i] != null && membros[i].equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    // Método auxiliar para encontrar o índice de um membro
    private int encontrarIndiceMembro(UsuarioIndividual usuario) {
        for (int i = 0; i < quantidadeMembros; i++) {
            if (membros[i] != null && membros[i].equals(usuario)) {
                return i;
            }
        }
        return -1; // Não encontrado
    }

    // Método para obter uma cópia dos membros (protege o array interno)
    public UsuarioIndividual[] getMembros() {
        UsuarioIndividual[] copia = new UsuarioIndividual[quantidadeMembros];
        for (int i = 0; i < quantidadeMembros; i++) {
            copia[i] = membros[i];
        }
        return copia;
    }

    // Método para obter um membro específico por índice
    public UsuarioIndividual getMembro(int indice) {
        if (indice >= 0 && indice < quantidadeMembros) {
            return membros[indice];
        }
        return null;
    }

    // Getters e Setters
    public UsuarioIndividual getAdministrador() {
        return administrador;
    }

    public int getQuantidadeMembros() {
        return quantidadeMembros;
    }

    public int getLimiteMaximoMembros() {
        return limiteMaximoMembros;
    }

    public void setLimiteMaximoMembros(int limite) {
        if (limite > 0 && limite >= quantidadeMembros) {
            // Se o novo limite for menor que o array atual, redimensiona
            if (limite < membros.length) {
                UsuarioIndividual[] novoArray = new UsuarioIndividual[limite];
                for (int i = 0; i < quantidadeMembros; i++) {
                    novoArray[i] = membros[i];
                }
                this.membros = novoArray;
            }
            // Se o novo limite for maior, expande o array
            else if (limite > membros.length) {
                UsuarioIndividual[] novoArray = new UsuarioIndividual[limite];
                for (int i = 0; i < quantidadeMembros; i++) {
                    novoArray[i] = membros[i];
                }
                this.membros = novoArray;
            }
            this.limiteMaximoMembros = limite;
        }
    }

    // Método para verificar se o grupo está cheio
    public boolean estaLotado() {
        return quantidadeMembros >= limiteMaximoMembros;
    }

    // Método para obter membros ativos
    public UsuarioIndividual[] getMembrosAtivos() {
        // Primeiro conta quantos membros ativos existem
        int contadorAtivos = 0;
        for (int i = 0; i < quantidadeMembros; i++) {
            if (membros[i] != null && membros[i].isAtivo()) {
                contadorAtivos++;
            }
        }
        
        // Cria array com o tamanho correto e adiciona os membros ativos
        UsuarioIndividual[] membrosAtivos = new UsuarioIndividual[contadorAtivos];
        int indiceAtivos = 0;
        for (int i = 0; i < quantidadeMembros; i++) {
            if (membros[i] != null && membros[i].isAtivo()) {
                membrosAtivos[indiceAtivos] = membros[i];
                indiceAtivos++;
            }
        }
        
        return membrosAtivos;
    }

    // Override do método equals para comparação correta de grupos
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Grupo grupo = (Grupo) obj;
        return getNome().equals(grupo.getNome()) && 
               getEmail().equals(grupo.getEmail());
    }
}