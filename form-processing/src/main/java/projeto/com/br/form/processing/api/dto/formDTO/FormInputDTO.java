package projeto.com.br.form.processing.api.dto.formDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import projeto.com.br.form.processing.domain.model.form.Status;

@Getter
@Setter
public class FormInputDTO {

    private static final int TAMANHO_MINIMO_MOTIVO = 5;
    private static final int TAMANHO_MAXIMO_MOTIVO = 100;
    private static final int TAMANHO_MINIMO_SETOR = 5;
    private static final int TAMANHO_MAXIMO_SETOR = 50;
    private static final int TAMANHO_MINIMO_PROBLEMA = 5;
    private static final int TAMANHO_MAXIMO_PROBLEMA = 100;

    @NotBlank
    @Size(min = TAMANHO_MINIMO_MOTIVO, max = TAMANHO_MAXIMO_MOTIVO)
    private String motivo;

    @NotBlank
    @Size(min = TAMANHO_MINIMO_SETOR, max = TAMANHO_MAXIMO_SETOR)
    private String setor;

    @NotBlank
    @Size(min = TAMANHO_MINIMO_PROBLEMA, max = TAMANHO_MAXIMO_PROBLEMA)
    private String problema;

    private Status status;
    private String mensagem;

}
