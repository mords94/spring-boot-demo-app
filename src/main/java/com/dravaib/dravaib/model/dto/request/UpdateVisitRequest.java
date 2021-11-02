package com.dravaib.dravaib.model.dto.request;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

public class UpdateVisitRequest implements Serializable {

    @NotNull
    @PastOrPresent
    private Date finishDate;

    public UpdateVisitRequest(Date finishDate) {
        this.finishDate = finishDate;
    }

    public UpdateVisitRequest() {
    }

    public Date getFinishDate() {
        return this.finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

}
