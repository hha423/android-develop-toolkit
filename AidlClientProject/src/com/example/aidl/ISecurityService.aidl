package com.example.aidl;

interface ISecurityService {
    String decode(String input);
    String encode(String input);
}