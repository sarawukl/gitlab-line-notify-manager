package com.sarawukl.gitlablinemgr.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Table(name = "Notify")
@NoArgsConstructor
public class Notify {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @NotEmpty
    private String notifyType;

    @NotNull
    @NotEmpty
    private String lineToken;
}
