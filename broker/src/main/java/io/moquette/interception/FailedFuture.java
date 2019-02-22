package io.moquette.interception;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FailedFuture<T> implements Future<T> {

    private final Throwable exception;
    private final String message;

    public FailedFuture(Throwable exception) {
        this(null, exception);
    }

    public FailedFuture(String message) {
        this(message, null);
    }
    
    public FailedFuture(String message, Throwable exception) {
    	this.message = message;
        this.exception = exception;
    }

    @Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public T get() throws InterruptedException, ExecutionException {
		throw new ExecutionException(this.message, this.exception);
	}

	@Override
	public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
		return get();
	}

}
