package com.okoho.okoho.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Candidat.
 */
@Document(collection = "candidat")
@TypeAlias("candidat")
public class Candidat {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    @DBRef
    @Field("userAccount")
    private UserAccount userAccount;
    @Field("country")
    private String country;

    @Field("dob")
    private LocalDate dob;
    @DBRef
    @Field("offerJobs")
    @JsonIgnoreProperties(value = { "recruteur" }, allowSetters = true)
    private Set<OfferJob> offerJobs = new HashSet<>();

    @DBRef
    @Field("competences")
    private Set<Competence> competences = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Candidat id(String id) {
        this.id = id;
        return this;
    }

    public String getCountry() {
        return this.country;
    }

    public Candidat country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Candidat dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public Candidat userAccount(UserAccount userAccount) {
        this.setUserAccount(userAccount);
        return this;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public Set<OfferJob> getOfferJobs() {
        return this.offerJobs;
    }

    public Candidat offerJobs(Set<OfferJob> offerJobs) {
        this.setOfferJobs(offerJobs);
        return this;
    }

    public Candidat addOfferJob(OfferJob offerJob) {
        this.offerJobs.add(offerJob);
        return this;
    }

    public Candidat removeOfferJob(OfferJob offerJob) {
        this.offerJobs.remove(offerJob);
        return this;
    }

    public void setOfferJobs(Set<OfferJob> offerJobs) {
        this.offerJobs = offerJobs;
    }

    public Set<Competence> getCompetences() {
        return this.competences;
    }

    public Candidat competences(Set<Competence> competences) {
        this.setCompetences(competences);
        return this;
    }

    public Candidat addCompetences(Competence competence) {
        this.competences.add(competence);
        return this;
    }

    public Candidat removeCompetences(Competence competence) {
        this.competences.remove(competence);
        return this;
    }

    public void setCompetences(Set<Competence> competences) {
        this.competences = competences;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Candidat)) {
            return false;
        }
        return id != null && id.equals(((Candidat) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Candidat{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", dob='" + getDob() + "'" +
            "}";
    }
}
