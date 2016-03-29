/**
 * LceBinding
 * <p/>
 * Created by Giang Nguyen on 3/2/16.
 */
package vn.tale.lcebinding;

/**
 * Utility class for retrieve message corresponding to the Exception error.
 */
public interface ErrorMessageProvider {

  /**
   * Get the error message to show on a screen
   *
   * @param throwable the error
   * @return message correspond to the exception.
   */
  String getErrorMessage(Throwable throwable);

  /**
   * Get the error message to show on a screen
   *
   * @param throwable the error
   * @return message correspond to the exception.
   */
  String getLightErrorMessage(Throwable throwable);
}
