package vn.tale.lcebinding;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBinding {

  private CompositeSubscription subscriptions;
  private LoadingContentError lce;

  public LceBinding(LoadingContentError lce) {
    this.lce = lce;
  }

  public void bind(@NonNull ShowHideView loadingView,
      @NonNull ShowHideView contentView,
      @NonNull final ErrorView errorView) {

    subscriptions = new CompositeSubscription();

    final Subscription loadingSubscription = bindShowHide(lce.isLoading(), loadingView);
    subscriptions.add(loadingSubscription);

    final Subscription contentSubscription = bindShowHide(lce.isShowContent(), contentView);
    subscriptions.add(contentSubscription);

    final Subscription errorShowHideSubscription = bindShowHide(lce.isError(),
        errorView);
    subscriptions.add(errorShowHideSubscription);

    final Subscription errorMsgSubscription = bindMessage(lce.errorMessage(),
        new Action1<String>() {
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

  public <T> Observable<T> createLceStream(Observable<T> target, ThreadScheduler threadScheduler) {
    return target.subscribeOn(threadScheduler.subscribeOn())
        .observeOn(threadScheduler.observeOn())
        .doOnSubscribe(new Action0() {
          @Override public void call() {
            lce.showLoading();
          }
        })
        .doOnNext(new Action1<T>() {
          @Override public void call(T ts) {
            lce.showContent();
          }
        })
        .doOnError(new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            lce.showError(throwable);
          }
        })
        .doOnCompleted(new Action0() {
          @Override public void call() {
            lce.hideLoading();
          }
        });
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
