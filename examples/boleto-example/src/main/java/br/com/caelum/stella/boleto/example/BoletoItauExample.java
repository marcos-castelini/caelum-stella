package br.com.caelum.stella.boleto.example;

import br.com.caelum.stella.boleto.*;
import br.com.caelum.stella.boleto.bancos.Itau;
import br.com.caelum.stella.boleto.transformer.GeradorDeBoleto;

public class BoletoItauExample {
    public static void main(String[] args) {
        Datas datas = Datas.novasDatas().comDocumento(1, 5, 2008)
                .comProcessamento(1, 5, 2008).comVencimento(2, 5, 2008);

        Pagador pagador = Pagador.novoPagador()
                .comNome("Fulano de tal")
                .comDocumento("111.222.333-12")
                .comEndereco(
                        Endereco.novoEndereco()
                                .comCep("12345-000")
                                .comCidade("São José do Rio Preto")
                                .comLogradouro("Rua teste")
                                .comBairro("Centro")
                );

        Beneficiario beneficiario = Beneficiario.novoBeneficiario()
                .comNomeBeneficiario("Fulano de tal")
                .comAgencia("1234")
                .comDigitoAgencia("0")
                .comCodigoBeneficiario("5000")
                .comDigitoCodigoBeneficiario("2")
                .comCarteira("109")
                .comNossoNumero("200")
                .comDigitoNossoNumero("1")
                .comDocumento("1123")
                .comNumeroConvenio("102112")
                .comModalidade(Modalidade.SEM_REGISTRO)
                .comEndereco(
                        Endereco.novoEndereco()
                                .comCep("12345-000")
                                .comCidade("São José do Rio Preto")
                                .comLogradouro("Rua teste")
                                .comBairro("Centro")
                );

        Banco banco = new Itau();

        Boleto boleto = Boleto.novoBoleto()
                .comBanco(banco)
                .comDatas(datas)
                .comDescricoes("descricao 1", "descricao 2", "descricao 3",
                        "descricao 4", "descricao 5")
                .comPagador(pagador)
                .comBeneficiario(beneficiario)
                .comValorBoleto("200.00")
                .comNumeroDoDocumento("1234")
                .comInstrucoes("instrucao 1", "instrucao 2",
                        "instrucao 3", "instrucao 4", "instrucao 5")
                .comLocaisDePagamento("local 1", "local 2")
                .comNumeroDoDocumento("4343");

        GeradorDeBoleto gerador = new GeradorDeBoleto(boleto);

        // Para gerar um boleto em PDF
        gerador.geraPDF("BoletoItau.pdf");

        // Para gerar um boleto em PNG
        gerador.geraPNG("BoletoItau.png");

        // Para gerar um array de bytes a partir de um PDF
        @SuppressWarnings("unused")
        byte[] bPDF = gerador.geraPDF();

        // Para gerar um array de bytes a partir de um PNG
        @SuppressWarnings("unused")
        byte[] bPNG = gerador.geraPNG();
    }
}
