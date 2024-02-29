package com.example.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "patterns")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OfferPattern {
    @Id
    @Column(name = "pattern_id")
    private Long id;
    @Column(name = "directory")
    private String directory;
    @Column(name = "name")
    private String name;
}
