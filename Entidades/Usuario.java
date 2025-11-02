public abstract class Usuario {
    private String nome;
    private String email;

// CONSTRUTOR
    protected Usuario(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

// GETTERS
    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

// SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

// MÉTODOS ABSTRATOS
    public abstract boolean podeCriarLancamento();

    public abstract String getTipoPerfil();
}
