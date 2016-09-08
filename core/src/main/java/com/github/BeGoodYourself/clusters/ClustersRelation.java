package com.github.BeGoodYourself.clusters;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ClustersRelation {
    private String id;
    private ConsumerClusters clusters;

    public ClustersRelation() {
    }

    public ClustersRelation(String id, ConsumerClusters clusters) {
        this.id = id;
        this.clusters = clusters;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ConsumerClusters getClusters() {
        return clusters;
    }

    public void setClusters(ConsumerClusters clusters) {
        this.clusters = clusters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClustersRelation)) return false;

        ClustersRelation that = (ClustersRelation) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(clusters, that.clusters)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(clusters)
                .toHashCode();
    }
}
