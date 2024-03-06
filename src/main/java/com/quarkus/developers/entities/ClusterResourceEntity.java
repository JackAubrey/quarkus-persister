package com.quarkus.developers.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "CLUSTER_RESOURCES")
public class ClusterResourceEntity {
    @Id
    @SequenceGenerator(name = "clusterResourceSequence", sequenceName = "cr_sequence", allocationSize = 1)
    @GeneratedValue(generator = "clusterResourceSequence")
    private Long id;

    @Version
    private Long version;

    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp createdDate;

    @Column(length = 50)
    private String producer;

    @Column(name = "created")
    private Timestamp created;

    @Column(length = 50)
    @Enumerated(EnumType.STRING)
    private PayloadType payloadType;

    @Column(length = 50)
    private String payloadClass;

    @ElementCollection
    @JoinTable(name="cr_attributes",
            joinColumns = {@JoinColumn(name = "attribute_id", referencedColumnName = "id")})
    @MapKeyColumn (name="attribute_key")
    @Column(name="attribute_value")
    private Map<String, Object> attributes;
}
