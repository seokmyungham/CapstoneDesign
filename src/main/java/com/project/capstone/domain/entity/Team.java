package com.project.capstone.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Team {

    MU("TEAM_MAN_UNITED", "맨체스터 유나이티드"),
    TH("TEAM_TOTTENHAM", "토트넘 핫스퍼"),
    MC("TEAM_MAN_CITY", "맨체스터 시티"),
    AS("TEAM_ARSENAL", "아스날"),
    LP("TEAM_LIVERPOOL", "리버풀"),
    CH("TEAM_CHELSEA", "첼시"),

    BA("TEAM_BARCELONA", "바르셀로나"),
    RM("TEAM_REAL_MADRID", "레알 마드리드"),
    AT("TEAM_ATLETICO_MADRID", "아틀레티코 마드리드"),

    BM("TEAM_BAYERN_MUNICH", "바이에른 뮌헨"),
    DM("TEAM_DORTMUND", "도루트문트"),

    AC("TEAM_AC_MILAN", "AC 밀란"),
    IN("TEAM_INTER", "인테르"),
    JU("TEAM_JUVENTUS", "유벤투스"),
    NA("TEAM_NAPOLI", "나폴리"),

    PS("TEAM_PARIS", "파리 생제르맹");


    private final String key;
    private final String Team;
}
