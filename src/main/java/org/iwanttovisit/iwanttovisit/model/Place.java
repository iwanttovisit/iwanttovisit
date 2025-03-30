package org.iwanttovisit.iwanttovisit.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "places")
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Place extends BaseEntity {

    private String name;
    private String description;
    private String coordinates;
    private String url;
    private float rating;

    @Enumerated(value = EnumType.STRING)
    private Category category;

    private boolean isVisited;

    @ManyToOne(fetch = FetchType.LAZY)
    private Map map;

    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

}
