package com.example.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "parameters")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Parameter {
    @Id
    @Column(name = "parameter_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "data_type")
    private String dataType;
    @ManyToOne
    @JoinColumn(name = "pattern_id")
    private OfferPattern offerPattern;
}
