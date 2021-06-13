package com.sweng.recipebook.controller;

import com.sweng.recipebook.security.JWTHandler;

/**
 * Controller - Abstract class for controller classes to extend.
 */
public abstract class Controller {
    protected static final JWTHandler JWT = new JWTHandler();
}
