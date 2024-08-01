package com.example.processador;

public enum TipoPagamento {
    BOLETO("BOLETO"),
    CARTAO_CREDITO("CARTAO_CREDITO"),
    TRANSFERENCIA_BANCARIA("TRANSFERENCIA");

    private final String descricao;

    TipoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
