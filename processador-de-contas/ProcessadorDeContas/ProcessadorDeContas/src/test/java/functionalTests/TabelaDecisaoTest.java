package functionalTests;

import com.example.processador.Conta;
import com.example.processador.Fatura;
import com.example.processador.ProcessadorDeContas;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TabelaDecisaoTest {

    @Test
    public void testeRegra1() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente 1");
        List<Conta> contas = Arrays.asList(
            new Conta("C13", LocalDate.of(2023, 2, 20), 1000.00, "BOLETO")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testeRegra2() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente 2");
        List<Conta> contas = Arrays.asList(
            new Conta("C14", LocalDate.of(2023, 2, 18), 1000.00, "BOLETO")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testeRegra3() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente 3");
        List<Conta> contas = Arrays.asList(
            new Conta("C15", LocalDate.of(2023, 2, 20), 5000.01, "BOLETO")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testeRegra4() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente 4");
        List<Conta> contas = Arrays.asList(
            new Conta("C16", LocalDate.of(2023, 2, 1), 800.00, "CARTAO_CREDITO")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testeRegra5() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente 5");
        List<Conta> contas = Arrays.asList(
            new Conta("C17", LocalDate.of(2023, 2, 10), 800.00, "CARTAO_CREDITO")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testeRegra6() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 700.00, "Cliente 6");
        List<Conta> contas = Arrays.asList(
            new Conta("C18", LocalDate.of(2023, 2, 17), 700.00, "TRANSFERENCIA")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testeRegra7() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 700.00, "Cliente 7");
        List<Conta> contas = Arrays.asList(
            new Conta("C19", LocalDate.of(2023, 2, 21), 700.00, "TRANSFERENCIA")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testeRegra8() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente 8");
        List<Conta> contas = Arrays.asList(
            new Conta("C20", LocalDate.of(2023, 2, 20), 500.00, "BOLETO"),
            new Conta("C21", LocalDate.of(2023, 2, 20), 500.00, "TRANSFERENCIA")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testeRegra9() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente 9");
        List<Conta> contas = Arrays.asList(
            new Conta("C21", LocalDate.of(2023, 2, 21), 100.00, "BOLETO"),
            new Conta("C22", LocalDate.of(2023, 2, 21), 100.00, "CARTAO_CREDITO"),
            new Conta("C23", LocalDate.of(2023, 2, 21), 100.00, "TRANSFERENCIA")
        );

        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, contas);
        assertEquals("PENDENTE", resultado);
    }
}
