package pt.ubi;

import android.content.Intent;

import java.io.Serializable;

public class ItemHistoric implements Serializable {

    private Integer idh ;
    private Integer id;
    private Integer cnt;
    private String datetime;

    public Integer getIdh() {
        return idh;
    }

    public void setIdh(Integer igh) {
        this.idh = igh;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
