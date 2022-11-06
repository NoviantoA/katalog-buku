package novianto.anggoro.spring.catalog.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "publisher")
public class Publisher extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "publisher_generator", sequenceName = "publisher_id_seq")
    private Long id;
    @Column(name = "name", nullable = false)
    private  String name;
    @Column(name = "company_name", nullable = false)
    private String companyName;
    @Column(name = "address")
    private String address;
    @OneToMany(mappedBy = "publisher")
    private List<Book> books;

}
