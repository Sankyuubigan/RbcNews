package nildencorp.apps.rbcnews.model;

import java.util.ArrayList;

public class RbcNewsResponseObject {
    private String status;
    private float totalResults;
    ArrayList<Article> articles;

    public ArrayList<Article> getArticles() {
        return articles;
    }

    public String getStatus() {
        return status;
    }

    public float getTotalResults() {
        return totalResults;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResults(float totalResults) {
        this.totalResults = totalResults;
    }
}
