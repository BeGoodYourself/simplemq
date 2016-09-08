package com.github.BeGoodYourself.clusters;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by Administrator on 2016/9/8.
 */
public class ClustersState {
    public static final int ERROR = 1;
    public static final int SUCCESS = 0;
    public static final int NETWORKERR = -1;
    private String clusters;
    private int state;

    public ClustersState(String clusters, int state) {
        this.clusters = clusters;
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getClusters() {
        return clusters;
    }

    public void setClusters(String clusters) {
        this.clusters = clusters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof ClustersState)) return false;

        ClustersState that = (ClustersState) o;

        return new EqualsBuilder()
                .append(state, that.state)
                .append(clusters, that.clusters)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(clusters)
                .append(state)
                .toHashCode();
    }
}
