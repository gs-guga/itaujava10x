package dev.java10x.itaujava10x.Controller;


import dev.java10x.itaujava10x.Config.EstatisticaConfig;
import dev.java10x.itaujava10x.DTO.EstatisticaDTO;
import dev.java10x.itaujava10x.Docs.EstatisticasControllerDoc;
import dev.java10x.itaujava10x.Repository.TransacaoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.OffsetDateTime;


@Slf4j
@RestController
@RequestMapping("/estatistica")
public class EstatisticasController implements EstatisticasControllerDoc {

    private final EstatisticaConfig estatisticaConfig;

    private final TransacaoRepository transacaoRepository;


    public EstatisticasController(EstatisticaConfig estatisticaConfig, TransacaoRepository transacaoRepository) {
        this.estatisticaConfig = estatisticaConfig;
        this.transacaoRepository = transacaoRepository;
    }

    @Override
    @GetMapping
    public ResponseEntity<EstatisticaDTO> estatisticas() {

        log.info("Calculando estatisticas a " + this.estatisticaConfig.toString() + " segundos ");

        final var horaAtual = OffsetDateTime.now()
                .minusSeconds(estatisticaConfig.segundos());

        return   ResponseEntity.ok(
                transacaoRepository.estatistica(horaAtual));
    }
}