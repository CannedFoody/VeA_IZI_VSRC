package eu.virac.vea_izi_vsrc.config;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "REVINFO")
@Getter
@Setter
@RevisionEntity(CustomRevisionListener.class)
public class CustomRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @RevisionNumber
    private int id;

    @RevisionTimestamp
    @Column(name = "revision_date")
    private long revisionDate;

    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "revision", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<RevisionChange> changes = new HashSet<>();
}