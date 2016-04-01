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
  private final SerializedRelay<String, String> lightErrorSubject;
  private final SerializedRelay<String, String> errorMessageSubject;
  private final ErrorMessageProvider errorMessageProvider;
  private boolean isContentShowing = false;

  public LoadingContentError(ErrorMessageProvider errorMessageProvider) {
    this.showContentSubject = BehaviorRelay.create(isContentShowing).toSerialized();
    this.loadingSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.errorSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.lightErrorSubject = BehaviorRelay.<String>create().toSerialized();
    this.errorMessageSubject = BehaviorRelay.<String>create().toSerialized();
    this.errorMessageProvider = errorMessageProvider;
    showContentSubject.subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean aBoolean) {
        isContentShowing = aBoolean;
      }
    });
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

  public Observable<String> lightError() {
    return lightErrorSubject.asObservable();
  }

  public Observable<String> errorMessage() {
    return errorMessageSubject.asObservable();
  }

  /**
   * Call to enable loading mode.
   */
  public void showLoading() {
    loadingSubject.call(true);
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
    showContentSubject.call(isContentShowing);
    errorSubject.call(!isContentShowing);
    loadingSubject.call(false);
    if (isContentShowing) {
      final String message = errorMessageProvider.getLightErrorMessage(throwable);
      lightErrorSubject.call(message);
    } else {
      final String message = errorMessageProvider.getErrorMessage(throwable);
      errorMessageSubject.call(message);
    }
  }

  public void hideError() {
    errorSubject.call(false);
  }
}
