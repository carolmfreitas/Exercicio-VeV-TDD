package functionalTests;

import com.example.processador.Conta;
import com.example.processador.Fatura;
import com.example.processador.ProcessadorDeContas;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ParticoesEquivalenciaTest {

    @Test
    public void testCaso7() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente G");
        Conta conta = new Conta("C7", LocalDate.of(2023, 2, 20), 1000.00, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso8() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 1000.00, "Cliente H");
        Conta conta = new Conta("C8", LocalDate.of(2023, 2, 20), -10.00, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testCaso9() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente I");
        Conta conta = new Conta("C9", LocalDate.of(2023, 2, 1), 800.00, "CARTAO_CREDITO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso10() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente J");
        Conta conta = new Conta("C10", LocalDate.of(2023, 2, 10), 800.00, "CARTAO_CREDITO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testCaso11() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 700.00, "Cliente K");
        Conta conta = new Conta("C11", LocalDate.of(2023, 2, 17), 700.00, "TRANSFERENCIA");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso12() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 700.00, "Cliente L");
        Conta conta = new Conta("C12", LocalDate.of(2023, 2, 20), 700.00, "TRANSFERENCIA");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }
}