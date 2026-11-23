
package dev.java10x.itaujava10x.Controller;

import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import dev.java10x.itaujava10x.Repository.TransacaoRepository;
import dev.java10x.itaujava10x.Service.TransacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoController.class)
class TransacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransacaoService transacaoService;

    @MockitoBean
    private TransacaoRepository transacaoRepository;

    @Test
    void deveRetornar201QuandoTransacaoForValida() throws Exception {

        String json = """
                {
                    "valor": 100.00,
                    "dataHora": "2026-11-20T10:00:00-03:00"
                }
                """;

        mockMvc.perform(post("/transacao")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isCreated());

        verify(transacaoService).validarTransacao(
                org.mockito.ArgumentMatchers.any(TransacaoDTO.class)
        );

        verify(transacaoRepository).salvarDados(
                org.mockito.ArgumentMatchers.any(TransacaoDTO.class)
        );
    }
    @Test
    void deveRetornar422QuandoValorDaTransacaoForNegativo() throws Exception {
        String json = """
                {
                    "valor": -100.00,
                     "dataHora": "2026-11-20T10:00:00-03:00"
                     }
        """;

        doThrow(new IllegalArgumentException("O valor não pode ser negativo"))
                .when(transacaoService)
                .validarTransacao(any(TransacaoDTO.class));

        mockMvc.perform(post("/transacao")
                .contentType("application/json")
                .content(json))
                .andExpect(status().isUnprocessableEntity());

        verify(transacaoService).validarTransacao(
                org.mockito.ArgumentMatchers.any(TransacaoDTO.class)
        );

        verify(transacaoRepository,never()).salvarDados(
                org.mockito.ArgumentMatchers.any(TransacaoDTO.class)
        );
    }
    @Test
    void deveRetornar400QuandoJsonForMalformado() throws Exception {
        String json = """
                {
                    "valor": -100.00,
                     "dataHora": 
                     }
        """;


        mockMvc.perform(post("/transacao")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isBadRequest());

        verify(transacaoService, never()).validarTransacao(
                any(TransacaoDTO.class)
        );

        verify(transacaoRepository,never()).salvarDados(
                org.mockito.ArgumentMatchers.any(TransacaoDTO.class)
        );
    }
    @Test
    void deveRetornar200QuandoExcluirTransacoes() throws Exception {

        mockMvc.perform(delete("/transacao"))
                .andExpect(status().isOk());

        verify(transacaoRepository).excluirDados();
    }
    @Test
    void deveRetornar422QuandoValorForNulo() throws Exception {

        String json = """
            {
                "valor": null,
                "dataHora": "2026-11-20T10:00:00-03:00"
            }
            """;

        doThrow(new IllegalArgumentException("O valor deve estar presente"))
                .when(transacaoService)
                .validarTransacao(any(TransacaoDTO.class));

        mockMvc.perform(post("/transacao")
                        .contentType("application/json")
                        .content(json))
                .andExpect(status().isUnprocessableEntity());

        verify(transacaoService).validarTransacao(any(TransacaoDTO.class));

        verify(transacaoRepository, never()).salvarDados(any(TransacaoDTO.class));
    }
}
