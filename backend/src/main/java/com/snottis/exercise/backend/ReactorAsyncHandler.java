package com.snottis.exercise.backend;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import jakarta.xml.ws.AsyncHandler;
import reactor.core.publisher.MonoSink;

public class ReactorAsyncHandler {
    public static <T> AsyncHandler<T> into(MonoSink<T> sink) {
        return res -> {
            try {
                sink.success(res.get(1, TimeUnit.MILLISECONDS));
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                sink.error(e);
            }
        };
    }
}
