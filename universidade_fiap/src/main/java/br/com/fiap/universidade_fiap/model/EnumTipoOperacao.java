package br.com.fiap.universidade_fiap.model;

public enum EnumTipoOperacao {

    ENTREGA("Entrega"),
    COLETA("Coleta"),
    MANUTENCAO("Manutenção"),
    TRANSFERENCIA("Transferência"),
    CHECK_IN("Check In"),
    CHECK_OUT("Check Out");

    private final String descricao;

    private EnumTipoOperacao(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
