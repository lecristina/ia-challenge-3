package br.com.fiap.universidade_fiap.model;

public enum EnumStatus {

	DISPONIVEL("Disponível", "bg-success"),
    EM_USO("Em Uso", "bg-primary"),
    MANUTENCAO("Manutenção", "bg-warning"),
    INDISPONIVEL("Indisponível", "bg-danger"),
    PENDENTE("Pendente", "bg-warning"),
    REPARO_SIMPLES("Reparo Simples", "bg-info"),
    DANOS_ESTRUTURAIS("Danos Estruturais", "bg-danger"),
    MOTOR_DEFEITUOSO("Motor Defeituoso", "bg-danger"),
    MANUTENCAO_AGENDADA("Manutenção Agendada", "bg-primary"),
    PRONTA("Pronta", "bg-success"),
    SEM_PLACA("Sem Placa", "bg-secondary"),
    ALUGADA("Alugada", "bg-success"),
    AGUARDANDO_ALUGUEL("Aguardando Aluguel", "bg-warning");

    private final String descricao;
    private final String cssClass;

    private EnumStatus(String descricao, String cssClass) {
        this.descricao = descricao;
        this.cssClass = cssClass;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCssClass() {
        return cssClass;
    }
}
