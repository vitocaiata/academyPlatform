package com.caiata;

import java.io.File;

public class GlobalParameters {
    public static final String PACKAGE = "com.palmieri";

    //EXT_SYSTEM
    public static final String EXT_JPG                      =                    ".jpg";
    public static final String EXT_PNG                      =                    ".png";
    public static final String EXT_HTML                     =                   ".html";
    public static final String EXT_EXE                      =                    ".exe";
    public static final String EXT_ANDROID                  =                   ".apk";
    public static final String EXT_JSON                     =                   ".json";
    public static final String EXT_PROP                     =                 ".properties";
    public static final String EXT_LOG                      =                    ".log";
    public static final String EXT_JAVA                     =                   ".java";


    public static final String HTTP                         =                    "http";
    public static final String HTTPS                        =                   "https";

    //OS
    public static final String MY_SO                        =               System.getProperty("os.name");
    public static final String WINDOWS_SO                   =               "Windows";

    //PATH
    public static final String USER_DIR                     =               System.getProperty("user.dir");
    public static final String SRC_DIR                      =               USER_DIR + File.separator + "src";
    public static final String DRIVER_DIR                   =               USER_DIR + File.separator + "driver";
    public static final String REPORT_DIR                   =               USER_DIR + File.separator + "report";
    public static final String SCREENSHOT_DIR               =               USER_DIR + File.separator + "screenshot";
    public static final String LOGS_PATH                    =               USER_DIR + File.separator + "logs";
    public static final String MAIN_DIR                     =               SRC_DIR + File.separator + "main";
    public static final String ROOT_PATH_TEST               =               SRC_DIR + File.separator + "test" + File.separator + "java";
    public static final String RESOURCES_PATH               =               MAIN_DIR + File.separator + "resources";
    public static final String CHROME_DRIVER_PATH           =               DRIVER_DIR + File.separator + "chrome" + File.separator + WINDOWS_SO + File.separator + "chromedriver" + EXT_EXE;
    public static final String BASE_PATH_APP                =               RESOURCES_PATH + File.separator + "apps";
    public static final String PROPERTIES_PATH              =               RESOURCES_PATH + File.separator + "properties";

    //

}
