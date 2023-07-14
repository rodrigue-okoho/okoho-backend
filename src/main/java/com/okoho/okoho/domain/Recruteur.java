package com.okoho.okoho.domain;


import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Recruteur.
 */
@Document(collection = "recruteur")
@TypeAlias("recruteur")
public class Recruteur {

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

    @Field("entreprise")
    private String entreprise;

    @Field("bp")
    private String bp;


   public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Recruteur id(String id) {
        this.id = id;
        return this;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public Recruteur userAccount(UserAccount userAccount) {
        this.setUserAccount(userAccount);
        return this;
    }
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }
    public String getCountry() {
        return this.country;
    }

    public Recruteur country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDob() {
        return this.dob;
    }

    public Recruteur dob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEntreprise() {
        return this.entreprise;
    }

    public Recruteur entreprise(String entreprise) {
        this.entreprise = entreprise;
        return this;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getBp() {
        return this.bp;
    }

    public Recruteur bp(String bp) {
        this.bp = bp;
        return this;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recruteur)) {
            return false;
        }
        return id != null && id.equals(((Recruteur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recruteur{" +
            "id=" + getId() +
            ", country='" + getCountry() + "'" +
            ", dob='" + getDob() + "'" +
            ", entreprise='" + getEntreprise() + "'" +
            ", bp='" + getBp() + "'" +
            "}";
    }
}
