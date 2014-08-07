package boodater.model;

import java.util.List;

public class DataTableResponse<T> {
    private List<T> data;

    public DataTableResponse(List<T> data) {
        this.data = data;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
