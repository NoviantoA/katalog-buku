package novianto.anggoro.spring.catalog.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "address")
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "street_name", nullable = false)
    private String streetName;
    @Column(name = "city_name", nullable = false)
    private String cityName;
    @Column(name = "zipcode", nullable = false)
    private String zipCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;
}
