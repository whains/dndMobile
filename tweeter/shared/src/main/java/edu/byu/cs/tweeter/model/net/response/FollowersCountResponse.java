package edu.byu.cs.tweeter.model.net.response;

import java.util.Objects;

public class FollowersCountResponse extends Response {

    int count;

    public FollowersCountResponse(String message){
        super(false, message);
    }

    public FollowersCountResponse(int count) {
        super(true, null);
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FollowersCountResponse that = (FollowersCountResponse) o;
        return count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(count);
    }
}
