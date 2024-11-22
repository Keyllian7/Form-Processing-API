package projeto.com.br.form.processing.api.dto.userDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserOutDTO extends UserBaseDTO{

    private String nome;
    private String email;
    private String senha;
}
