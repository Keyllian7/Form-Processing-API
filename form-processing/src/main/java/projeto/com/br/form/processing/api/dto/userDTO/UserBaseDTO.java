package projeto.com.br.form.processing.api.dto.userDTO;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Getter
@Setter
public class UserBaseDTO {

    @NotNull
    private UUID id;

}
