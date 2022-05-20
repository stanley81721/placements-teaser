package com.interview.common;

public final class Constants {

    public interface Reviewable {
        public static final int REVIEWDED = 1;
        public static final int UNREVIEWED = 0;
    }

    public interface Common {
        public static final String EMPTY_STR = "";
    }

    public interface JSONVariableName {
        public static final String STATUS = "status";
        public static final String STATUS_ERROR = "ERROR";
        public static final String STATUS_SUCCESS = "SUCCESS";
    }
    
}
