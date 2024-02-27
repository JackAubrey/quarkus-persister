package com.quarkus.developers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ResourceQuotaInfoEntity {
    @Id
    @SequenceGenerator(name = "resQuotaSequence", sequenceName = "res_quota_sequence", allocationSize = 1)
    @GeneratedValue(generator = "resQuotaSequence")
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @UpdateTimestamp
    private Timestamp lastModifiedDate;

    @Column(length = 256)
    private String name;

    @Column(length = 256)
    private String namespace;

    @ElementCollection
    @JoinTable(name="spec_hard_key_value",
            joinColumns = {@JoinColumn(name = "spec_hard_id", referencedColumnName = "id")})
    @MapKeyColumn (name="quota_key")
    @Column(name="quota_value")
    private Map<String, BigDecimal> specHard;

    @ElementCollection
    @JoinTable(name="status_hard_key_value",
            joinColumns = {@JoinColumn(name = "status_hard_id", referencedColumnName = "id")})
    @MapKeyColumn (name="quota_key")
    @Column(name="quota_value")
    private Map<String, BigDecimal> statusHard;

    @ElementCollection
    @JoinTable(name="status_used_key_value",
            joinColumns = {@JoinColumn(name = "status_used_id", referencedColumnName = "id")})
    @MapKeyColumn (name="quota_key")
    @Column(name="quota_value")
    private Map<String, BigDecimal> statusUsed;
}
