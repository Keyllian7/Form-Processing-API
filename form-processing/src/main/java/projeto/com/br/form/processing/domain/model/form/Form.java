package projeto.com.br.form.processing.domain.model.form;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import projeto.com.br.form.processing.api.dto.form.FormRequestDTO;
import projeto.com.br.form.processing.domain.model.user.User;
import java.util.Date;

@Table
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Form {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String motivo;
    private String setor;
    private String mensagem;
    private Date dataCriacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User emissor;

    public Form(FormRequestDTO data, User emissor) {
        this.motivo = data.motivo();
        this.setor = data.setor();
        this.mensagem = data.mensagem();
        this.dataCriacao = new Date();
        this.emissor = emissor;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
