package com.example.blob.Service;

import java.util.ArrayList;
public interface HRAccessClient {
    void connect();

    void connect(String url, String username, String password);
}