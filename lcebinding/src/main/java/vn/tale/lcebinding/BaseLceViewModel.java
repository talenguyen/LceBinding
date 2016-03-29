/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 2/27/16.
 */

package vn.tale.lcebinding;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Base View Model to handle Loading - Content - Error - Empty pattern when start content from api.
 *
 * @param <T> expected model type
 */
public class BaseLceViewModel<T> extends BindableLce {
  private final ThreadScheduler threadScheduler;

  /**
   * Constructor
   *
   * @param errorMessageProvider message provider.
   * @param threadScheduler thread scheduler which will be use to subscribe/observe
   */
  public BaseLceViewModel(ErrorMessageProvider errorMessageProvider,
      ThreadScheduler threadScheduler) {
    super(errorMessageProvider);
    this.threadScheduler = threadScheduler;
  }

  public Observable<T> start(Observable<T> target) {
    showLoading();
    return target.subscribeOn(threadScheduler.subscribeOn())
        .observeOn(threadScheduler.observeOn())
        .doOnNext(new Action1<T>() {
          @Override public void call(T ts) {
            showContent();
          }
        })
        .doOnError(new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            showError(throwable);
          }
        })
        .doOnCompleted(new Action0() {
          @Override public void call() {
            hideLoading();
          }
        });
  }
}
