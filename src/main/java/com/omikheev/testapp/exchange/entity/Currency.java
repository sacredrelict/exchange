package com.omikheev.testapp.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.GenerationType;

/**
 * Entity for Currency object
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Entity
@Table(name = "currency")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Currency {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "iso_code", unique = true)
    private String isoCode;
}
