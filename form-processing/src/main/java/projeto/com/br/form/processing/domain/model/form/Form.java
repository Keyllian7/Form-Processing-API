package projeto.com.br.form.processing.domain.model.form;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.Where;
import projeto.com.br.form.processing.domain.enums.Status;
import projeto.com.br.form.processing.domain.model.user.User;

import java.time.OffsetDateTime;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "forms")
public class Form {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String motivo;
    private String setor;
    private String problema;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private String mensagem;

    @CreationTimestamp
    private OffsetDateTime dataCriacao;

    @UpdateTimestamp
    private OffsetDateTime dataAtualizacao;

    @Where(clause = "dataExclusao IS NULL")
    private OffsetDateTime dataExclusao;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
