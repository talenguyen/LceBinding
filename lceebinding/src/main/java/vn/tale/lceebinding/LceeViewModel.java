/**
 * RxRepository
 * <p/>
 * Created by Giang Nguyen on 3/1/16.
 */

package vn.tale.lceebinding;

import com.jakewharton.rxrelay.BehaviorRelay;
import com.jakewharton.rxrelay.SerializedRelay;
import rx.Observable;
import rx.functions.Action1;

/**
 * Base view model class which will handle to display Loading-Content-Error-Empty.
 */
public class LceeViewModel {

  private final SerializedRelay<Boolean, Boolean> showContentSubject;
  private final SerializedRelay<Boolean, Boolean> loadingSubject;
  private final SerializedRelay<Boolean, Boolean> errorSubject;
  private final SerializedRelay<Boolean, Boolean> emptySubject;
  private final SerializedRelay<String, String> lightErrorSubject;
  private final SerializedRelay<String, String> errorMessageSubject;
  private final ErrorMessageProvider errorMessageProvider;
  private boolean isContentShowing = false;

  public LceeViewModel(ErrorMessageProvider errorMessageProvider) {
    this.showContentSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.loadingSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.errorSubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.emptySubject = BehaviorRelay.<Boolean>create().toSerialized();
    this.lightErrorSubject = BehaviorRelay.<String>create().toSerialized();
    this.errorMessageSubject = BehaviorRelay.<String>create().toSerialized();
    this.errorMessageProvider = errorMessageProvider;
    showContentSubject.subscribe(new Action1<Boolean>() {
      @Override public void call(Boolean aBoolean) {
        isContentShowing = aBoolean;
      }
    });
  }

  //////
  // Getter
  //////
  public Observable<Boolean> isShowContent() {
    return showContentSubject.asObservable().distinctUntilChanged();
  }

  public Observable<Boolean> isLoading() {
    return loadingSubject.asObservable().distinctUntilChanged();
  }

  public Observable<Boolean> isError() {
    return errorSubject.asObservable().distinctUntilChanged();
  }

  public Observable<Boolean> isEmpty() {
    return emptySubject.asObservable().distinctUntilChanged();
  }

  public Observable<String> lightError() {
    return lightErrorSubject.asObservable();
  }

  public Observable<String> errorMessage() {
    return errorMessageSubject.asObservable();
  }
  //////
  // Public methods.
  //////

  /**
   * Call to enable loading mode.
   */
  public void showLoading() {
    loadingSubject.call(true);
    showContentSubject.call(false);
    errorSubject.call(false);
    emptySubject.call(false);
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
    emptySubject.call(false);
  }

  public void hideContent() {
    showContentSubject.call(false);
  }

  public void showEmpty() {
    emptySubject.call(true);
    loadingSubject.call(false);
    errorSubject.call(false);
    showContentSubject.call(false);
  }

  public void hideEmpty() {
    emptySubject.call(false);
  }

  public void showError(Throwable throwable) {
    showContentSubject.call(isContentShowing);
    errorSubject.call(!isContentShowing);
    loadingSubject.call(false);
    emptySubject.call(false);
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
