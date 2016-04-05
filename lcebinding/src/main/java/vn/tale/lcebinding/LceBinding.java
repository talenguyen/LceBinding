package vn.tale.lcebinding;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBinding {

  private CompositeSubscription subscriptions = null;

  public void bind(LoadingContentError lce, ShowHideView loadingView,
      ShowHideView contentView,
      final ErrorView errorView) {

    if (subscriptions == null) {
      subscriptions = new CompositeSubscription();
    }

    bindShowHide(lce.isLoading(), loadingView);

    bindShowHide(lce.isShowContent(), contentView);

    bindShowHide(lce.isError(), errorView);

    bindErrorMessage(lce.errorMessage(), errorView);

  }

  public void bindShowHide(Observable<Boolean> showHideStream, final ShowHideView showHideView) {
    if (subscriptions == null) {
      subscriptions = new CompositeSubscription();
    }
    final Subscription subscription = showHideStream.subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean show) {
        if (show) {
          showHideView.show();
        } else {
          showHideView.hide();
        }
      }
    });
    subscriptions.add(subscription);
  }

  public void bindErrorMessage(Observable<String> msgStream, final ErrorView errorView) {
    if (subscriptions == null) {
      subscriptions = new CompositeSubscription();
    }
    final Subscription subscription = msgStream.subscribe(new Action1<String>() {
      @Override
      public void call(String msg) {
        errorView.setError(msg);
      }
    });
    subscriptions.add(subscription);
  }

  public void unbind() {
    if (subscriptions == null) {
      return;
    }
    subscriptions.unsubscribe();
    subscriptions.clear();
    subscriptions = null;
  }

}