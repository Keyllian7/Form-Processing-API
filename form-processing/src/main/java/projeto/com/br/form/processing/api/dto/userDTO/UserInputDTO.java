package projeto.com.br.form.processing.api.dto.userDTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInputDTO {

    private static final int TAMANHO_MINIMO_NOME = 4;
    private static final int TAMANHO_MAXIMO_NOME = 40;
    private static final int TAMANHO_MINIMO_SENHA = 6;
    private static final int TAMANHO_MAXIMO_SENHA = 100;

    @NotBlank
    @Size(min = TAMANHO_MINIMO_NOME, max = TAMANHO_MAXIMO_NOME)
    private String nome;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = TAMANHO_MINIMO_SENHA, max = TAMANHO_MAXIMO_SENHA)
    private String senha;

}
