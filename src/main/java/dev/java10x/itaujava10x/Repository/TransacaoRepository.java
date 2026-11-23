package dev.java10x.itaujava10x.Repository;

import dev.java10x.itaujava10x.DTO.EstatisticaDTO;
import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransacaoRepository {

    private final List<TransacaoDTO> transacoes = new ArrayList<>();


    public void salvarDados(TransacaoDTO transacaoDTO) {
        transacoes.add(transacaoDTO);
    }

    public void excluirDados() {
        transacoes.clear();
    }


    public EstatisticaDTO estatistica(OffsetDateTime horaAtual) {

        final var summary = transacoes.stream()
                .filter(t ->
                        !t.getDataHora().isBefore(horaAtual))
                .mapToDouble(t -> t.getValor().doubleValue())
                .summaryStatistics();

        if (summary.getCount() == 0) {
            return new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        return new EstatisticaDTO(
                summary.getCount(),
                summary.getSum(),
                summary.getAverage(),
                summary.getMin(),
                summary.getMax()
        );
    }
}
