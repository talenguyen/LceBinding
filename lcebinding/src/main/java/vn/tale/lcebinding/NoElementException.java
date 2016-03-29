package vn.tale.lcebinding;

/**
 * Author giangnguyen. Created on 3/29/16.
 */
public class NoElementException extends RuntimeException {

  private static final long serialVersionUID = -1848914673093119416L;

  /**
   * Constructs a new {@code NoElementException} that includes the current
   * stack trace.
   */
  public NoElementException() {
  }

  /**
   * Constructs a new {@code NoElementException} with the current stack
   * trace and the specified detail message.
   *
   * @param detailMessage the detail message for this exception.
   */
  public NoElementException(String detailMessage) {
    super(detailMessage);
  }

  /**
   * Constructs a new {@code NoElementException} with the current stack
   * trace, the specified detail message and the specified cause.
   *
   * @param message the detail message for this exception.
   * @param cause the cause of this exception.
   * @since 1.5
   */
  public NoElementException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructs a new {@code NoElementException} with the current stack
   * trace and the specified cause.
   *
   * @param cause the cause of this exception, may be {@code null}.
   * @since 1.5
   */
  public NoElementException(Throwable cause) {
    super((cause == null ? null : cause.toString()), cause);
  }
}
