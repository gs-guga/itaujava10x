
package dev.java10x.itaujava10x.Service;

import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TransacaoServiceTest {

    private final TransacaoService transacaoService =
            new TransacaoService();

    @Test
    void deveValidarTransacaoQuandoDadosForemValidos() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("123.45"),
                OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00")
        );

        assertDoesNotThrow(
                () -> transacaoService.validarTransacao(transacaoDTO)
        );
    }

    @Test
    void deveRejeitarTransacaoComValorNegativo() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("-10.00"),
                OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.validarTransacao(transacaoDTO)
        );
    }

    @Test
    void deveRejeitarTransacaoQuandoValorForNulo() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                null,
                OffsetDateTime.parse("2020-08-07T12:34:56.789-03:00")
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.validarTransacao(transacaoDTO)
        );
    }

    @Test
    void deveRejeitarTransacaoQuandoDataHoraForNula() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                new BigDecimal("100.00"),
                null
        );

        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.validarTransacao(transacaoDTO)
        );
    }

    @Test
    void deveRejeitarTransacaoQuandoDataHoraForFutura() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
          new BigDecimal("100.00"),
                OffsetDateTime.now().plusDays(1)
        );
        assertThrows(
                IllegalArgumentException.class,
                () -> transacaoService.validarTransacao(transacaoDTO));
    }
    @Test
    void deveAceitarTransacaoComValorZero() {

        TransacaoDTO transacaoDTO = new TransacaoDTO(
                BigDecimal.ZERO,
                OffsetDateTime.now()
        );

        assertDoesNotThrow(
                () -> transacaoService.validarTransacao(transacaoDTO)
        );
    }
}
