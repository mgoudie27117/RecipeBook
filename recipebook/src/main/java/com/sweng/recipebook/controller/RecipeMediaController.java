package com.sweng.recipebook.controller;

//import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;

/**
 * RecipeMediaController - REST controller for all API calls related to
 * application recipe media.
 */
@RestController
@RequestMapping("/api/recipemedia")
public class RecipeMediaController extends Controller {

    // @RequestMapping(value = "/uploadrecipemedia")
    // public String uploadrecipemedia(@RequestParam("file") MultipartFile file) {
    // String result = "SUCCESS";
    // String fileName = StringUtils.cleanPath(file.getOriginalFilename());
    // System.out.println(fileName);
    // return result;
    // }
}
