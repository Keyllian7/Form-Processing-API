package projeto.com.br.form.processing.domain.model.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FormRequestDTO (
        @NotNull @NotBlank
        String motivo,

        @NotNull @NotBlank
        String setor,

        String mensagem

){
}
