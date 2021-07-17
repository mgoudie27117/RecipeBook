package com.sweng.recipebook.controller;

import com.sweng.recipebook.models.JWTHandler;
import com.sweng.recipebook.models.SecurityHandler;

/**
 * Controller - Abstract class for controller classes to extend.
 */
public abstract class Controller {
    protected static final SecurityHandler JWT = new JWTHandler();
}
