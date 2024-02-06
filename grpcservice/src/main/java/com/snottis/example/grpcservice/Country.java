package com.snottis.example.grpcservice;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.Data;

@Data
@Table(name = "country")
public class Country {
    @Id
    private Long id;
    @Column("country_name")
    private String name;
    private Float gdp;
}
