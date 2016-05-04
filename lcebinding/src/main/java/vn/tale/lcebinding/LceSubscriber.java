package vn.tale.lcebinding;

import rx.Subscriber;

/**
 * Extend {@link Subscriber} to involve appropriate {@link LoadingContentError} method base on the
 * callback
 *
 * @param <T> data type.
 */
public class LceSubscriber<T> extends Subscriber<T> {
  private final LoadingContentError lce;

  public LceSubscriber(LoadingContentError lce) {
    this.lce = lce;
  }

  @Override public void onStart() {
    super.onStart();
    lce.showLoading();
  }

  @Override public void onCompleted() {
    lce.hideLoading();
  }

  @Override public void onError(Throwable e) {
    lce.showError(e);
  }

  @Override public void onNext(T t) {
    lce.showContent();
  }
}
