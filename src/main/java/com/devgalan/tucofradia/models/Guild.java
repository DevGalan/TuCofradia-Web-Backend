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
@Table(name = "GUILDS")
public class Guild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, length = 1000)
    private String description;

    @Column(nullable = false)
    private Integer reputation;

    @Column(nullable = false)
    private Long money;

    @Column(nullable = false)
    private Integer brothers;

    @Column(nullable = false)
    private Byte fee;

    @ManyToOne
    @JoinColumn(name = "server_id", nullable = false)
    private Server server;

}
