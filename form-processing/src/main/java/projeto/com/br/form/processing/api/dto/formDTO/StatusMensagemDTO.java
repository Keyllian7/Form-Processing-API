package projeto.com.br.form.processing.api.dto.formDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import projeto.com.br.form.processing.domain.enums.Status;

@Getter
@Setter
public class StatusMensagemDTO {

    @NotNull
    private Status status;

    @NotNull
    private String mensagem;
}