
package dev.java10x.itaujava10x.Repository;

import dev.java10x.itaujava10x.DTO.EstatisticaDTO;
import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransacaoRepositoryTest {

    private final TransacaoRepository transacaoRepository =
            new TransacaoRepository();

    @Test
    void deveSalvarTransacaoECacularEstatisticas() {

        // Arrange: preparar os dados
        OffsetDateTime agora = OffsetDateTime.now();

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("100.00"),
                agora
        );

        // Act: executar o comportamento
        transacaoRepository.salvarDados(transacaoDTO);

        long segundos = 60;

        EstatisticaDTO estatistica = transacaoRepository.estatistica(
                agora.minusSeconds(segundos)
        );

        // Assert: verificar o resultado
        assertEquals(1L, estatistica.getCount());
        assertEquals(100.0, estatistica.getSum());
        assertEquals(100.0, estatistica.getAvg());
        assertEquals(100.0, estatistica.getMin());
        assertEquals(100.0, estatistica.getMax());
    }

    @Test
    void deveExcluirTodasAsTransacoes() {
        OffsetDateTime agora = OffsetDateTime.now();

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("100.00"),
                agora
        );

        // Arrange: salva uma transação
        transacaoRepository.salvarDados(transacaoDTO);

        // Act: exclui todas as transações
        transacaoRepository.excluirDados();

        // Consulta as estatísticas depois da exclusão
        long segundos = 60;
        EstatisticaDTO estatistica = transacaoRepository.estatistica(
                agora.minusSeconds(segundos)
        );

        // Assert: verifica se tudo voltou para zero
        assertEquals(0L, estatistica.getCount());
        assertEquals(0.0, estatistica.getSum());
        assertEquals(0.0, estatistica.getAvg());
        assertEquals(0.0, estatistica.getMin());
        assertEquals(0.0, estatistica.getMax());
    }
    @Test
    void deveCalcularEstatisticasComVariasTransacoes() {
        OffsetDateTime agora = OffsetDateTime.now();

        TransacaoDTO transacao1 = new TransacaoDTO(
                new BigDecimal("10.00"),
                agora
        );

        TransacaoDTO transacao2 = new TransacaoDTO(
                new BigDecimal("20.00"),
                agora
        );

        TransacaoDTO transacao3 = new TransacaoDTO(
                new BigDecimal("30.00"),
                agora
        );

        transacaoRepository.salvarDados(transacao1);
        transacaoRepository.salvarDados(transacao2);
        transacaoRepository.salvarDados(transacao3);

        EstatisticaDTO estatistica = transacaoRepository.estatistica(
                agora.minusSeconds(60)
        );

        assertEquals(3L, estatistica.getCount());
        assertEquals(60.0, estatistica.getSum());
        assertEquals(20.0, estatistica.getAvg());
        assertEquals(10.0, estatistica.getMin());
        assertEquals(30.0, estatistica.getMax());
    }
    @Test
    void deveConsiderarApenasTransacoesDentroDoIntervalo() {
        OffsetDateTime agora = OffsetDateTime.now();

        TransacaoDTO transacaoAntiga = new TransacaoDTO(
                new BigDecimal("500.00"),
                agora.minusSeconds(120)
        );

        TransacaoDTO transacaoRecente = new TransacaoDTO(
                new BigDecimal("100.00"),
                agora.minusSeconds(10)
        );

        transacaoRepository.salvarDados(transacaoAntiga);
        transacaoRepository.salvarDados(transacaoRecente);

        EstatisticaDTO estatistica = transacaoRepository.estatistica(
                agora.minusSeconds(60)
        );

        assertEquals(1L, estatistica.getCount());
        assertEquals(100.0, estatistica.getSum());
        assertEquals(100.0, estatistica.getAvg());
        assertEquals(100.0, estatistica.getMin());
        assertEquals(100.0, estatistica.getMax());
    }
    @Test
    void deveConsiderarTransacaoExatamenteNoLimiteDoIntervalo() {
        OffsetDateTime agora = OffsetDateTime.now();

        OffsetDateTime limite = agora.minusSeconds(60);

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("150.00"),
                limite
        );

        // Arrange: salva uma transação exatamente no limite
        transacaoRepository.salvarDados(transacaoDTO);

        // Act: consulta as estatísticas usando o mesmo limite
        EstatisticaDTO estatistica = transacaoRepository.estatistica(limite);

        // Assert: verifica se a transação foi considerada
        assertEquals(1L, estatistica.getCount());
        assertEquals(150.0, estatistica.getSum());
        assertEquals(150.0, estatistica.getAvg());
        assertEquals(150.0, estatistica.getMin());
        assertEquals(150.0, estatistica.getMax());
    }

}

