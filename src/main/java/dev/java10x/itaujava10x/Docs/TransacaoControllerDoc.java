
package dev.java10x.itaujava10x.Docs;

import dev.java10x.itaujava10x.DTO.TransacaoDTO;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@Tag(
        name = "Transações",
        description = "Endpoints de gerenciamento de transações"
)
public interface TransacaoControllerDoc {

    @Operation(
            summary = "Adicionar transação",
            description = "Valida e registra uma nova transação."
    )
    @RequestBody(
            description = "Dados da transação",
            required = true,
            content = @Content(
                    schema = @Schema(implementation = TransacaoDTO.class)
            )
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Transação criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido"),
            @ApiResponse(responseCode = "422", description = "Erro de validação")
    })
    ResponseEntity<Void> adicionar(TransacaoDTO transacaoDTO);

    @Operation(
            summary = "Excluir transações",
            description = "Exclui todas as transações armazenadas."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transações excluídas com sucesso")
    })
    ResponseEntity<Void> excluir();
}

