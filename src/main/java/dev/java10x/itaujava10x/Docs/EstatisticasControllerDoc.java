
package dev.java10x.itaujava10x.Docs;

import dev.java10x.itaujava10x.DTO.EstatisticaDTO;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@Tag(
        name = "Estatísticas",
        description = "Endpoint para consulta de estatísticas das transações"
)
public interface EstatisticasControllerDoc {

    @Operation(
            summary = "Consultar estatísticas",
            description = "Retorna as estatísticas das transações realizadas dentro do intervalo de tempo configurado."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Estatísticas calculadas com sucesso",
                    content = @Content(
                            schema = @Schema(implementation = EstatisticaDTO.class)
                    )
            )
    })
    ResponseEntity<EstatisticaDTO> estatisticas();
}

