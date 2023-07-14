package com.okoho.okoho.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.okoho.okoho.domain.UserAccount;

/**
 * A DTO for the {@link com.okoho.okoho.domain.Recruteur} entity.
 */
public class RecruteurDTO implements Serializable {

    private String id;

    private String country;

    private LocalDate dob;

    private String entreprise;

    private String bp;
    private UserAccount userAccount;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public UserAccount getUserAccount() {
        return this.userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getBp() {
        return bp;
    }

    public void setBp(String bp) {
        this.bp = bp;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecruteurDTO)) {
            return false;
        }

        RecruteurDTO recruteurDTO = (RecruteurDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, recruteurDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecruteurDTO{" +
            "id='" + getId() + "'" +
            ", country='" + getCountry() + "'" +
            ", dob='" + getDob() + "'" +
            ", entreprise='" + getEntreprise() + "'" +
            ", bp='" + getBp() + "'" +
            ", userAccount=" +
            "}";
    }
}
