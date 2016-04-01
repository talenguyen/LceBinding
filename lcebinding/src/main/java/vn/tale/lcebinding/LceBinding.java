package vn.tale.lcebinding;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBinding {

  private CompositeSubscription subscriptions;

  public void bind(@NonNull LoadingContentError loadingContentError,
      @NonNull ShowHideView loadingView,
      @NonNull ShowHideView contentView,
      @NonNull final ErrorView errorView) {

    subscriptions = new CompositeSubscription();

    final Subscription loadingSubscription = bindShowHide(loadingContentError.isLoading(), loadingView);
    subscriptions.add(loadingSubscription);

    final Subscription contentSubscription = bindShowHide(loadingContentError.isShowContent(), contentView);
    subscriptions.add(contentSubscription);

    final Subscription errorShowHideSubscription = bindShowHide(loadingContentError.isError(),
        errorView);
    subscriptions.add(errorShowHideSubscription);

    final Subscription errorMsgSubscription =
        bindMessage(loadingContentError.errorMessage(), new Action1<String>() {
          @Override public void call(String msg) {
            errorView.setError(msg);
          }
        });
    subscriptions.add(errorMsgSubscription);

  }

  public void unbind() {
    if (subscriptions != null) {
      subscriptions.unsubscribe();
      subscriptions.clear();
      subscriptions = null;
    }
  }

  private Subscription bindMessage(Observable<String> msgStream, Action1<String> action1) {
    return msgStream.subscribe(action1);
  }

  private Subscription bindShowHide(@NonNull Observable<Boolean> showHideStream,
      @NonNull final ShowHideView showHideView) {
    return showHideStream.subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean loading) {
        if (loading) {
          showHideView.show();
        } else {
          showHideView.hide();
        }
      }
    });
  }
}
