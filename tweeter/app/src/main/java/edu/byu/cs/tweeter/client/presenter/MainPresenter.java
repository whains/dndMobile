package edu.byu.cs.tweeter.client.presenter;

import android.util.Log;

import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.model.service.FollowService;
import edu.byu.cs.tweeter.client.model.service.StatusService;
import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

public class MainPresenter extends Presenter<MainPresenter.MainView> implements FollowService.UnfollowObserver, FollowService.GetFollowersCountObserver,
        FollowService.GetFollowingCountObserver, FollowService.FollowObserver, UserService.LogoutObserver,
        FollowService.IsFollowerObserver, StatusService.PostStatusObserver{

    public interface MainView extends Presenter.View {
        abstract void updateFollowerCount(int count);
        abstract void updateFollowingCount(int count);
        abstract void updateFollowButton(boolean value);
        abstract void logoutUser();
        abstract void updateIsFollower(boolean isFollower);
    }

    private AuthToken authToken;
    private User selectedUser;
    private User currentUser;

    private static final String LOG_TAG = "MainActivity";

    public MainPresenter(MainView view, AuthToken authToken) {
        super(view);
        this.authToken = authToken;
        this.selectedUser = Cache.getInstance().getCurrUser();
    }

    public void unfollow(User selectedUser){
        view.displayInfoMessage("Unfollowing...");
        this.selectedUser = selectedUser;
        if (currentUser == null) {
            this.currentUser = Cache.getInstance().getCurrUser();
        }
        new FollowService().unfollow(authToken, selectedUser, currentUser, this);
    }

    public void follow(User selectedUser) {
        view.displayInfoMessage("Following...");
        this.selectedUser = selectedUser;
        this.currentUser = Cache.getInstance().getCurrUser();
        new FollowService().follow(authToken, selectedUser, currentUser, this);
    }

    public void logout() {
        view.displayInfoMessage("Logging Out...");
        new UserService().logout(authToken, Cache.getInstance().getCurrUser().getAlias(), this);
    }

    public void isFollower(User currentUser, User selectedUser){
        this.selectedUser = selectedUser;
        new FollowService().isFollower(authToken, currentUser, selectedUser, this);
    }

    public void postStatus(String post) {
        try {
            view.displayInfoMessage("Posting Status...");
            Status newStatus = createStatus(post);
            getStatusService().postStatus(authToken, newStatus, Cache.getInstance().getCurrUser().alias,  this);
        } catch (Exception ex) {
            Log.e(LOG_TAG, ex.getMessage(), ex);
            view.displayErrorMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    public StatusService getStatusService() {
        return new StatusService();
    }

    public Status createStatus(String post) throws ParseException, MalformedURLException {
        return new Status(post, Cache.getInstance().getCurrUser(), getFormattedDateTime(), parseURLs(post), parseMentions(post), System.currentTimeMillis());
    }

    public void updateSelectedUserFollowingAndFollowersCounts() {
        new FollowService().getFollowersCount(authToken, selectedUser, this);
        new FollowService().getFollowingCount(authToken, selectedUser, this);
    }

    @Override
    public void getUnfollowSucceeded() {
        updateSelectedUserFollowingAndFollowersCounts();
        view.updateFollowButton(true);
        view.displayInfoMessage("Removing " + selectedUser.getName() + "...");
    }

    @Override
    public void getFollowSucceeded() {
        updateSelectedUserFollowingAndFollowersCounts();
        view.updateFollowButton(false);
        view.displayInfoMessage("Adding " + selectedUser.getName() + "...");
    }

    @Override
    public void getFollowersCountSucceeded(int count) {
        view.updateFollowerCount(count);
    }

    @Override
    public void getFollowingCountSucceeded(int count) {
        view.updateFollowingCount(count);
    }

    @Override
    public void logoutSucceeded() {
        view.displayInfoMessage("Logged Out");
        view.logoutUser();
    }

    @Override
    public void handleFailed(String message) {
        view.displayErrorMessage(message);
    }


    @Override
    public void isFollowerSucceeded(boolean isFollower) {
        updateSelectedUserFollowingAndFollowersCounts();
        view.updateIsFollower(isFollower);
    }

    @Override
    public void postStatusSucceeded(String message) {
        view.displayInfoMessage(message);
    }

    private String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    private List<String> parseURLs(String post) throws MalformedURLException {
        List<String> containedUrls = new ArrayList<>();
        for (String word : post.split("\\s")) {
            if (word.startsWith("http://") || word.startsWith("https://")) {

                int index = findUrlEndIndex(word);

                word = word.substring(0, index);

                containedUrls.add(word);
            }
        }

        return containedUrls;
    }

    private int findUrlEndIndex(String word) {
        if (word.contains(".com")) {
            int index = word.indexOf(".com");
            index += 4;
            return index;
        } else if (word.contains(".org")) {
            int index = word.indexOf(".org");
            index += 4;
            return index;
        } else if (word.contains(".edu")) {
            int index = word.indexOf(".edu");
            index += 4;
            return index;
        } else if (word.contains(".net")) {
            int index = word.indexOf(".net");
            index += 4;
            return index;
        } else if (word.contains(".mil")) {
            int index = word.indexOf(".mil");
            index += 4;
            return index;
        } else {
            return word.length();
        }
    }

    private List<String> parseMentions(String post) {
        List<String> containedMentions = new ArrayList<>();

        for (String word : post.split("\\s")) {
            if (word.startsWith("@")) {
                word = word.replaceAll("[^a-zA-Z0-9]", "");
                word = "@".concat(word);

                containedMentions.add(word);
            }
        }

        return containedMentions;
    }
}
