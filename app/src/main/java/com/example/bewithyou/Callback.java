package com.example.bewithyou;

public interface Callback<T> {
    void onSuccess(T data);

    void onError(String errorMessage);
}
