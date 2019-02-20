package nildencorp.apps.rbcnews.model;

import java.util.ArrayList;

public class Everything {
    private String status;
    private float totalResults;
    ArrayList<Article> articles;

    // Getter Methods

    public String getStatus() {
        return status;
    }

    public float getTotalResults() {
        return totalResults;
    }

    // Setter Methods

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResults(float totalResults) {
        this.totalResults = totalResults;
    }
}
