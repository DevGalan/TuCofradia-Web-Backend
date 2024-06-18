package com.devgalan.tucofradia.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SERVERS")
public class Server {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String code;

    private String description;

    private String password;

    @Column(nullable = false)
    private Byte gameMonth;

    @Column(nullable = false)
    private Byte maxPlayers;

    @Column(nullable = false)
    private Byte amountPlayers;

    @ManyToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private User admin;

}
