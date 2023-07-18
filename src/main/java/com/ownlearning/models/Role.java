package com.ownlearning.models;

import jakarta.persistence.Table;

@Table(name = "roles")
public enum Role {
    ADMIN,
    USER
}
