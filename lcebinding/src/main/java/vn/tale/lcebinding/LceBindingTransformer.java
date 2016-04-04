package vn.tale.lcebinding;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Implementation of {@link Observable.Transformer} to bind an observable to Lce
 * @param <T> data type.
 */
public class LceBindingTransformer<T> implements Observable.Transformer<T, T> {

  private LoadingContentError lce;

  public LceBindingTransformer(LoadingContentError lce) {
    this.lce = lce;
  }

  @Override public Observable<T> call(Observable<T> sourceStream) {
    return sourceStream.doOnSubscribe(new Action0() {
      @Override public void call() {
        lce.showLoading();
      }
    }).doOnNext(new Action1<T>() {
      @Override public void call(T ts) {
        lce.showContent();
      }
    }).doOnError(new Action1<Throwable>() {
      @Override public void call(Throwable throwable) {
        lce.showError(throwable);
      }
    }).doOnCompleted(new Action0() {
      @Override public void call() {
        lce.hideLoading();
      }
    });
  }
}
