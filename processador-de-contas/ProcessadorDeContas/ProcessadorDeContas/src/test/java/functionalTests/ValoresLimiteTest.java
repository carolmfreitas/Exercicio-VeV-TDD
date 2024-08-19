package functionalTests;

import com.example.processador.Conta;
import com.example.processador.Fatura;
import com.example.processador.ProcessadorDeContas;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

public class ValoresLimiteTest{
	@Test
    public void testCaso1() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 0.01, "Cliente A");
        Conta conta = new Conta("C1", LocalDate.of(2023, 2, 20), 0.01, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso2() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 5000.00, "Cliente B");
        Conta conta = new Conta("C2", LocalDate.of(2023, 2, 20), 5000.00, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso3() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 0.01, "Cliente C");
        Conta conta = new Conta("C3", LocalDate.of(2023, 2, 20), 0.00, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testCaso4() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 5000.00, "Cliente D");
        Conta conta = new Conta("C4", LocalDate.of(2023, 2, 20), 5000.01, "BOLETO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PENDENTE", resultado);
    }

    @Test
    public void testCaso5() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente E");
        Conta conta = new Conta("C5", LocalDate.of(2023, 2, 5), 800.00, "CARTAO_CREDITO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PAGA", resultado);
    }

    @Test
    public void testCaso6() {
        Fatura fatura = new Fatura(LocalDate.of(2023, 2, 20), 800.00, "Cliente F");
        Conta conta = new Conta("C6", LocalDate.of(2023, 2, 6), 800.00, "CARTAO_CREDITO");
        ProcessadorDeContas processador = new ProcessadorDeContas();
        String resultado = processador.processarFatura(fatura, Arrays.asList(conta));
        assertEquals("PENDENTE", resultado);
    }
}