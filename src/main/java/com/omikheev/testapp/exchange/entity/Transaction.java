package com.omikheev.testapp.exchange.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

/**
 * Entity for Transaction object
 *
 * @author Oleg Mikheev
 * @since 24.07.2021
 */
@Entity
@Table(name = "transaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private Double amount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_source_id")
    private Currency currencySource;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "currency_target_id")
    private Currency currencyTarget;
}
