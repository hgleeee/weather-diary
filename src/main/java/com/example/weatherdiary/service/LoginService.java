package com.example.weatherdiary.service;

public interface LoginService {

    public void login(String loginId);

    public void logout();

    public String getCurrentLoginId();
}
