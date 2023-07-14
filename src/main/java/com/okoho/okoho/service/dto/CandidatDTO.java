package com.okoho.okoho.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.okoho.okoho.domain.Candidat} entity.
 */
public class CandidatDTO implements Serializable {

    private String id;

    private Boolean isvalid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsvalid() {
        return isvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        this.isvalid = isvalid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CandidatDTO)) {
            return false;
        }

        CandidatDTO candidatDTO = (CandidatDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, candidatDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CandidatDTO{" +
            "id='" + getId() + "'" +
            ", isvalid='" + getIsvalid() + "'" +
            "}";
    }
}
