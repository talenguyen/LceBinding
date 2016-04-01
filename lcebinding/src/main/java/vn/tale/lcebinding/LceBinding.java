package vn.tale.lcebinding;

import android.support.annotation.NonNull;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Author giangnguyen. Created on 4/1/16.
 */
public class LceBinding {
  public void bind(@NonNull LoadingContentError loadingContentError,
      @NonNull final ShowHideView loadingView, @NonNull ShowHideView contentView,
      @NonNull final ErrorView errorView) {

    bindShowHide(loadingContentError.isLoading(), loadingView);

    bindShowHide(loadingContentError.isShowContent(), contentView);

    bindShowHide(loadingContentError.isError(), errorView);

    bindError(loadingContentError, errorView);

  }

  private void bindError(LoadingContentError loadingContentError, final ErrorView errorView) {
    loadingContentError.errorMessage()
        .subscribe(new Action1<String>() {
          @Override public void call(String msg) {
            errorView.setError(msg);
          }
        });

    loadingContentError.lightError()
        .subscribe(new Action1<String>() {
          @Override public void call(String msg) {
            errorView.setLightError(msg);
          }
        });
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
