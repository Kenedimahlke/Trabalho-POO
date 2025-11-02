public class UsuarioIndividual extends Usuario {
    private String cpf;
    private String perfil;
    private boolean ativo;

// CONSTRUTOR
    public UsuarioIndividual(String nome, String email, String cpf, String perfil, boolean ativo) {
        super(nome, email);
        this.cpf = cpf;
        this.perfil = (perfil != null && !perfil.isEmpty()) ? perfil : "BASICO";
        this.ativo = ativo;
    }

// MÉTODOS IMPLEMENTADOS
    @Override
    public boolean podeCriarLancamento() {
        return ativo && ("BASICO".equals(perfil) || "PREMIUM".equals(perfil));
    }

// GETTERS
    public String getTipoPerfil() {
        return perfil;
    }

    public String getCpf() {
        return cpf;
    }

    public String getPerfil() {
        return perfil;
    }

// SETTERS
    public void setPerfil(String perfil) {
        if (perfil != null && (perfil.equals("BASICO") || perfil.equals("PREMIUM"))) {
            this.perfil = perfil;
        }
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}