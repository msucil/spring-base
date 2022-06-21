package com.msucil.dev.springbase.domain.manage.privilege;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {

    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(updatable = false)
    private SystemPrivilege name;

    private Instant createdAt = Instant.now();

    public String getLabel() {
        return this.name.getLabel();
    }
}
