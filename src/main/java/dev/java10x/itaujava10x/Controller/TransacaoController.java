package dev.java10x.itaujava10x.Controller;

import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import dev.java10x.itaujava10x.Docs.TransacaoControllerDoc;
import dev.java10x.itaujava10x.Repository.TransacaoRepository;
import dev.java10x.itaujava10x.Service.TransacaoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/transacao")
public class TransacaoController implements TransacaoControllerDoc {

    @Autowired
    private TransacaoService transacaoService;

    @Autowired
    private TransacaoRepository transacaoRepository;

    @Override
    @PostMapping
    public ResponseEntity<Void> adicionar(
            @RequestBody TransacaoDTO transacaoDTO) {

            log.info("Adicionando transacao " + transacaoDTO.toString());

            transacaoService.validarTransacao(transacaoDTO);
            transacaoRepository.salvarDados(transacaoDTO);

            return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @Override
    @DeleteMapping
    public ResponseEntity<Void> excluir() {
        transacaoRepository.excluirDados();
        return ResponseEntity.status(HttpStatus.OK).build();

    }

}
