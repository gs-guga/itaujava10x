
package dev.java10x.itaujava10x.Controller;

import dev.java10x.itaujava10x.Config.EstatisticaConfig;
import dev.java10x.itaujava10x.DTO.EstatisticaDTO;
import dev.java10x.itaujava10x.Repository.TransacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.OffsetDateTime;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstatisticasController.class)
class EstatisticasControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EstatisticaConfig estatisticaConfig;

    @MockitoBean
    private TransacaoRepository transacaoRepository;

    @Test
    void deveRetornar200AoConsultarEstatisticas() throws Exception {

        when(estatisticaConfig.segundos()).thenReturn(60);

        when(transacaoRepository.estatistica(
                org.mockito.ArgumentMatchers.any(OffsetDateTime.class)
        )).thenReturn(new EstatisticaDTO(0L, 0.0, 0.0, 0.0, 0.0));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk());
    }
    @Test
    void deveUtilizarIntervaloConfiguradoParaBuscarEstatisticas() throws Exception {

        when(estatisticaConfig.segundos()).thenReturn(60);

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk());

        verify(transacaoRepository).estatistica(
                argThat(hora ->
                        !hora.isAfter(OffsetDateTime.now().minusSeconds(60).plusSeconds(1))
                                && !hora.isBefore(OffsetDateTime.now().minusSeconds(60).minusSeconds(1))
                )
        );
    }
    @Test
    void deveRetornarEstatisticasNoFormatoCorreto() throws Exception {

        when(estatisticaConfig.segundos()).thenReturn(60);

        when(transacaoRepository.estatistica(
                org.mockito.ArgumentMatchers.any(OffsetDateTime.class)
        )).thenReturn(new EstatisticaDTO(
                2L,
                300.0,
                150.0,
                100.0,
                200.0
        ));

        mockMvc.perform(get("/estatistica"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(2))
                .andExpect(jsonPath("$.sum").value(300.0))
                .andExpect(jsonPath("$.avg").value(150.0))
                .andExpect(jsonPath("$.min").value(100.0))
                .andExpect(jsonPath("$.max").value(200.0));
    }
    @Test
    void deveRetornarEstatisticasZeradasQuandoNaoExistemTransacoes() throws Exception {

        when(estatisticaConfig.segundos()).thenReturn(60);

        when(transacaoRepository.estatistica(
                org.mockito.ArgumentMatchers.any(OffsetDateTime.class)
        )).thenReturn(new EstatisticaDTO(
                0L,
                0.0,
                0.0,
                0.0,
                0.0
        ));

        mockMvc.perform(get("/estatistica")).andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(0))
                .andExpect(jsonPath("$.sum").value(0.0))
                .andExpect(jsonPath("$.avg").value(0.0))
                .andExpect(jsonPath("$.min").value(0.0))
                .andExpect(jsonPath("$.max").value(0.0));

    }
}
