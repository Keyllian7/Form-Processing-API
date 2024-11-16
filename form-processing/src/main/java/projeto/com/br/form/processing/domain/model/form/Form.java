package projeto.com.br.form.processing.domain.model.form;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Table
@Entity(name = "form")
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

    public Form(FormRequestDTO data) {
        this.motivo = data.motivo();
        this.setor = data.setor();
        this.mensagem = data.mensagem();
        this.dataCriacao = new Date();
    }

}
