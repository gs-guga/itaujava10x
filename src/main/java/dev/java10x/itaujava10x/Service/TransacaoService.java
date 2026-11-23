package dev.java10x.itaujava10x.Service;


import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.OffsetDateTime;


@Service
public class TransacaoService {


    public void validarTransacao(TransacaoDTO transacaoDTO) {
        if (transacaoDTO.getValor() == null) {
            throw new IllegalArgumentException("O valor deve estar presente");
        }
        if (transacaoDTO.getDataHora() == null) {
            throw new IllegalArgumentException("A data e hora devem estar presentes");
        }
        if (transacaoDTO.getValor().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("O valor não pode ser negativo");
        }
        if (transacaoDTO.getDataHora().isAfter(OffsetDateTime.now())) {
            throw new IllegalArgumentException("A data e hora não podem ser no futuro");
        }
    }

}