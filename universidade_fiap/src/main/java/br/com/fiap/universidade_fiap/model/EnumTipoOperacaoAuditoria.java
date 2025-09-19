package br.com.fiap.universidade_fiap.model;

public enum EnumTipoOperacaoAuditoria {

    INSERT("Inserção"),
    UPDATE("Atualização"),
    DELETE("Exclusão");

    private final String descricao;

    private EnumTipoOperacaoAuditoria(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
