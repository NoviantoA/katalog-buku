package novianto.anggoro.spring.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "author")
// @DynamicUpdate
// soft delete menggunakan fitur dari hibernate
@SQLDelete(sql = "UPDATE author SET deleted = true WHERE id = ?")
@Where(clause = "deleted = false")
public class Author extends AbstractBaseEntity {

    // postgre -> bigserial
    // mysql -> autoincrement
    // ini berlaku karena memakai strategy->identity
    // strategy sequence
    // cons : 1 insert -2x
    // pros : batch insert
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY
           // , generator = "author_generator"
    )
    @SequenceGenerator(name = "author_generator", sequenceName = "author_id_seq")
    private Long id;
    @Column(name = "author_name", nullable = false, columnDefinition = "varchar(300)")
    private String name;
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private List<Address> addresses;

}
