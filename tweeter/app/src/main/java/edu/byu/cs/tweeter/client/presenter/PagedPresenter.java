package edu.byu.cs.tweeter.client.presenter;

import java.net.MalformedURLException;
import java.util.List;

import edu.byu.cs.tweeter.client.model.service.UserService;
import edu.byu.cs.tweeter.client.model.service.observer.PagedObserver;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;

public abstract class PagedPresenter<T> extends Presenter<PagedPresenter.PagedView> implements UserService.GetUserObserver, PagedObserver<T> {

    public interface PagedView<T> extends Presenter.View {
        abstract void addItems(List<T> items);
        abstract void setLoading(boolean value) throws MalformedURLException;
        abstract void navigateToUser(User user);
    }

    public PagedPresenter(PagedView view, AuthToken authToken, User user) {
        super(view);
        this.authToken = authToken;
        this.user = user;
    }

    protected AuthToken authToken;
    protected User user;
    public boolean isLoading = false;
    public boolean hasMorePages = true;
    public T lastItem;
    public List<T> items;
    protected static final int PAGE_SIZE = 10;

    public void loadMoreItems() throws MalformedURLException {
        if (!isLoading && hasMorePages) {
            isLoading = true;
            view.setLoading(true);
            getItems(authToken, user, PAGE_SIZE, lastItem);
        }
    }

    protected abstract void getItems(AuthToken authToken, User user, int pageSize, T lastItem);

    @Override
    public void getUserSucceeded(User user) {
        view.navigateToUser(user);

    }

    @Override
    public void handleFailed(String message) {
        view.displayErrorMessage(message);
    }

    @Override
    public void getItemsSucceeded(List<T> items, boolean hasMorePages) throws MalformedURLException {
        view.setLoading(false);
        lastItem = (items.size() > 0) ? items.get(items.size() - 1) : null;
        this.hasMorePages = hasMorePages;
        this.items  = items;
        view.addItems(items);
        isLoading = false;
    }

    public void goToUser(String alias) {
        view.displayInfoMessage("Getting user's profile...");
        new UserService().getUser(authToken, alias, this);
    }

    public boolean load() {
        return !isLoading && hasMorePages;
    }

}
