package br.com.fiap.universidade_fiap.model;

public enum EnumPerfil {

    ADMIN("Administrador"),
    GERENTE("Gerente"),
    OPERADOR("Operador");

    private final String descricao;

    private EnumPerfil(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
