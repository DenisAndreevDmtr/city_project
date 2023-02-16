package com.andersen.cities.entity;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@RequiredArgsConstructor
@SuperBuilder
@Entity
@Table(name = "cities")
public class City extends BaseEntity {
    @CsvBindByName(column = "name")
    @Column(name = "name")
    @NotNull
    private String name;
    @CsvBindByName(column = "photo")
    @Column(name = "photo")
    @NotNull
    private String photo;
}