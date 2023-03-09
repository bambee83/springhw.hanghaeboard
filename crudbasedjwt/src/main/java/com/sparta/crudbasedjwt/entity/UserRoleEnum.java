package com.sparta.crudbasedjwt.entity;

public enum UserRoleEnum {   //Enum : 유저의 권한을 알려준다 ! 사실 이넘클래스 잘 모르겠음 패턴으로 이해해보려했음.
    USER(Authority.USER),  // 사용자 권한
    ADMIN(Authority.ADMIN);  // 관리자 권한

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }

    public String getAuthority() {
        return this.authority;
    }

    public static class Authority {
        public static final String USER = "ROLE_USER";
        public static final String ADMIN = "ROLE_ADMIN";
    }
}
