package com.exemplo.usuario.dto.response;

// DTO de saida da API para usuario.
// Repare que ele nao expõe a senha.
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private String plano;
    private Integer creditosCursos;
    private Integer cursosConcluidosComSucesso;
    private Integer moedas;
    private Boolean assinaturaAtiva;

    public UsuarioResponseDTO() {
    }

    public UsuarioResponseDTO(Long id, String nome, String email, String plano, Integer creditosCursos,
                              Integer cursosConcluidosComSucesso, Integer moedas, Boolean assinaturaAtiva) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.plano = plano;
        this.creditosCursos = creditosCursos;
        this.cursosConcluidosComSucesso = cursosConcluidosComSucesso;
        this.moedas = moedas;
        this.assinaturaAtiva = assinaturaAtiva;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getPlano() {
        return plano;
    }

    public Integer getCreditosCursos() {
        return creditosCursos;
    }

    public Integer getCursosConcluidosComSucesso() {
        return cursosConcluidosComSucesso;
    }

    public Integer getMoedas() {
        return moedas;
    }

    public Boolean getAssinaturaAtiva() {
        return assinaturaAtiva;
    }
}
