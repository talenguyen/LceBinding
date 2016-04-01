/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 3/1/16.
 */

package vn.tale.lcebinding;

import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.SerializedRelay;
import rx.Observable;
import rx.functions.Action1;

/**
 * Base view model class which will handle to display Loading-Content-Error.
 */
public class LoadingContentError {

  private final SerializedRelay<Boolean, Boolean> showContentSubject;
  private final SerializedRelay<Boolean, Boolean> loadingSubject;
  private final SerializedRelay<Boolean, Boolean> errorSubject;
  private final SerializedRelay<String, String> errorMessageSubject;
  private final ErrorMessageProvider errorMessageProvider;

  public LoadingContentError(ErrorMessageProvider errorMessageProvider) {
    this.showContentSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.loadingSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.errorSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.errorMessageSubject = BehaviorRelay.<String>create().toSerialized();
    this.errorMessageProvider = errorMessageProvider;
  }

  public Observable<Boolean> isShowContent() {
    return showContentSubject.asObservable().distinctUntilChanged();
  }

  public Observable<Boolean> isLoading() {
    return loadingSubject.asObservable().distinctUntilChanged();
  }

  public Observable<Boolean> isError() {
    return errorSubject.asObservable().distinctUntilChanged();
  }

  public Observable<String> errorMessage() {
    return errorMessageSubject.asObservable();
  }

  /**
   * Call to enable loading mode.
   */
  public void showLoading() {
    loadingSubject.call(true);
    showContentSubject.call(false);
    errorSubject.call(false);
  }

  /**
   * Call to hide loading.
   */
  public void hideLoading() {
    loadingSubject.call(false);
  }

  public void showContent() {
    showContentSubject.call(true);
    loadingSubject.call(false);
    errorSubject.call(false);
  }

  public void hideContent() {
    showContentSubject.call(false);
  }

  public void showError(Throwable throwable) {
    errorSubject.call(true);
    loadingSubject.call(false);
    showContentSubject.call(false);
    final String message = errorMessageProvider.getErrorMessage(throwable);
    errorMessageSubject.call(message);
  }

  public void hideError() {
    errorSubject.call(false);
  }
}
